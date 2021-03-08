package net.manbucy.ipp.boot.core.security;

/**
 * @author ManBu
 */
public class SystemUserId extends AbstractUserId {
    public static final String USER_TYPE = "system";

    public SystemUserId(Object id) {
        super(USER_TYPE, id);
    }

    public Long getId() {
        return (Long) id;
    }
}
