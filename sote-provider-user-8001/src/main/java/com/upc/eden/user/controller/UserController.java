package com.upc.eden.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.eden.commens.entity.User;
import com.upc.eden.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/02/26/15:52
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping
    public Page<User> list(@RequestParam(defaultValue = "1") long cp,
                           @RequestParam(defaultValue = "2") long size) {

        Page<User> page = new Page<>(cp, size);
        Page<User> userPage = userService.page(page, null);
        return userPage;
    }

    @GetMapping("/{userId}")
    public User findByUserId(@PathVariable Integer userId) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        User findUser = userService.getOne(wrapper);
        return findUser;
    }
}
