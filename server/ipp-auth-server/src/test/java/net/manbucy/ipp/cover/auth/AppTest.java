package net.manbucy.ipp.cover.auth;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.config.properties.VerifyCodeLimitProperties;
import net.manbucy.ipp.cover.auth.mapper.PermissionMapper;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import net.manbucy.ipp.cover.auth.pojo.entity.Permission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTest {

    @Autowired
    protected ApplicationContext ctx;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void getEncryptPwd() {
//        String encryptPwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
//        log.info("encrypt: {}", encryptPwd);

        String content = "{\"username\":\"admin\",\"password\":\"admin.123456\",\"email\":\"1412039603@qq.com\",\"verifyCode\":\"198251\"}";

        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), "ipp#aes".getBytes()).getEncoded();
        AES aes = SecureUtil.aes(key);

        // 加密
        byte[] encrypt = aes.encrypt(content);

        // 解密
        byte[] decrypt = aes.decrypt(encrypt);

        // 加密为16进制表示
        String encryptHex = aes.encryptHex(content);

        // 解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        System.out.println(decryptStr);
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


    @Test
    public void test_get_verify_code_limit_properties_info() {
        VerifyCodeLimitProperties verifyCodeLimitProperties = ctx.getBean(VerifyCodeLimitProperties.class);

        log.info("verifyCodeLimitProperties: {}", verifyCodeLimitProperties);
    }
}
