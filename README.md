# SpringCould的服务注册及服务调用

-
  - 一、SpringCould的简介
  - 二、SpringCould的服务注册及服务调用

## **一、SpringCould的简介**

**1、什么是SpringCloud**
Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简 化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、 熔断器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring并 没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框 架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给 开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。
Spring Cloud项目的官方网址：[http://projects.spring.io/spring-cloud/](http://projects.spring.io/spring-cloud/)

**2、SpringCloud与SpringBoot的关系**
Spring Boot 是 Spring 的一套快速配置脚手架，可以基于Spring Boot 快速开发单 个微服务，Spring Cloud是一个基于Spring Boot实现的云应用开发工具；Spring Boot专 注于快速、方便集成的单个微服务个体，Spring Cloud关注全局的服务治理框架； Spring Boot使用了默认大于配置的理念，很多集成方案已经帮你选择好了，能不配置就 不配置，Spring Cloud很大的一部分是基于Spring Boot来实现，可以不基于Spring Boot 吗？不可以。
Spring Boot可以离开Spring Cloud独立使用开发项目，但是Spring Cloud离不开 Spring Boot，属于依赖的关系。

**3、SpringCloud主要框架**

- 服务的发现 —— Netflix Eureka
- 服务的调用 ——Netflix Feign
- 熔断器 —— Netflix Hystrix
- 服务网关 —— Netflix Zuul
- 分布式配置 —— Spring Cloud Config
- 消息总线 —— Spring Cloud Bus

## **二、SpringCould的服务注册及服务调用**

**1、组件Eureka介绍**

Eureka是Spring Cloud Netflix微服务套件中的一部分，可以与Springboot构建的微服务很容易的整合起来。
Eureka包含了服务器端和客户端组件。服务器端，也被称作是服务注册中心，用于提供服务的注册与发现。Eureka支持高可用的配置，当集群中有分片出现故障时，Eureka就会转入自动保护模式，它允许分片故障期间继续提供服务的发现和注册，当故障分片恢复正常时，集群中其他分片会把他们的状态再次同步回来。
客户端组件包含服务消费者与服务生产者。在应用程序运行时，Eureka客户端向注册中心注册自身提供的服务并周期性的发送心跳来更新它的服务租约。同时也可以从服务端查询当前注册的服务信息并把他们缓存到本地并周期性的刷新服务状态。

**2、服务注册**

eureka-server是服务注册中心，用于提供服务的注册与发现，
eureka-client是服务提供者，创建进行注册测试

**3、服务调用**

使用Feign实现服务的调用。一个模块调用另外一个模块的的服务。