package net.manbucy.ipp.cloud.security.config;

import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author ManBu
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ResourceServerIgnoreUrlProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    final RedisConnectionFactory redisConnectionFactory;

    final ResourceServerIgnoreUrlProperties ignoreUrlProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(new RedisTokenStore(redisConnectionFactory));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ArrayUtil.toArray(ignoreUrlProperties.getIgnoreUrl().iterator(), String.class)).permitAll()
                .anyRequest().authenticated();
    }
}
