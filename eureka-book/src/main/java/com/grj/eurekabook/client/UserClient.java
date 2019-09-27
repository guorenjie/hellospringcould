package com.grj.eurekabook.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * 用户客户端
 * 使用@FeignClient注解的时候 是默认使用了ribbon进行客户端的负载均衡的,默认的是随机的策略
 * name:任意客户端名称，application.yml中的spring.application.name的值，用于创建Ribbon负载均衡器，
 * fallback：熔断回调
 * @author guorenjie
 */
@Component
@FeignClient(name="user-client",fallback = HystrixClientFallback.class)
public interface UserClient {
    /**
     * 获取用户(这里的GetMapping是用户服务方法的请求地址，controller的RequestMapping可以自定义)
     * @return
     */
    @GetMapping("/user/getAllList")
    public List<Map<String,Object>> getAllUserList();
}
