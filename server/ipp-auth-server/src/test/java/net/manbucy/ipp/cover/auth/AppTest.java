package net.manbucy.ipp.cover.auth;

import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.mapper.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void getEncryptPwd() {
        String encryptPwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
        log.info("encrypt: {}", encryptPwd);
    }

    @Test
    public void testFindUserDetailByUsername(){
        UserDTO userDTO = userMapper.findUserDetailByUsername("admin");

        log.info("userDTO: {}", userDTO);
    }

}
