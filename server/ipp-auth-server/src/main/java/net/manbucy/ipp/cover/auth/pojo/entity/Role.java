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
@TableName("s_role")
public class Role extends CommonEntity {
    private static final long serialVersionUID = 1L;

    @TableField(value = "name")
    private String name;

    @TableField(value = "code")
    private String code;

    @TableField(value = "memo")
    private String memo;
}
