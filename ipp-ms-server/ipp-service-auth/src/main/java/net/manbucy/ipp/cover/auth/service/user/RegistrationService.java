package net.manbucy.ipp.cover.auth.service.user;

import net.manbucy.ipp.cover.auth.controller.user.ao.VerifyCodeReceiver;

/**
 * 注册服务接口
 *
 * @author ManBu
 * @since 2021/10/30 15:38
 */
public interface RegistrationService {

    /**
     * 发送注册验证码
     *
     * @param verifyCodeReceiver 验证码接收者和
     */
    void sendVerifyCode(VerifyCodeReceiver verifyCodeReceiver);
}
