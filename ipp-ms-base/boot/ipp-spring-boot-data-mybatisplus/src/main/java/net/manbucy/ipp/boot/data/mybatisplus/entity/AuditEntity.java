package net.manbucy.ipp.boot.data.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ManBu
 */
@Data
public class AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    @TableField(value = "version")
    private Integer version;

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_deleted")
    private Boolean deleted;
}
