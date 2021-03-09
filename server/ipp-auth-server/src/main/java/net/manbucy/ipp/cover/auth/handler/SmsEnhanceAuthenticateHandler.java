package net.manbucy.ipp.cover.auth.handler;

import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.core.security.SystemUserId;
import net.manbucy.ipp.boot.core.security.UserDetail;
import net.manbucy.ipp.cloud.security.provider.enhance.AbstractEnhanceAuthenticateHandler;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ManBu
 */
@Slf4j
@Component
public class SmsEnhanceAuthenticateHandler extends AbstractEnhanceAuthenticateHandler {
    private static final String AUTH_TYPE = "sms";

    private static final String PHONE_PARAMETER_KEY = "phone";
    private static final String CODE_PARAMETER_KEY = "sms_code";

    @Override
    protected String getAuthType() {
        return AUTH_TYPE;
    }

    @Override
    protected UserDetails retrieveUser(Map<String, String> credentials) {
        String phone = credentials.get(PHONE_PARAMETER_KEY);
        String code = credentials.get(CODE_PARAMETER_KEY);

        log.info("SmsEnhanceAuthenticateHandler.retrieveUser phone: {}, code: {}", phone, code);

        UserDetails userDetails = new UserDetail(new SystemUserId(1L), phone, code, true, AuthorityUtils.createAuthorityList("ROLE_visitor"));

        return userDetails;
    }
}
