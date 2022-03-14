package com.upc.eden.auth.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/03/13/21:04
 * @Description:
 */
@SpringBootTest
class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void test() {

        System.out.println(userMapper.selectList(null));
    }
}