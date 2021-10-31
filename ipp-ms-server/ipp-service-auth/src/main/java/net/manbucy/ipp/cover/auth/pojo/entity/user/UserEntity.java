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
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("s_user")
@Schema(name= "User对象", title= "系统用户表")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户名")
    @TableField("username")
    private String username;

    @Schema(name = "密码")
    @TableField("password")
    private String password;

    @Schema(name = "手机号码")
    @TableField("mobile")
    private String mobile;

    @Schema(name = "邮箱地址")
    @TableField("email")
    private String email;

    @Schema(name = "是否禁用")
    @TableField("is_locked")
    private Integer isLocked;


}
