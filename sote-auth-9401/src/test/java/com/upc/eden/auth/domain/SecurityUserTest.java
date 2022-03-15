package com.upc.eden.auth.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.auth.mapper.RoleMapper;
import com.upc.eden.auth.mapper.UserMapper;
import com.upc.eden.commen.domain.Role;
import com.upc.eden.commen.domain.User;
import io.lettuce.core.ScriptOutputType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: CS Dong
 * @Date: 2022/03/14/20:46
 * @Description:
 */
@SpringBootTest
class SecurityUserTest {

    @Resource
    RoleMapper roleMapper;
    @Resource
    UserMapper userMapper;

    @Test
    void test() {

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role_id", 1);
        Role role = roleMapper.selectOne(roleQueryWrapper);
        System.out.println(role);
    }

}