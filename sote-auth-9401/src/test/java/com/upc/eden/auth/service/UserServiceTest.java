package com.upc.eden.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/14/11:33
 * @Description:
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    public void test() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<User> list = userService.list(null);
        System.out.println(list);
    }

}