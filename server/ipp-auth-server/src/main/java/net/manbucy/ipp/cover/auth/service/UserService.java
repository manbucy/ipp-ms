package net.manbucy.ipp.cover.auth.service;

import net.manbucy.ipp.cover.auth.controller.user.ao.RegInfo;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;

/**
 * @author ManBu
 */
public interface UserService {
    /**
     * 校验用户名
     *
     * @param username 待校验的用户名
     * @return 校验结果
     */
    RegistrationResult checkUsername(String username);

    /**
     * 校验用户手机号码
     *
     * @param phone 待校验的手机号码
     * @return 校验结果
     */
    RegistrationResult checkUserPhone(String phone);

    /**
     * 校验用户邮箱
     *
     * @param email 待校验的邮箱
     * @return 校验结果
     */
    RegistrationResult checkUserEmail(String email);

    /**
     * 发送注册验证码给手机
     *
     * @param phone 接收验证码的手机
     * @return 发送结果
     */
    RegistrationResult sendRegVerifyCodeToPhone(String phone);

    /**
     * 发送注册验证码给邮箱
     *
     * @param email 接收验证码的邮箱
     * @return 发送结果
     */
    RegistrationResult sendRegVerifyCodeToEmail(String email);

    /**
     * 使用手机号码注册
     *
     * @param regInfo 注册信息
     * @return 注册结果
     */
    RegistrationResult registrationByPhone(RegInfo regInfo);

    /**
     * 使用邮箱注册
     *
     * @param regInfo 注册信息
     * @return 注册结果
     */
    RegistrationResult registrationByEmail(RegInfo regInfo);
}
