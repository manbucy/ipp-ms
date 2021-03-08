package net.manbucy.ipp.cover.auth;

import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.mapper.PermissionMapper;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import net.manbucy.ipp.cover.auth.pojo.entity.Permission;
import net.manbucy.ipp.cover.auth.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void getEncryptPwd() {
        String encryptPwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
        log.info("encrypt: {}", encryptPwd);
    }

    @Test
    public void testFindUserDetailByUsername() {
        UserDTO userDTO = userMapper.findUserDetailByUsername("admin");

        log.info("userDTO: {}", userDTO);
    }

    @Test
    public void test_insert_auto_audit_entity_meta_object() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        Permission permission = new Permission();
        permission.setName("testnone");
        permission.setCode("test.none.auto.audit");
        permission.setEnabled(true);

        int i = permissionMapper.insert(permission);

        log.info("result: {}", i);

    }

    @Test
    public void test_update_auto_audit_entity_meta_object() {
        UserDetails userDetail = userDetailsService.loadUserByUsername("admin");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Permission permission = new Permission();
        permission.setName("testUpdateAutoAudit");
        permission.setId(3L);

        permissionMapper.updateById(permission);

    }


}
