package net.manbucy.ipp.cover.auth.pojo.entity.user;

import net.manbucy.ipp.boot.data.mybatisplus.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author ManBu
 * @since 2021-08-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("ipp_user")
@Schema(name= "User对象", title= "系统用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户名")
    @TableField("username")
    private String username;

    @Schema(name = "密码")
    @TableField("password")
    private String password;

    @Schema(name = "姓名")
    @TableField("name")
    private String name;

    @Schema(name = "手机号码")
    @TableField("phone")
    private String phone;

    @Schema(name = "电子邮件")
    @TableField("email")
    private String email;

    @Schema(name = "头像地址")
    @TableField("avatar")
    private String avatar;

    @Schema(name = "是否禁用")
    @TableField("is_locked")
    private Boolean locked;


}
