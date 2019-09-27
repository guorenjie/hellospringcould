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
（1）搭建空的maven工程，以后把其它模块都放入下面创建完成后，将src目录删除，因为这个工程只是为了放SpringCould的其他模块
（2）搭建服务注册中心服务注册中心 （eureka-server)
Eureka Server是基于springboot的，只要启动一个springboot就可以了。start.spring.io提供了一系列启动模板。创建module，选择Spring initializer：
设置artifact Id值为eureka-server，注意group id与后面要建的模块要统一！！加入Eureka Server组件自动生成的pom.xml文件不用改动，修改启动类EurekaServerApplication.java，添加@EnableEurekaServer
在默认情况下，服务注册中心也会把自己当做是一个服务，将自己注册进服务注册中心，所以我们可以通过配置来禁用他的客户端注册行为，修改application.properties文件

#我这个项目只提供给别人注册服务的平台,我自己不注册,也不需要用别人的服务

spring.application.name=eureka-server

#服务注册中心端口号

server.port=8080

#服务注册中心实例的主机名

eureka.instance.hostname=localhost

#是否向服务注册中心注册自己

eureka.client.register-with-eureka=false

#是否检索服务

eureka.client.fetch-registry=false

#服务注册中心的配置内容，指定服务注册中心的位置

eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9
- 10
- 11
- 12

启动应用，并访问http://localhost:8080/即可看到Eureka信息面板，如下：从上图看到，在&quot;Instances currently registered with Eureka&quot;信息中，没有一个实例，说明目前还没有服务注册。接下来创建一个服务提供者eureka-client进行注册测试。

（3）创建并注册服务提供者 Eureka Client
创建方式如eureka-server模块类似；在选择组件的时候需要选择对应的组件
注意要选择Web组件或者其它能够持久运行的。不然会注册失败！！！
启动类EurekaClientApplication.java添加@EnableDiscoveryClient注解以实现Eureka中的DiscoveryClient实现。

- @EnableEurekaClient和@EnableDiscoveryClient的区别
spring cloud中discovery service有许多种实现（eureka、consul、zookeeper等等），@EnableDiscoveryClient基于spring-cloud-commons,@EnableEurekaClient基于spring-cloud-netflix。

就是如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。

修改配置文件application.properties

spring.application.name=eureka-client

server.port=8090

# 这个是固定死的,这是我们要注册到哪里去

eureka.client.serviceUrl.defaultZone=http://localhost:8080/eureka/

- 1
- 2
- 3
- 4

测试是否能够注册
先启动：eureka-server 然后启动eureka-client
发现多了一个EUREKA-CLIENT，说明注册成功了

其它问题：
运行一段时间后，出现如图：停止eureka-client后也没有删除节点。
自我保护模式打开时，已关停节点是会一直显示在 Eureka 首页的，关闭自我保护模式后，由于其默认的心跳周期比较长等原因，要过一会儿才会发现已关停节点被自动踢出了
若想尽快的及时踢出，那就只有修改默认的心跳周期参数了
注册中心eureka-server的配置文件application.properties中修改为

spring.application.name=eureka-server

#服务注册中心端口号

server.port=8080

#服务注册中心实例的主机名

eureka.instance.hostname=localhost

#关闭自我保护

eureka.server.enableSelfPreservation=false

# 续期时间，即扫描失效服务的间隔时间（缺省为60\*1000ms）

eureka.server.eviction-interval-timer-in-ms:1000

#是否向服务注册中心注册自己

eureka.client.register-with-eureka=false

#是否检索服务

eureka.client.fetch-registry=false

#服务注册中心的配置内容，指定服务注册中心的位置

eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9
- 10
- 11
- 12
- 13
- 14
- 15

Eureka客户端eureka-client的配置文件application.properties中修改为

spring.application.name=eureka-client

server.port=8090

# 心跳时间，即服务续约间隔时间（缺省为30s）

eureka.instance.lease-renewal-interval-in-seconds:5

# 发呆时间，即服务续约到期时间（缺省为90s）

eureka.instance.lease-expiration-duration-in-seconds:15

# 开启健康检查（依赖spring-boot-starter-actuator）

eureka.client.healthcheck.enabled:true

