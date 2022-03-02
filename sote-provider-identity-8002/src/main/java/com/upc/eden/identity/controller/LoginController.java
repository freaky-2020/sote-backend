package com.upc.eden.identity.controller;

import com.upc.eden.commens.clients.UserClient;
import com.upc.eden.commens.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author: CS Dong
 * @Date: 2022/02/23/11:12
 * @Description:
 */
@Controller
public class LoginController {

    @Resource
    UserClient userClient;

    @GetMapping({"/", "/login"})
    public String loginPage(HttpSession session) {

        if(session.getAttribute("loginUser") != null) {
            session.removeAttribute("loginUser");
        }
        if(session.getAttribute("nowStatus") != null) {
            session.removeAttribute("nowStatus");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session) {

        Integer userId = user.getUserId();
        String password = user.getPassword();

        User findUser = userClient.findByUserId(userId);
        if(findUser != null && password.equals(findUser.getPassword())) {
            session.setAttribute("loginUser", findUser);
            return "redirect:/indexPage";
        }
        model.addAttribute("msg", "用户账号不存在或密码错误！");
        return "login";
    }

    @GetMapping("/indexPage")
    public String toIndex(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        Integer status = loginUser.getUserStatus();
        session.setAttribute("nowStatus", status);
        if(status == 1) {
            return "redirect:/admin";
        } else if(status == 2) {
            return "redirect:/teacher";
        } else {
            return "redirect:/student";
        }
    }

    @GetMapping("/admin")
    public String adminIndex(HttpSession session) {

        session.setAttribute("nowStatus", 1);
        return "index";
    }

    @GetMapping("/teacher")
    public String teacherIndex(@RequestParam(defaultValue = "0") int isAdmin,
                               HttpSession session) {

        if(isAdmin == 1) {
            session.setAttribute("nowStatus", 2);
        }
        return "index";
    }

    @GetMapping("/student")
    public String studentIndex(@RequestParam(defaultValue = "0") int isAdmin,
                               HttpSession session) {

        if(isAdmin == 1) {
            session.setAttribute("nowStatus", 3);
        }
        return "index";
    }
}
