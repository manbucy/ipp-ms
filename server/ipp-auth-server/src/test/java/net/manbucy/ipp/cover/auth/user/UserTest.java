package net.manbucy.ipp.cover.auth.user;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;
import net.manbucy.ipp.cover.auth.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private UserService userService;


    @Test
    public void test_send_reg_verify_code_to_email() {
        String email = "1412039603@qq.com";
        RegistrationResult verifyCodeSendResult = userService.sendRegVerifyCodeToEmail(email);

        log.info("verifyCodeSendResult: {}", JSONUtil.toJsonStr(verifyCodeSendResult));

    }
}
