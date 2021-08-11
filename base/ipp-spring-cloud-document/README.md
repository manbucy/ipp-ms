# ipp-spring-cloud-document

在SpringCloud模式下，各个服务都会有自己的文档地址`http://host:port/documentation/base-url/swagger-ui`, 本项目旨在将各个服务的文档聚合在网关显示

## 使用方式

现有如下服务：

- `Gateway Service` url: `http://localhost:8000/xxxx`
- `Auth Service` url: `http://localhost:8001/xxxx`
- `Content Service` url: `http://localhost:8002/xxxx`  
  通过网关访问Auth、Content的地址为 `http://localhost:8000/auth/xxxx`、`http://localhost:8000/content/xxxx`

### 普通服务

也就是普通的业务服务/提供具体文档的服务(Auth、Content)。  
首先在项目启动的`SpringBootApplication`上添加`@EnableDocumentProvider`注解

```java

@SpringBootApplication
@EnableDocumentProvider
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }
}
```

在yaml添加配置

```yaml
springfox:
  documentation:
    base-url: /documentation
    swagger:
      v2:
        path: ${springfox.documentation.base-url}/v2/api-docs
    open-api:
      v3:
        path: ${springfox.documentation.base-url}/v3/api-docs
    swagger-ui:
      base-url: ${springfox.documentation.base-url}
    provider:
      base-package: "net.manbucy.ipp.cover.auth"
      group-name: "ipp-auth-api"
      version: "V3"
      context: "auth"
```

上述配置中`springfox.documentation.base-url`、`springfox.documentation.swagger.*`、`springfox.documentation.open-api.*`
、`springfox.documentation.swagger-ui.*`均为springfox的官方配置，按照自己的需要配置即可。  
`springfox.documentation.provider.*`是需要提供的文档的具体信息

- `provider.base-package` 扫描此包下的接口文档
- `provider.group-name` 分组名称
- `provider.version` 文档版本`V1 V2 V3`
- `provider.context` 通过网关访问此服务需要的上下文, 比如auth服务通过网关访问时需要在本身地址前添加`auth`

按照上述配置后，auth服务的文档地址为`http://host:port/documentation/swagger-ui`,
接口详情地址为: `http://host:port/documentation/v3/api-docs?group=ipp-auth-api`

### 网关服务

在项目启动的`SpringBootApplication`上添加`@EnableDocumentServer`注解

```java

@SpringBootApplication
@EnableDocumentServer
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }
}
```

在yaml添加配置

```yaml
# 网关路由配置
spring:
  cloud:
    gateway:
      routes:
        - id: ipp-auth-server
          uri: lb://ipp-auth-server
          predicates:
            - Path=/auth/**
        - id: ipp-content-server
          uri: lb://ipp-content-server
          predicates:
            - Path=/content/**

# swagger文档配置
springfox:
  documentation:
    base-url: /documentation
    swagger:
      v2:
        path: ${springfox.documentation.base-url}/v2/api-docs
    open-api:
      v3:
        path: ${springfox.documentation.base-url}/v3/api-docs
    swagger-ui:
      base-url: ${springfox.documentation.base-url}
```

此处网关只配置了swagger的文档访问地址，网关并没有提供具体的文档。

## 原理

1、当添加`@EnableDocumentProvider`注解后，该服务会暴露一个EndPoint(`http://host:port/actuator/doc-info`), 该接口会返回此服务的文档信息

```json
[
  {
    "name": "ipp-auth-api",
    "url": "/auth/documentation/v3/api-docs?group=ipp-auth-api",
    "swaggerVersion": "3.0.3",
    "location": "/auth/documentation/v3/api-docs?group=ipp-auth-api"
  }
]
```

2、当添加`@EnableDocumentServer`后，此服务的`SwaggerResourcesProvider`会被重写

```java
public class IppSwaggerResourceProvider implements SwaggerResourcesProvider, ApplicationContextAware {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> serviceList = discoveryClient.getServices();
        for (String serviceId : serviceList) {
            // get SwaggerResource from  http://serviceId/actuator/doc-info
            resources.addAll(swaggerResource);
        }
        return resources;
    }
}
```

原理就是遍历当前注册中心所有的服务的`/actuator/doc-info`端点，获取swagger-info, 然后在组装成swaggerResource并返回。
