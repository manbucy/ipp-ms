package net.manbucy.ipp.cover.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.core.security.SystemUserId;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.cloud.security.constants.SecurityConstants;
import net.manbucy.ipp.cloud.security.provider.enhance.AbstractEnhanceAuthenticateHandler;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.RoleDTO;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 密码登录
 *
 * @author ManBu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordEnhanceAuthenticateHandler extends AbstractEnhanceAuthenticateHandler {
    private static final String AUTH_TYPE = "password";

    private static final String USERNAME_PARAMETER_KEY = "username";
    private static final String PASSWORD_PARAMETER_KEY = "password";

    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;

    @Override
    protected String getAuthType() {
        return AUTH_TYPE;
    }

    @Override
    protected UserDetails retrieveUser(Map<String, String> credentials) {
        String username = credentials.get(USERNAME_PARAMETER_KEY);
        String password = credentials.get(PASSWORD_PARAMETER_KEY);

        UserDTO user = userMapper.findUserDetailByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("username: %s not exist", username));
        }
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not correct");
        }

        List<RoleDTO> roles = user.getRoles();
        Set<String> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(role.getCode().startsWith(SecurityConstants.ROLE_PREFIX) ? role.getCode() :
                    SecurityConstants.ROLE_PREFIX + role.getCode());
            role.getPermissions().forEach(permission -> authorities.add(permission.getCode()));
        });

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));

        UserDetail userDetail = new UserDetail(new SystemUserId(user.getUserId()), user.getUsername(), user.getPassword(),
                !user.getLocked(), grantedAuthorities);
        log.debug("UserDetailsServiceImpl.loadUserByUsername userDetail: {}", userDetail);
        return userDetail;
    }
}
