# base-service-api-gateway 网关
## 基于Spring Cloud Gateway API网关

## 实现功能
* 支持API参数校验 （已完成）
* 支持appkey授权服务校验 （已完成）
* 支持接口熔断降级配置已加入阿里巴巴 sentinel （已完成）
* 支持管理后台动态配置 （TODO）

## 实现原理
* 在分布式环境下 采用与Eureka Server相同的内存缓存更新策略，配置内容保存在数据库中。

![架构图](./gateway.png)

