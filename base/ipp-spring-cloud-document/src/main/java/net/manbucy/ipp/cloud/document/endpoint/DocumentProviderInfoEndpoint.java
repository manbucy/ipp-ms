package net.manbucy.ipp.cloud.document.endpoint;

import lombok.RequiredArgsConstructor;
import net.manbucy.ipp.cloud.document.config.properties.DocumentProviderProperties;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author ManBu
 * @date 2021/8/9 21:48
 */
@RequiredArgsConstructor
@Endpoint(id = DocumentProviderInfoEndpoint.ENDPOINT_ID)
public class DocumentProviderInfoEndpoint {
    public static final String ENDPOINT_ID = "doc-info";

    private final DocumentProviderProperties documentProviderProperties;

    @ReadOperation
    public DocumentProviderProperties providerInfo() {
        return documentProviderProperties;
    }
}
