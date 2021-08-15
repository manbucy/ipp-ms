package net.manbucy.ipp.cover.auth.mapper.user;

import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import net.manbucy.ipp.cover.auth.pojo.entity.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户详情信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDTO findUserDetailByUsername(String username);
}
