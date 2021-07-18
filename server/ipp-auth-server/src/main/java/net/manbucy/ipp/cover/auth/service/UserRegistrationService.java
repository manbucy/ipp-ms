package net.manbucy.ipp.cover.auth.service;

import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegCheckItem;
import net.manbucy.ipp.cover.auth.controller.user.vo.reg.RegCheckResult;
import net.manbucy.ipp.cover.auth.controller.user.ao.reg.RegInfo;
import net.manbucy.ipp.cover.auth.controller.user.vo.reg.VerifyCodeSendResult;
import net.manbucy.ipp.cover.auth.pojo.dto.RegistrationResult;

/**
 * @author ManBu
 */
public interface UserRegistrationService {

    /**
     * 校验注册信息项
     * @param regCheckItem 待校验的注册信息项
     * @return 校验结果
     */
    RegCheckResult checkRegInfo(RegCheckItem regCheckItem);

    /**
     * 发送验证码
     * @param regInfo 注册信息
     * @return 发送结果
     */
    VerifyCodeSendResult sendRegVerifyCodeToAuthNum(RegInfo regInfo);


    /**
     * 注册
     *
     * @param regInfo 注册信息
     * @return 注册结果
     */
    RegistrationResult register(RegInfo regInfo);

}
