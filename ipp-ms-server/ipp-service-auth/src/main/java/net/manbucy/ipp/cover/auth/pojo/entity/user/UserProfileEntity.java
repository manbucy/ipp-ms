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
 * 
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
@TableName("s_user_profile")
@Schema(name= "UserProfile对象", title= "")
public class UserProfileEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "用户Id")
    @TableField("user_id")
    private Long userId;

    @Schema(name = "用户昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(name = "用户头像地址")
    @TableField("avatar")
    private String avatar;

    @Schema(name = "个性签名")
    @TableField("signature")
    private String signature;


}
