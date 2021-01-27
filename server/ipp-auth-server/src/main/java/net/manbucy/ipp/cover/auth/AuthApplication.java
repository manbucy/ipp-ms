package net.manbucy.ipp.cover.auth;

import net.manbucy.ipp.cloud.security.annotation.EnableDefaultResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 * @author ManBu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDefaultResourceServer
@MapperScan("net.manbucy.ipp.cover.auth.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }
}
