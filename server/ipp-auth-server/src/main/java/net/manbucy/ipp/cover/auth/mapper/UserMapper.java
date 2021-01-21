package net.manbucy.ipp.cover.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.manbucy.ipp.cover.auth.pojo.entity.User;
import net.manbucy.ipp.cover.auth.pojo.dto.UserDTO;
import org.apache.ibatis.annotations.Select;

/**
 * @author ManBu
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户姓名查询用户信息
     * @param username 用户姓名
     * @return 用户信息
     */
    @Select("select u.*, u.is_locked as locked, u.is_deleted as deleted  from s_user u where u.username = #{username} " +
            "and u.is_deleted = 0")
    User findUserByUsername(String username);

    /**
     * 根据用户姓名查询用户信息
     * @param username 用户姓名
     * @return 用户信息
     */
    UserDTO findUserDetailByUsername(String username);
}
