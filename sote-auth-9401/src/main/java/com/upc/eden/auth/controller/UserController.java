package com.upc.eden.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.eden.auth.domain.SecurityUser;
import com.upc.eden.auth.holder.LoginUserHolder;
import com.upc.eden.auth.service.UserService;
import com.upc.eden.commen.domain.User;
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

    @Resource
    LoginUserHolder loginUserHolder;

    @GetMapping
    public Page<User> list(@RequestParam(defaultValue = "1") long cp,
                           @RequestParam(defaultValue = "2") long size) {

        Page<User> page = new Page<>(cp, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, true, "id");
        Page<User> userPage = userService.page(page, wrapper);
        return userPage;
    }

    @GetMapping("/current")
    public SecurityUser getCurrentUser() {

        return loginUserHolder.getCurrentUser();
    }

    @PostMapping("/add")
    public boolean add(User user) {

        boolean res = userService.save(user);
        return res;
    }

    @PostMapping("/update")
    public boolean update(User user) {

        String userName = user.getUserName();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.update(user, wrapper);
        return res;
    }

    @GetMapping("/delete/{userName}")
    public boolean delete(@PathVariable String userName) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.remove(wrapper);
        return res;
    }
}
