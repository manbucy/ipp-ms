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
 * 系统许可表
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
@TableName("ipp_permission")
@Schema(name= "Permission对象", title= "系统许可表")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(name = "名称")
    @TableField("name")
    private String name;

    @Schema(name = "路径")
    @TableField("url")
    private String url;

    @Schema(name = "许可代码")
    @TableField("code")
    private String code;

    @Schema(name = "描述")
    @TableField("memo")
    private String memo;

    @Schema(name = "是否可用")
    @TableField("is_enabled")
    private Boolean enabled;


}
