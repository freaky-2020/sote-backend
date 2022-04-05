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
@Api(tags = { "用户增删改查接口文档"} )
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    LoginUserHolder loginUserHolder;

    /**
     * 获取全部用户信息的列表
     * /auth/user
     * @return 全部用户信息的列表，按 roleId、依次降序
     */
    @ApiOperation("获取全部用户信息的列表，返回值按roleId、id依次降序")
    @GetMapping
    public List<User> list() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, true, "role_id")
                .orderBy(true, true, "id");
        List<User> userList = userService.list(wrapper);
        return userList;
    }

    /**
     * 获取全部用户信息的页封装体
     * /auth/user/page
     * @param cp 当前页数，默认为 1
     * @param size 每页条数，默认为 10
     * @return 全部管理员信息的页封装体，按 roleId、依次降序
     */
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

    /**
     * 获取全部管理员信息的页封装体
     * /auth/user/page/admin
     * @param cp 当前页数
     * @param size 每页条数
     * @return 全部管理员信息的页封装体，按 roleId、id依次降序
     */
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

    /**
     * 获取全部教师信息的页封装体
     * /auth/user/page/teacher
     * @param cp 当前页数
     * @param size 每页条数
     * @return 全部教师信息的页封装体，按 roleId、id依次降序
     */
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

    /**
     * 获取全部学生信息的页封装体
     * /auth/user/page/student
     * @param cp 当前页数
     * @param size 每页条数
     * @return 全部学生信息的页封装体，按 roleId、id依次降序
     */
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

    /**
     * 添加用户
     * /auth/user/add
     * @param user 要添加的用户信息会被自动封装在此对象中：要求必须含有 userName、password、roleId
     * @return 添加是否成功，true or false
     */
    @ApiOperation("添加用户")
    @PostMapping("/add")
    public boolean add(User user) {

        boolean res = userService.save(user);
        return res;
    }

    /**
     * 更新用户
     * /auth/user/update
     * @param user 要更新的用户信息会被自动封装在此对象中：要求必须含有 userName、password、roleId
     * @return 更新是否成功，true or false
     */
    @ApiOperation("更新用户：以userName为索引标准，即userName不可修改")
    @PutMapping("/update")
    public boolean update(User user) {

        String userName = user.getUserName();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.update(user, wrapper);
        return res;
    }

    /**
     * 删除用户
     * /auth/user/delete/{userName}
     * @param userName 要删除用户的 userName, 拼接在路径中
     * @return 删除是否成功，true or false
     */
    @ApiOperation("删除用户，待删除用户的userName拼接在路径尾")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账号", paramType = "path")})
    @DeleteMapping("/delete/{userName}")
    public boolean delete(@PathVariable String userName) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        boolean res = userService.remove(wrapper);
        return res;
    }

    /**
     * 批量删除用户，以字符串数组封装所有待删除账号作为参数
     * @param userNames 待删除账号的字符串数组
     * @return 删除成功的条数
     */
    @ApiOperation("批量删除用户，以字符串数组封装所有待删除账号作为参数：返回值为成功条数")
    @ApiImplicitParam(name = "userNames", allowMultiple = true, dataTypeClass = List.class)
    @DeleteMapping("/delete")
    public int delete(String[] userNames) {

        int res = 0;
        for (String userName: userNames) {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name", userName);
            if (userService.remove(wrapper)) ++res;
        }
        return res;
    }
}
