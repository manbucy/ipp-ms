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
public class UserDTO {
    private Long userId;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean locked;

    private List<RoleDTO> roles;
}
