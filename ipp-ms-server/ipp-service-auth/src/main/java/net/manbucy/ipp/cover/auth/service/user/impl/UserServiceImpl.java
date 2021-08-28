package net.manbucy.ipp.cover.auth.service.user.impl;

import net.manbucy.ipp.cover.auth.pojo.entity.user.User;
import net.manbucy.ipp.cover.auth.mapper.user.UserMapper;
import net.manbucy.ipp.cover.auth.service.user.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
