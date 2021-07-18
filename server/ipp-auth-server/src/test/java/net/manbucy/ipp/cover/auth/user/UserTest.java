package net.manbucy.ipp.cover.auth.user;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegAuthType;
import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegInfo;
import net.manbucy.ipp.cover.auth.controller.user.vo.reg.VerifyCodeSendResult;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;
import net.manbucy.ipp.cover.auth.service.UserRegistrationService;
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
    private UserRegistrationService userService;


    @Test
    public void test_send_reg_verify_code_to_email() {
        RegInfo regInfo = new RegInfo();
        regInfo.setAuthType(RegAuthType.EMAIL.toString());
        regInfo.setAuthValue("1412039603@qq.com");
        VerifyCodeSendResult verifyCodeSendResult = userService.sendRegVerifyCodeToAuthNum(regInfo);

        log.info("verifyCodeSendResult: {}", JSONUtil.toJsonStr(verifyCodeSendResult));

    }
}
