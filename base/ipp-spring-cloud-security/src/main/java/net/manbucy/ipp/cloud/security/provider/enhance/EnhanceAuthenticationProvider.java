package net.manbucy.ipp.cloud.security.provider.enhance;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Map;

/**
 * @author ManBu
 */
public class EnhanceAuthenticationProvider implements AuthenticationProvider {

    private List<AbstractEnhanceAuthenticateHandler> authenticateHandlerList;

    public EnhanceAuthenticationProvider() {

    }

    public EnhanceAuthenticationProvider(List<AbstractEnhanceAuthenticateHandler> authenticateHandlerList) {
        this.authenticateHandlerList = authenticateHandlerList;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticateHandlerList == null || authenticateHandlerList.size() == 0) {
            throw new ProviderNotFoundException("can't find any support handler");
        }

        EnhanceAuthenticationToken userToken = (EnhanceAuthenticationToken) authentication;
        String authType = userToken.getPrincipal().toString();
        Map<String, String> credentials = (Map<String, String>) userToken.getCredentials();

        for (AbstractEnhanceAuthenticateHandler handler : authenticateHandlerList) {
            if (handler.support(userToken.getPrincipal().toString())) {
                return handler.authenticate(authType, credentials);
            }
        }
        throw new ProviderNotFoundException("can't find any support handler");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EnhanceAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setAuthenticateHandlerList(List<AbstractEnhanceAuthenticateHandler> authenticateHandlerList) {
        this.authenticateHandlerList = authenticateHandlerList;
    }
}
