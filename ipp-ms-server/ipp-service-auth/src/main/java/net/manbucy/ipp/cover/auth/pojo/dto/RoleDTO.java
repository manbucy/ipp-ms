package net.manbucy.ipp.cover.auth.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ManBu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private static final long serialVersionUID = 1L;

    private Long roleId;

    private String name;

    private String code;

    private String memo;

    private List<PermissionDTO> permissions;
}
