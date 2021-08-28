package net.manbucy.ipp.cloud.document.config;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.cloud.document.provider.IppSwaggerResourceProvider;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author ManBu
 * @date 2021/8/9 22:18
 */
@Configuration
@RequiredArgsConstructor
public class DocumentServerConfiguration {
    private final Environment environment;
    private final DiscoveryClient discoveryClient;

    @Bean
    @Primary
    public IppSwaggerResourceProvider ippSwaggerResourceProvider() {
        return new IppSwaggerResourceProvider(environment, discoveryClient, documentRestTemplate());
    }

    @Bean
    public RestTemplate documentRestTemplate() {
        return new RestTemplate();
    }
}