eureka.client.serviceUrl.defaultZone=http://localhost:8080/eureka/

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9

eureka-client的pom.xml增加

\&lt;dependency\&gt;

   \&lt;groupId\&gt;org.springframework.boot\&lt;/groupId\&gt;

   \&lt;artifactId\&gt;spring-boot-starter-actuator\&lt;/artifactId\&gt;

\&lt;/dependency\&gt;

- 1
- 2
- 3
- 4

修改后，关闭eureka-client，注册中心就会很快删除节点

**3、服务调用**

使用Feign实现服务的调用。一个模块调用另外一个模块的的服务。
假如两个项目，一个学生项目，一个年级项目，想在学生项目中查询年级的所有信息，一下来实现这个例子：
先创建两个项目，eureka-student和eureka-grade，然后选择如图的组件建好后，项目的整体结构如图：

在eureka-student模块的pom.xml中添加feign的依赖

\&lt;dependency\&gt;

            \&lt;groupId\&gt;org.springframework.cloud\&lt;/groupId\&gt;

            \&lt;artifactId\&gt;spring-cloud-starter-openfeign\&lt;/artifactId\&gt;

\&lt;/dependency\&gt;

- 1
- 2
- 3
- 4

eureka-student模块的启动类添加@EnableDiscoveryClient @EnableFeignClients注解

@SpringBootApplication

@MapperScan(&quot;com.tdh.eurekastudent.mapper&quot;)

@EnableEurekaClient

@EnableDiscoveryClient

@EnableFeignClients

publicclass EurekaStudentApplication {

    publicstaticvoidmain(String[] args){

        SpringApplication.run(EurekaStudentApplication.class, args);

    }

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9
- 10

eureka-student的application.properties

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/studentdb?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=root

spring.application.name=eureka-student

server.port=8091

eureka.client.serviceUrl.defaultZone=http://localhost:8080/eureka/

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8

eureka-student的实体类

@Data

publicclass Student {

    private Integer id;

    private String name;

    private String sex;

    private Integer gradeId;

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7

eureka-student的service类

publicinterface StudentService {

    /\*\*

     \* 查询所有学生信息

     \* @return

     \*/

    List\&lt;Student\&gt;selStudents();

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7

eureka-student的mapper层

@Component

publicinterface StudentMapper {

    @Select(&quot;select \* from student&quot;)

    List\&lt;Student\&gt;selStudents();

}

- 1
- 2
- 3
- 4
- 5

eureka-student的service实现类

@Service

publicclass StudentServiceImpl implements  StudentService{

    @Autowired

    private StudentMapper studentMapper;

    @Override

    public List\&lt;Student\&gt;selStudents(){

        return studentMapper.selStudents();

    }

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9
- 10

然后eureka-grade模块和eureka-student一样写一个查询所有年级信息的方法
注意的是eureka-grade的application.properties文件

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/studentdb?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=root

spring.application.name=eureka-grade

server.port=8092

eureka.client.serviceUrl.defaultZone=http://localhost:8080/eureka/

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8

然后开始调用服务，在eureka-student中编写一个GradeClient

/\*\*

 \* 客服端

 \*/

@FeignClient(&quot;eureka-grade&quot;)

publicinterface GradeClient {

    @GetMapping(&quot;/grades&quot;)

    public List\&lt;Grade\&gt;selAllGrades();

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8

eureka-student的控制层

@RestController

publicclass StudentController {

    @Autowired

    private StudentService studentService;

    @Autowired

    private GradeClient gradeClient;

    @GetMapping(&quot;/students&quot;)

    public List\&lt;Student\&gt;selAllStudents(){

        return studentService.selStudents();

    }

//这里调用了eureka-grade的服务，实现了年级信息的查询

    @GetMapping(&quot;/grades&quot;)

    public List\&lt;Grade\&gt;selAllGrades(){

        return gradeClient.selAllGrades();

    };

}

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- 8
- 9
- 10
- 11
- 12
- 13
- 14
- 15
- 16
- 17
- 18
- 19
- 20

最后将四个模块都启动

去查看结果测试一下，可以看到student从eureka中取到了grade的接口，可以实现查询年级信息的接口