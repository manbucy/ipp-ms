package net.manbucy.ipp.cloud.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ManBu
 */
@Data
@RefreshScope
@ConfigurationProperties("spring.security.oauth2.resource-server")
public class ResourceServerIgnoreUrlProperties {
    private Set<String> ignoreUrl = new HashSet<>();
}
