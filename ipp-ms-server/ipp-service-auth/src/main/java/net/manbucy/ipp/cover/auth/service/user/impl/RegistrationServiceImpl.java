package net.manbucy.ipp.cover.auth.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.cover.auth.controller.user.ao.UserThirdAuthType;
import net.manbucy.ipp.cover.auth.controller.user.ao.VerifyCodeReceiver;
import net.manbucy.ipp.cover.auth.service.user.RegistrationService;
import net.manbucy.ipp.cover.auth.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * 注册服务-接口实现类
 *
 * @author ManBu
 * @since 2021/10/30 15:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;

    @Override
    public void sendVerifyCode(VerifyCodeReceiver verifyCodeReceiver) {
        log.info("sendVerifyCode. 验证码接收者为verifyCodeReceiver: {}", verifyCodeReceiver);
        final UserThirdAuthType thirdAuthType = verifyCodeReceiver.getType();
        final String thirdAuthAccount = verifyCodeReceiver.getReceiver();
        // 1、校验手机号码 或 邮箱 是否已注册
        userService.validateThirdAuthAccountRegistered(thirdAuthType, thirdAuthAccount);




    }



}

