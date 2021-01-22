package net.manbucy.ipp.cover.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * @author ManBu
 */
@Component
@ConditionalOnProperty(name = "spring.cloud.gateway.nacos-metadata-balancer.enabled")
@RefreshScope
public class NacosMetadataLoadBalancerClientFilter implements GlobalFilter, Ordered {
    public static final int NACOS_METADATA_LOAD_BALANCER_ORDER = 10149;
    public static final String LOAD_BALANCER_PREFIX = "lb";

    private final LoadBalancerClientFactory clientFactory;

    private final LoadBalancerProperties loadBalancerProperties;

    @Value("${spring.cloud.gateway.nacos-metadata-balancer.key}")
    private String metadataKey;

    public NacosMetadataLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory,
                                                 LoadBalancerProperties loadBalancerProperties) {
        this.clientFactory = clientFactory;
        this.loadBalancerProperties = loadBalancerProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null || (!LOAD_BALANCER_PREFIX.equals(url.getScheme()) && !LOAD_BALANCER_PREFIX.equals(schemePrefix))) {
            return chain.filter(exchange);
        }
        // preserve the original url
        addOriginalRequestUrl(exchange, url);

        URI requestUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        if (ObjectUtils.isEmpty(requestUri)) {
            return chain.filter(exchange);
        }
        String serviceId = requestUri.getHost();
        ServiceInstanceListSupplier supplier = clientFactory.getProvider(serviceId, ServiceInstanceListSupplier.class)
                .getIfAvailable(NoopServiceInstanceListSupplier::new);

        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(new RequestDataContext(
                new RequestData(exchange.getRequest()), getHint(serviceId, loadBalancerProperties.getHint())));

        return supplier.get(lbRequest)
                .next()
                .map(serviceInstances -> getInstanceResponse(serviceInstances, lbRequest))
                .doOnNext(response -> {
                    if (response.hasServer()) {
                        // 当前response有效则进行替换url地址，否则不处理
                        ServiceInstance retrievedInstance = response.getServer();

                        URI uri = exchange.getRequest().getURI();

                        // if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
                        // if the loadbalancer doesn't provide one.
                        String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
                        if (schemePrefix != null) {
                            overrideScheme = url.getScheme();
                        }

                        DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance,
                                overrideScheme);

                        URI requestUrl = LoadBalancerUriTools.reconstructURI(serviceInstance, uri);

                        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, requestUrl);
                        exchange.getAttributes().put(GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
                    }
                }).then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return NACOS_METADATA_LOAD_BALANCER_ORDER;
    }

    private String getHint(String serviceId, Map<String, String> hints) {
        String defaultHint = hints.getOrDefault("default", "default");
        String hintPropertyValue = hints.get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request<RequestDataContext> request) {
        List<Response<ServiceInstance>> responseList = instances.stream()
                .filter(serviceInstance -> {
                    Map<String, String> metadata = serviceInstance.getMetadata();
                    HttpHeaders headers = request.getContext().getClientRequest().getHeaders();
                    if (StringUtils.hasText(metadata.get(metadataKey))
                            && StringUtils.hasText(headers.getFirst(metadataKey))) {
                        return metadata.get(metadataKey).equals(headers.getFirst(metadataKey));
                    }
                    return false;
                })
                .map(DefaultResponse::new)
                .collect(Collectors.toList());
        if (responseList.isEmpty()) {
            return new EmptyResponse();
        }
        // 在所有符合要求的instance中随机选择
        int index = ThreadLocalRandom.current().nextInt(responseList.size());
        return responseList.get(index);
    }
}
