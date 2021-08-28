package net.manbucy.ipp.cloud.document.constants;

import springfox.documentation.spi.DocumentationType;

/**
 * @author ManBu
 * @date 2021/8/9 21:11
 */
public enum SwaggerDocumentVersion {
    /**
     * {@link springfox.documentation.spi.DocumentationType#SWAGGER_12}
     */
    V1,
    /**
     * {@link springfox.documentation.spi.DocumentationType#SWAGGER_2}
     */
    V2,
    /**
     * {@link springfox.documentation.spi.DocumentationType#OAS_30}
     */
    V3;

    public static DocumentationType documentationType(SwaggerDocumentVersion version) {
        if (version == null) {
            return DocumentationType.OAS_30;
        }
        switch (version) {
            case V1:
                return DocumentationType.SWAGGER_12;
            case V2:
                return DocumentationType.SWAGGER_2;
            case V3:
                return DocumentationType.OAS_30;
            default:
                return DocumentationType.OAS_30;
        }
    }
}
