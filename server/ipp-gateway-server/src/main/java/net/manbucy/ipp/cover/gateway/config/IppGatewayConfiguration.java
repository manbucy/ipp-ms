package net.manbucy.ipp.cover.gateway.config;

import net.manbucy.ipp.cover.gateway.filter.factory.AccessForbiddenGatewayFilterFactory;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ManBu
 */
@Configuration
public class IppGatewayConfiguration {

    @Bean
    @ConditionalOnEnabledFilter
    public AccessForbiddenGatewayFilterFactory addAccessForbiddenGatewayFilterFactory() {
        return new AccessForbiddenGatewayFilterFactory();
    }
}
