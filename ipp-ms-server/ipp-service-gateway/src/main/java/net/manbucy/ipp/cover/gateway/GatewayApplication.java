package net.manbucy.ipp.cover.gateway;

import net.manbucy.ipp.cloud.document.annotation.EnableDocumentServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ManBu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDocumentServer
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
