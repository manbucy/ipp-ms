package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.UserProfile;
import net.manbucy.ipp.cover.auth.mapper.user.UserProfileMapper;
import net.manbucy.ipp.cover.auth.service.user.UserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {

}
