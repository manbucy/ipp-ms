package net.manbucy.ipp.boot.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author ManBu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommonEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
