package net.manbucy.ipp.cover.content;

import net.manbucy.ipp.cloud.document.annotation.EnableDocumentProvider;
import net.manbucy.ipp.cloud.security.annotation.EnableDefaultResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ManBu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDocumentProvider
@EnableDefaultResourceServer
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class);
    }
}
