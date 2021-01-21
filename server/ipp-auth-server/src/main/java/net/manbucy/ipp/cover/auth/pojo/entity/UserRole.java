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
@TableName("s_user_role")
public class UserRole extends CommonEntity {
    private static final long serialVersionUID = 1L;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "role_id")
    private Long roleId;
}
