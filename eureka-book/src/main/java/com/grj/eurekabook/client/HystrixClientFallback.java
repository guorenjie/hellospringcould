package com.grj.eurekabook.client;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author guorenjie
 * @Date 2019/9/28 2:27
 **/
@Component
public class HystrixClientFallback implements UserClient{

    /**
     * 获取用户(这里的GetMapping是用户服务方法的请求地址，controller的RequestMapping可以自定义)
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllUserList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("msg","熔断回调测试：系统开小差了！");
        list.add(map);
        return list;
    }
}
