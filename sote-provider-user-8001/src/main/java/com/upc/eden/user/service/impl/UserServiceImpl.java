package com.upc.eden.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commens.entity.User;
import com.upc.eden.user.mapper.UserMapper;
import com.upc.eden.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




