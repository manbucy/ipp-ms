package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.Role;
import net.manbucy.ipp.cover.auth.mapper.user.RoleMapper;
import net.manbucy.ipp.cover.auth.service.user.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
