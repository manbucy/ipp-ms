package net.manbucy.ipp.cover.gateway.filter.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ManBu
 */
@Slf4j
public class AccessForbiddenGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AccessForbiddenGatewayFilterFactory.Config> {

    public AccessForbiddenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("pathPatterns");
    }

    @Override
    public GatewayFilter apply(Config config) {
        AntPathMatcher matcher = new AntPathMatcher();
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();
                String path = request.getPath().pathWithinApplication().value();
                for (String pattern : config.pathPatterns) {
                    if (matcher.match(pattern, path)) {
                        log.info("path: '{}' match pattern: {}, forbidden request", path, pattern);
                        response.setStatusCode(HttpStatus.FORBIDDEN);
                        return response.setComplete();
                    }
                }
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        private List<String> pathPatterns = new ArrayList<>();

        public List<String> getPathPatterns() {
            return pathPatterns;
        }

        public void setPathPatterns(List<String> pathPatterns) {
            this.pathPatterns = pathPatterns;
        }
    }
}
