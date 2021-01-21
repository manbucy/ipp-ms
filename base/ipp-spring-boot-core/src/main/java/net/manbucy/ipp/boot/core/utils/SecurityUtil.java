package net.manbucy.ipp.boot.core.utils;

import net.manbucy.ipp.boot.core.security.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ManBu
 */
public class SecurityUtil {
    public static UserDetail getUser() {
        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail instanceof UserDetail ? ((UserDetail) userDetail) : null;
    }
}
