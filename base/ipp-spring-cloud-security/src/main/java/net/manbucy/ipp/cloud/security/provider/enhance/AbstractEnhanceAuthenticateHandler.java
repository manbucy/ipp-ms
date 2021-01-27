package net.manbucy.ipp.cloud.security.provider.enhance;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * @author ManBu
 */
public abstract class AbstractEnhanceAuthenticateHandler {

    public boolean support(String authType) {
        if (StrUtil.isBlank(authType) || StrUtil.isBlank(getAuthType())) {
            return false;
        }
        return StrUtil.equalsAnyIgnoreCase(authType, getAuthType());
    }

    public Authentication authenticate(String authType, Map<String, String> credentials) {
        UserDetails userDetails = retrieveUser(credentials);
        checkUser(userDetails);
        return new EnhanceAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    /**
     * 获取当前类的 authType
     *
     * @return
     */
    protected abstract String getAuthType();

    protected abstract UserDetails retrieveUser(Map<String, String> credentials);

    private void checkUser(UserDetails user) {
        if (user == null) {
            throw new BadCredentialsException("cant not retrieveUser any user");
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }

        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("User credentials have expired");
        }
    }

}
