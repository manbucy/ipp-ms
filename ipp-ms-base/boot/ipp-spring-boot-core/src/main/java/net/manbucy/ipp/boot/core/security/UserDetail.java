package net.manbucy.ipp.boot.core.security;

import net.manbucy.ipp.boot.core.exception.BizException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author ManBu
 */
public class UserDetail extends User {
    private static final long serialVersionUID = 1L;
    private AbstractUserId userId;

    public UserDetail(AbstractUserId userId, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, enabled, enabled, enabled, authorities);
        this.userId = userId;
    }

    public AbstractUserId getUserId() {
        return userId;
    }


    public Long getSystemUserId() {
        if (userId instanceof SystemUserId) {
            return ((SystemUserId) userId).getId();
        }
        throw new BizException("the user is not system user");
    }

}
