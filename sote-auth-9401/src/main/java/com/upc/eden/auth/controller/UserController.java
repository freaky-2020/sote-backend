package com.upc.eden.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.eden.auth.domain.SecurityUser;
import com.upc.eden.auth.holder.LoginUserHolder;
import com.upc.eden.auth.service.UserService;
import com.upc.eden.commen.domain.auth.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/02/26/15:52
 * @Description:
 */
@RestController
@Api(tags = { "User增删改查接口文档"} )
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    LoginUserHolder loginUserHolder;

    @ApiOperation("获取全部用户信息的列表，返回值按roleId、id依次降序")
    @GetMapping
    public List<User> list() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        List<User> userList = userService.list(wrapper);
        return userList;
    }

    @ApiOperation("获取全部用户信息的页封装体，返回值按roleId、id依次降序")
    @GetMapping("/page")
    public Page<User> page(@ApiParam(value = "当前页码") @RequestParam(defaultValue = "1") long cp,
                           @ApiParam(value = "每页条数") @RequestParam(defaultValue = "10") long size) {

        Page<User> page = new Page<>(cp, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        Page<User> userPage = userService.page(page, wrapper);
        return userPage;
    }

    @ApiOperation("获取全部管理员信息的页封装体，返回值按roleId、id依次降序")
    @GetMapping("/page/admin")
    public Page<User> adminPage(@ApiParam(value = "当前页码") @RequestParam(defaultValue = "1") long cp,
                                @ApiParam(value = "每页条数") @RequestParam(defaultValue = "10") long size) {

        Page<User> page = new Page<>(cp, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", 1)
                .orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        Page<User> adminPage = userService.page(page, wrapper);
        return adminPage;
    }

    @ApiOperation("获取全部教师信息的页封装体，返回值按roleId、id依次降序")
    @GetMapping("/page/teacher")
    public Page<User> teacherPage(@ApiParam(value = "当前页码") @RequestParam(defaultValue = "1") long cp,
                                  @ApiParam(value = "每页条数") @RequestParam(defaultValue = "10") long size) {

        Page<User> page = new Page<>(cp, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", 2)
                .orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        Page<User> teacherPage = userService.page(page, wrapper);
        return teacherPage;
    }

    @ApiOperation("获取全部学生信息的页封装体，返回值按roleId、id依次降序")
    @GetMapping("/page/student")
    public Page<User> studentPage(@ApiParam(value = "当前页码") @RequestParam(defaultValue = "1") long cp,
                                  @ApiParam(value = "每页条数") @RequestParam(defaultValue = "10") long size) {

        Page<User> page = new Page<>(cp, size);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", 3)
                .orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        Page<User> studentPage = userService.page(page, wrapper);
        return studentPage;
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/current")
    public SecurityUser getCurrentUser() {

        return loginUserHolder.getCurrentUser();
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public boolean add(@RequestBody User user) {

        boolean res = userService.save(user);
        return res;
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public boolean update(@RequestBody User user) {

        String userName = user.getUserName();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.update(user, wrapper);
        return res;
    }

    @ApiOperation("删除用户")
    @GetMapping("/delete/{userName}")
    public boolean delete(@PathVariable String userName) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.remove(wrapper);
        return res;
    }

    @ApiOperation("批量删除用户")
    @PostMapping("/delete")
    public int delete(@RequestBody String[] userNames) {

        int res = 0;
        for (String userName: userNames) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name", userName);
            if (userService.remove(wrapper)) ++res;
        }
        return res;
    }
}
