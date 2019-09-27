package com.grj.eurekauser.service.impl;

import com.grj.eurekauser.entity.User;
import com.grj.eurekauser.mapper.UserMapper;
import com.grj.eurekauser.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guorenjie
 * @since 2019-09-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
