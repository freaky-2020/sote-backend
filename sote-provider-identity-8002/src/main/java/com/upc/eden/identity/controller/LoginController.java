package com.upc.eden.identity.controller;

import com.upc.eden.commen.clients.UserClient;
import com.upc.eden.commen.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author: CS Dong
 * @Date: 2022/02/23/11:12
 * @Description:
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserClient userClient;

    @GetMapping
    public String loginPage(HttpSession session) {

        if(session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }
        if(session.getAttribute("nowStatus") != null) {
            session.removeAttribute("nowStatus");
        }
        return "login";
    }

    @PostMapping
    public String login(User user, Model model, HttpSession session) {

        Integer userId = user.getUserId();
        String password = user.getPassword();

        User findUser = userClient.findByUserId(userId);
        if(findUser != null && password.equals(findUser.getPassword())) {
            session.setAttribute("loginUser", findUser);
            return "redirect:/index";
        }
        model.addAttribute("msg", "用户账号不存在或密码错误！");
        return "login";
    }
}
