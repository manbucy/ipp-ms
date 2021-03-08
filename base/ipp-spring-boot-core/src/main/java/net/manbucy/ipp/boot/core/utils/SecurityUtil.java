package net.manbucy.ipp.boot.core.utils;

import net.manbucy.ipp.boot.core.security.UserDetail;
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
