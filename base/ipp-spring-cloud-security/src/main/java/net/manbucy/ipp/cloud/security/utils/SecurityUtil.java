package net.manbucy.ipp.cloud.security.utils;

import net.manbucy.ipp.cloud.security.core.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author ManBu
 */
public class SecurityUtil {
    public static Optional<UserDetail> getUser() {
        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail instanceof UserDetail ? Optional.of((UserDetail)userDetail) : Optional.empty();
    }
}
