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
 * 系统角色表
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
@TableName("ipp_role")
@Schema(name= "Role对象", title= "系统角色表")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "角色名")
    @TableField("name")
    private String name;

    @Schema(name = "角色代码")
    @TableField("code")
    private String code;

    @Schema(name = "角色描述")
    @TableField("memo")
    private String memo;


}
