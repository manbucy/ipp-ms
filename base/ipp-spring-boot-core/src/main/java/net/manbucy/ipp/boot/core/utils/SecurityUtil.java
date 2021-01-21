package net.manbucy.ipp.boot.core.utils;

import net.manbucy.ipp.boot.core.security.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ManBu
 */
public class SecurityUtil {
    public static UserDetail getUser() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
