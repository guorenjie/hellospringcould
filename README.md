# SpringCould的学习

## 一、SpringCould的简介

### 1、什么是SpringCloud

Spring Cloud是一系列框架的有序集合。它利用Spring Boot的开发便利性巧妙地简 化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、 熔断器、数据监控等，都可以用Spring Boot的开发风格做到一键启动和部署。Spring并 没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框 架组合起来，通过Spring Boot风格进行再封装屏蔽掉了复杂的配置和实现原理，最终给 开发者留出了一套简单易懂、易部署和易维护的分布式系统开发工具包。
Spring Cloud项目的官方网址：[http://projects.spring.io/spring-cloud/](http://projects.spring.io/spring-cloud/)

### 2、SpringCloud与SpringBoot的关系


Spring Boot 是 Spring 的一套快速配置脚手架，可以基于Spring Boot 快速开发单 个微服务，Spring Cloud是一个基于Spring Boot实现的云应用开发工具；Spring Boot专 注于快速、方便集成的单个微服务个体，Spring Cloud关注全局的服务治理框架； Spring Boot使用了默认大于配置的理念，很多集成方案已经帮你选择好了，能不配置就 不配置，Spring Cloud很大的一部分是基于Spring Boot来实现，可以不基于Spring Boot 吗？不可以。
Spring Boot可以离开Spring Cloud独立使用开发项目，但是Spring Cloud离不开 Spring Boot，属于依赖的关系。

### 3、SpringCloud主要框架

- 服务的发现 —— Netflix Eureka
- 服务的调用 ——RestTemplate和Feign
- 熔断器 —— Netflix Hystrix
- 服务网关 —— Netflix Zuul
- 分布式配置 —— Spring Cloud Config
- 消息总线 —— Spring Cloud Bus

## 二、项目介绍

### 1、创建Maven项目

maven创建父项目
创建项目名为:hellospringcould
创建父项目的好处:管理子模块和公共依赖（依赖版本）

### 2、搭建服务注册中心服务注册中心

**eureka-server是服务注册中心，用于提供服务的注册与发现**
**eureka-client是服务提供者，创建进行注册测试**

创建eureka-server子模块：
1. 引入Eureka Server依赖
2. 启动类添加@EnableEurekaServer注解
3. 增加bootstrap.yml配置文件

创建eureka-client子模块：
1. 引入spring的web和eureka-client依赖
2. 启动类添加@EnableDiscoveryClient注解
3. 增加bootstrap.yml配置文件（如需开启健康检查：引入spring-boot-starter-actuator依赖）

### 3、服务调用

**使用Feign实现服务的调用。一个模块调用另外一个模块的的服务。**
创建两个eureka-client子模块eureka-user和eureka-book,在eureka-book服务中调用eureka-user服务

1. 在eureka-client的基础上加入正常项目依赖（eureka-book额外加入spring-cloud-starter-openfeign）
2. 在eureka-client的基础上加入正常的项目开发以及配置（eureka-book启动类加入@EnableFeignClients注解,新建eureka-user客户端接口用来调用eureka-user服务）
3. 此处也可使用RestTemplate实现服务调用，,可加入客户端负载均衡器Ribbon实现本地负载均衡（代码未实现）