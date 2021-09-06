package net.manbucy.ipp.cover.auth.config;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.cloud.security.provider.enhance.AbstractEnhanceAuthenticateHandler;
import net.manbucy.ipp.cloud.security.provider.enhance.EnhanceAuthenticationProvider;
import net.manbucy.ipp.cloud.security.provider.enhance.EnhanceTokenGranter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ManBu
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthAuthorizeServerConfigurer extends AuthorizationServerConfigurerAdapter {
    final RedisConnectionFactory redisConnectionFactory;

    final DataSource dataSource;

    final List<AbstractEnhanceAuthenticateHandler> enhanceAuthList;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(redisTokenStore())
                .authenticationManager(providerManager())
                .tokenGranter(compositeTokenGranter(endpoints));

    }

    public AuthenticationManager providerManager() {
        EnhanceAuthenticationProvider enhanceProvider = new EnhanceAuthenticationProvider();
        enhanceProvider.setAuthenticateHandlerList(enhanceAuthList);

        return new ProviderManager(enhanceProvider);
    }


    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    private TokenGranter compositeTokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new EnhanceTokenGranter(providerManager(), endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(granters);
    }

}
