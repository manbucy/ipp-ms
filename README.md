# ipp-ms

**Imaginary Perfect Products**   
**子虚物优**


:pencil: [待办列表](doc/todo/index.md)

## 模块概览
| 名称 | 目录 | 介绍 | 端口 | 访问地址 |
| :--- | :---: | :--- | :---: | :--- |
| ipp-spring-boot-dependence | base | springboot相关基础依赖 | - | - |
| ipp-spring-cloud-dependence | base | springcloud相关基础依赖 | - | - |
| ipp-spring-boot-core | base | springboot项目基础, 为其它服务提供公共基础代码 | - | - |
| ipp-service-gateway | service | springcloud网关服务 | 48000 | http://localhost:48000/ |
| ipp-service-auth | service | 权限服务 | 48010 | http://localhost:48000/auth/ |
| ipp-service-admin | service | 后台管理 | 48020 | http://localhost:48000/admin/ |
| ipp-service-product | service | 产品服务 | 48030 | http://localhost:48000/product/ |
| ipp-service-content | service | 内容服务 | 48040 | http://localhost:48000/content/ |


### Swagger文档地址
| 模块名 | 文档地址 |   
| :---: | :--- |  
| ipp-service-gateway | http://localhost:48000/documentation/swagger-ui/ |  
| ipp-service-auth | http://localhost:48000/auth/documentation/swagger-ui/ |  
| ipp-service-admin | http://localhost:48000/admin/documentation/swagger-ui/ |  
| ipp-service-product | http://localhost:48000/product/documentation/swagger-ui/ |  
| ipp-service-content | http://localhost:48000/content/documentation/swagger-ui/ |


# 鸣谢
感谢 [JetBrains](https://www.jetbrains.com/) 对本项目的支持。  
![JetBrains](doc/image/jetbrains.svg)