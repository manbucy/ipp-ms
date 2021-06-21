package net.manbucy.ipp.boot.core.security;

/**
 * @author ManBu
 */
public class SystemUserId extends AbstractUserId {
    private static final long serialVersionUID = 1L;

    public static final String USER_TYPE = "system";

    public SystemUserId(Object id) {
        super(USER_TYPE, id);
    }

    public Long getId() {
        return (Long) id;
    }
}
