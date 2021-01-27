package net.manbucy.ipp.cover.product;

import net.manbucy.ipp.cloud.security.annotation.EnableDefaultResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ManBu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDefaultResourceServer
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class);
    }
}
