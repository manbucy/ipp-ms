package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.UserRoleEntity;
import net.manbucy.ipp.cover.auth.mapper.user.UserRoleMapper;
import net.manbucy.ipp.cover.auth.service.user.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-09-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {

}
