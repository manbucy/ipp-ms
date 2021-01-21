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
@TableName("s_role_permission")
public class RolePermission extends CommonEntity {
    private static final long serialVersionUID = 1L;

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "permission_id")
    private Long permissionId;

}
