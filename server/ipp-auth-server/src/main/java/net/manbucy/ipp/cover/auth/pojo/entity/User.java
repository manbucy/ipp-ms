package net.manbucy.ipp.cover.auth.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import net.manbucy.ipp.boot.data.entity.CommonEntity;

/**
 * @author ManBu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("s_user")
public class User extends CommonEntity {
    private static final long serialVersionUID = 1L;
    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "name")
    private String name;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "is_locked")
    private Boolean locked;
}
