package net.manbucy.ipp.cover.auth.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import net.manbucy.ipp.boot.data.mybatisplus.entity.BaseEntity;

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
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "email")
    private String email;

    @TableField(value = "is_locked")
    private Boolean locked;
}
