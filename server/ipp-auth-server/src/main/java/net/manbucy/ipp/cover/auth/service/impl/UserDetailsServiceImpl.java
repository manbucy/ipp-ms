package net.manbucy.ipp.cover.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.core.security.SystemUserId;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.cloud.security.constants.SecurityConstants;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import net.manbucy.ipp.cover.auth.pojo.dto.RoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ManBu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userMapper.findUserDetailByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("username: %s not found", username));
        }
        List<RoleDTO> roles = user.getRoles();
        Set<String> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(role.getCode().startsWith(SecurityConstants.ROLE_PREFIX) ? role.getCode() :
                    SecurityConstants.ROLE_PREFIX + role.getCode());
            role.getPermissions().forEach(permission -> authorities.add(permission.getCode()));
        });

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));

        UserDetail userDetail = new UserDetail(new SystemUserId(user.getUserId()), user.getUsername(), user.getPassword(),user.getName(),
                !user.getLocked(), grantedAuthorities);
        log.debug("UserDetailsServiceImpl.loadUserByUsername userDetail: {}", userDetail);
        return userDetail;
    }
}
