package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.Permission;
import net.manbucy.ipp.cover.auth.mapper.user.PermissionMapper;
import net.manbucy.ipp.cover.auth.service.user.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统许可表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
