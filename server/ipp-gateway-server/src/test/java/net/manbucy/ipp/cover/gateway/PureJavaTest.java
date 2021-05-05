package net.manbucy.ipp.cover.gateway;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

@Slf4j
public class PureJavaTest {
    @Test
    public void test_get_uuid() {
        log.info("uuid: {}", IdUtil.fastUUID());
    }

    @Test
    public void test_aes_encrypt_and_decrypt() {
        String aesKey = "076776ef4b774ef5";
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, aesKey.getBytes(StandardCharsets.UTF_8));

        String plantText = "{\"username\":\"admin\",\"password\":\"admin.123456\",\"email\":\"1412039603@qq.com\",\"verifyCode\":\"198251\"}";

        String encryptStr = aes.encryptBase64(plantText);
        log.info("encryptStr: {}", encryptStr);

        String decryptStr = aes.decryptStr(encryptStr);
        log.info("decryptStr: {}", decryptStr);
    }
}
