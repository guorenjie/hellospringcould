package com.grj.eurekabook.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * 用户客户端
 * @author guorenjie
 */
@FeignClient("user-client")
public interface UserClient {
    /**
     * 获取用户(这里的GetMapping是用户服务方法的请求地址，controller的RequestMapping可以自定义)
     * @return
     */
    @GetMapping("/user/getAllList")
    public List<Map<String,Object>> getAllUserList();
}
