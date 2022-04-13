package com.upc.eden.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.auth.domain.SecurityUser;
import com.upc.eden.auth.holder.LoginUserHolder;
import com.upc.eden.auth.service.UserService;
import com.upc.eden.commen.domain.auth.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/04/04/16:08
 * @Description:
 */

@RestController
@RequestMapping("/client")
public class ClientController {

    @Resource
    private LoginUserHolder loginUserHolder;
    @Resource
    private UserService userService;

    /**
     * 获取当前登录用户信息
     * /auth/user/current
     * @return 当前登录用户的 User类封装体
     */
    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/current")
    public SecurityUser getCurrentUser() {

        return loginUserHolder.getCurrentUser();
    }

    @ApiOperation("根据userName获取用户信息")
    @GetMapping("/get")
    public User getInfoByUserName(@RequestParam String userName) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User user = userService.getOne(userQueryWrapper);
        return user;
    }
}
