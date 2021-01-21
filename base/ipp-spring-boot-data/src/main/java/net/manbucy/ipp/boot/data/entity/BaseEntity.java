package net.manbucy.ipp.boot.data.entity;

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
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField(value = "create_user")
    private String createUser;
    
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_user")
    private String updateUser;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @Version
    @TableField(value = "version")
    private Integer version;

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_deleted")
    private Boolean deleted;
}
