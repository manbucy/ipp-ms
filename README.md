# ipp-ms
**Inexistent Product Platform**
**子虚物优**

## 模块概览
| 名称 | 目录 | 介绍 | 端口 | 访问地址 |
| :- | :-: | :- | :-: | :- |
| ipp-spring-boot-dependence | base | springboot相关基础依赖 | - | - |
| ipp-spring-cloud-dependence | base | springcloud相关基础依赖 | - | - |
| ipp-spring-boot-core | base | springboot项目基础, 为其它服务提供公共基础代码 | - | - |
| ipp-gateway-server | server | springcloud网关服务 | 48000 | http://localhost:48000/ |
| ipp-auth-server | server | 权限服务 | 48010 | http://localhost:48000/auth/ |
| ipp-admin-server | server | 后台管理 | 48020 | http://localhost:48000/admin/ |
| ipp-product-server | server | 产品服务 | 48030 | http://localhost:48000/product/ |
| ipp-content-server | server | 内容服务 | 48040 | http://localhost:48000/content/ |


### Swagger文档地址
| 模块名 | 文档地址 |
| :-: | :- |
| ipp-gateway-server | http://localhost:48000/documentation/swagger-ui/ |
| ipp-auth-server | http://localhost:48000/auth/documentation/swagger-ui/ |
| ipp-admin-server | http://localhost:48000/admin/documentation/swagger-ui/ |
| ipp-product-server | http://localhost:48000/product/documentation/swagger-ui/ |
| ipp-content-server | http://localhost:48000/content/documentation/swagger-ui/ |