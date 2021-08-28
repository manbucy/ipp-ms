package net.manbucy.ipp.cover.gateway.filter;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyDecoder;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyEncoder;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * http api 安全过滤器，负责对http中的request body json 进行加密解密
 *
 * @author ManBu
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "spring.cloud.gateway.security-http-api.enabled")
@RefreshScope
public class SecurityHttpApiFilter implements GlobalFilter, Ordered, InitializingBean {
    private static final int SECURITY_HTTP_API_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;

    private final List<HttpMessageReader<?>> messageReaders;
    private final Map<String, MessageBodyDecoder> messageBodyDecoders;
    private final Map<String, MessageBodyEncoder> messageBodyEncoders;

    @Value("${spring.cloud.gateway.security-http-api.aes-key}")
    private String aesKey;

    private AES aes;

    private static final String HEADER_SECURITY_API_ENABLE = "Ipp-Security-Api-Enable";
    private static final String HEADER_SECURITY_API_DECODE_REQ = "Ipp-Security-Decode-Req";
    private static final String HEADER_SECURITY_API_ENCODE_RESP = "Ipp-Security-Encode-Resp";
    private static final String HEADER_SECURITY_API_DECODE_RESP = "Ipp-Security-Decode-Resp";

    public SecurityHttpApiFilter(Set<MessageBodyDecoder> messageBodyDecoders, Set<MessageBodyEncoder> messageBodyEncoders) {
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
        this.messageBodyDecoders = messageBodyDecoders.stream()
                .collect(Collectors.toMap(MessageBodyDecoder::encodingType, identity()));
        this.messageBodyEncoders = messageBodyEncoders.stream()
                .collect(Collectors.toMap(MessageBodyEncoder::encodingType, identity()));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String enable = headers.getFirst(HEADER_SECURITY_API_ENABLE);

        if (Boolean.TRUE.toString().equals(enable)) {
            ServerWebExchange securityExchange = exchange.mutate()
                    .request(new SecurityHttpApiRequest(exchange))
                    .response(new SecurityHttpApiResponse(exchange))
                    .build();
            return chain.filter(securityExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return SECURITY_HTTP_API_FILTER_ORDER;
    }

    private String decodeBody(String encryptedBody) {
        return aes.decryptStr(encryptedBody);
    }

    private String encodeBody(String unencryptedBody) {
        return aes.encryptBase64(unencryptedBody);
    }

    protected class SecurityHttpApiRequest extends ServerHttpRequestDecorator {
        ServerWebExchange exchange;
        CachedBodyOutputMessage cachedBodyOutputMessage = null;

        public SecurityHttpApiRequest(ServerWebExchange exchange) {
            super(exchange.getRequest());
            this.exchange = exchange;
        }

        @Override
        public Flux<DataBuffer> getBody() {
            if (cachedBodyOutputMessage != null) {
                return cachedBodyOutputMessage.getBody();
            }
            return getUnencryptedBody();
        }

        private Flux<DataBuffer> getUnencryptedBody() {
            ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());

            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                    .flatMap(originalBody -> {
                        String unencryptedBody = decodeBody(originalBody);
                        return Mono.just(unencryptedBody);
                    });

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return BodyInserters.fromPublisher(modifiedBody, String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .thenMany(Flux.defer(() -> {
                        cachedBodyOutputMessage = outputMessage;
                        return cachedBodyOutputMessage.getBody();
                    }));
        }
    }

    protected class SecurityHttpApiResponse extends ServerHttpResponseDecorator {
        ServerWebExchange exchange;

        public SecurityHttpApiResponse(ServerWebExchange exchange) {
            super(exchange.getResponse());
            this.exchange = exchange;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);
            ClientResponse clientResponse = prepareClientResponse(body, httpHeaders);

            Mono<String> modifiedBody = extractBody(exchange, clientResponse, String.class)
                    .flatMap(originalBody -> {
                        String encryptedBody = encodeBody(originalBody);
                        return Mono.just(encryptedBody);
                    });

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                    exchange.getResponse().getHeaders());


            return BodyInserters.fromPublisher(modifiedBody, String.class)
                    .insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        Mono<DataBuffer> messageBody = writeBody(getDelegate(), outputMessage, String.class);
                        return getDelegate().writeWith(messageBody);
                    }));
        }

        @Override
        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMapSequential(p -> p));
        }

        private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body, HttpHeaders httpHeaders) {
            ClientResponse.Builder builder;
            builder = ClientResponse.create(exchange.getResponse().getStatusCode(), messageReaders);
            return builder.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();
        }

        private <T> Mono<T> extractBody(ServerWebExchange exchange, ClientResponse clientResponse, Class<T> inClass) {
            if (byte[].class.isAssignableFrom(inClass)) {
                return clientResponse.bodyToMono(inClass);
            }

            List<String> encodingHeaders = exchange.getResponse().getHeaders().getOrEmpty(HttpHeaders.CONTENT_ENCODING);
            for (String encoding : encodingHeaders) {
                MessageBodyDecoder decoder = messageBodyDecoders.get(encoding);
                if (decoder != null) {
                    return clientResponse.bodyToMono(byte[].class).publishOn(Schedulers.parallel()).map(decoder::decode)
                            .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
                            .map(buffer -> prepareClientResponse(Mono.just(buffer),
                                    exchange.getResponse().getHeaders()))
                            .flatMap(response -> response.bodyToMono(inClass));
                }
            }

            return clientResponse.bodyToMono(inClass);
        }

        private Mono<DataBuffer> writeBody(ServerHttpResponse httpResponse, CachedBodyOutputMessage message,
                                           Class<?> outClass) {
            Mono<DataBuffer> response = DataBufferUtils.join(message.getBody());
            if (byte[].class.isAssignableFrom(outClass)) {
                return response;
            }

            List<String> encodingHeaders = httpResponse.getHeaders().getOrEmpty(HttpHeaders.CONTENT_ENCODING);
            for (String encoding : encodingHeaders) {
                MessageBodyEncoder encoder = messageBodyEncoders.get(encoding);
                if (encoder != null) {
                    DataBufferFactory dataBufferFactory = httpResponse.bufferFactory();
                    response = response.publishOn(Schedulers.parallel()).map(buffer -> {
                        byte[] encodedResponse = encoder.encode(buffer);
                        DataBufferUtils.release(buffer);
                        return encodedResponse;
                    }).map(dataBufferFactory::wrap);
                    break;
                }
            }
            return response;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        aes = new AES(Mode.ECB, Padding.PKCS5Padding, aesKey.getBytes(StandardCharsets.UTF_8));
    }
}
