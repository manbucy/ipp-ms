package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.UserProfileEntity;
import net.manbucy.ipp.cover.auth.mapper.user.UserProfileMapper;
import net.manbucy.ipp.cover.auth.service.user.UserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-09-25
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfileEntity> implements UserProfileService {

}
