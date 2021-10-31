package net.manbucy.ipp.cover.auth.service.user;

import net.manbucy.ipp.cover.auth.controller.user.ao.UserThirdAuthType;
import net.manbucy.ipp.cover.auth.pojo.entity.user.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author ManBu
 * @since 2021-09-25
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 校验第三方认证类型的值
     *
     * @param type  待校验的类型
     * @param value 待校验的值
     */
    void validateThirdAuthTypeAccountValue(UserThirdAuthType type, String value);

    /**
     * 第三方认证账号(手机号、电子邮箱)是否已经被注册
     *
     * @param type  第三方认证类型
     * @param value 第三方认证账号(手机号、电子邮箱)
     */
    void validateThirdAuthAccountRegistered(UserThirdAuthType type, String value);
}
