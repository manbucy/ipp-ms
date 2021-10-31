package net.manbucy.ipp.cover.auth.service.captcha;

/**
 * @author ManBu
 * @since 2021/10/31 10:58
 */
public interface VerifyCodeService {

    /**
     * 通过邮箱发送验证码
     *
     * @param email 需要发送的邮箱
     * @param key   验证码在redis中存储的key
     */
    void sendEmailVerifyCode(String email, String key);


    /**
     * 验证邮箱的验证码
     *
     * @param email 邮箱
     * @param key   验证码
     */
    void validateEmailVerifyCode(String email, String key);

}
