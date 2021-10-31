package net.manbucy.ipp.cover.auth.service.user.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.manbucy.ipp.boot.core.exception.BizException;
import net.manbucy.ipp.cover.auth.controller.user.ao.UserThirdAuthType;
import net.manbucy.ipp.cover.auth.mapper.user.UserMapper;
import net.manbucy.ipp.cover.auth.pojo.entity.user.UserEntity;
import net.manbucy.ipp.cover.auth.pojo.enums.AuthErrorCode;
import net.manbucy.ipp.cover.auth.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-09-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public void validateThirdAuthTypeAccountValue(UserThirdAuthType type, String value) {
        switch (type) {
            case MOBILE:
                if (!Validator.isMobile(value)) {
                    throw new BizException(AuthErrorCode.MOBILE_CHECK_ERROR.code, AuthErrorCode.MOBILE_CHECK_ERROR.msg);
                }
                break;
            case EMAIL:
                if (!Validator.isEmail(value)) {
                    throw new BizException(AuthErrorCode.EMAIL_CHECK_ERROR.code, AuthErrorCode.EMAIL_CHECK_ERROR.msg);
                }
                break;
            default:
                throw new BizException("未知类型");
        }
    }

    @Override
    public void validateThirdAuthAccountRegistered(UserThirdAuthType type, String value) {
        this.validateThirdAuthTypeAccountValue(type, value);
        AuthErrorCode errorCode;
        UserEntity userQuery = new UserEntity();
        switch (type) {
            case MOBILE:
                userQuery.setMobile(value);
                errorCode = AuthErrorCode.MOBILE_EXIST_ERROR;
                break;
            case EMAIL:
                userQuery.setEmail(value);
                errorCode = AuthErrorCode.EMAIL_EXIST_ERROR;
                break;
            default:
                throw new BizException("未知类型");
        }
        Integer count = getBaseMapper().selectCount(new QueryWrapper<>(userQuery));
        if (count == null || count > 0) {
            throw new BizException(errorCode.code, errorCode.msg);
        }
    }
}
