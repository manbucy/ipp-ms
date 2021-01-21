package net.manbucy.ipp.cover.auth.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ManBu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO{
    private static final long serialVersionUID = 1L;

    private Long permissionId;

    private String name;

    private String url;

    private String code;

    private String memo;

    private Boolean enabled;
}
