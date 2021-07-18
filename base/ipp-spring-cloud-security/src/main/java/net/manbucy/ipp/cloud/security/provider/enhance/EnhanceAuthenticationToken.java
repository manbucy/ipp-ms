package net.manbucy.ipp.cloud.security.provider.enhance;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * @author ManBu
 */
public class EnhanceAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;
    /**
     * 授权登录类型
     */
    private final Object principal;

    /**
     * 授权登录信息
     */
    private Map<String, String> credentials;

    public EnhanceAuthenticationToken(Object principal, Map<String, String> credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    public EnhanceAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
