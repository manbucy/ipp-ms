package net.manbucy.ipp.cover.product.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ManBu
 */
@RestController
@RequestMapping("/hello")
@RefreshScope
public class HelloController {

    @Value("${product.name}")
    private String productName;

    @Value("${product.code}")
    private String productCode;

    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return String.format("hello [%S] -- from product", string);
    }

    @GetMapping("/product")
    public String product() {
        return String.format("产品代码[%s], 产品名称[%s]", productCode, productName);
    }
}
