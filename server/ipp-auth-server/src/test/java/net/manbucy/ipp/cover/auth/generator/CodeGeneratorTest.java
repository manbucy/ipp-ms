package net.manbucy.ipp.cover.auth.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import net.manbucy.ipp.boot.data.mybatisplus.generator.CodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author ManBu
 * @date 2021/8/15 11:16
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CodeGeneratorTest {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Test
    public void test_code_generator() {
        CodeGenerator codeGenerator = CodeGenerator.builder()
                .dataSourceConfig(getDataSourceConfig())
                .parentPackage("net.manbucy.ipp.cover.auth")
                .moduleName("user")
                .tableNames(new String[]{"ipp_permission", "ipp_role", "ipp_role_permission", "ipp_user", "ipp_user_profile", "ipp_user_role"})
//                .fileOverride(true)
                .build();

        codeGenerator.generate();
    }

    private DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceProperties.getUrl());
        dsc.setDriverName(dataSourceProperties.getDriverClassName());
        dsc.setUsername(dataSourceProperties.getUsername());
        dsc.setPassword(dataSourceProperties.getPassword());
        return dsc;
    }
}
