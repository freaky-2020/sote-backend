package com.upc.eden.identity.controller;

import com.upc.eden.commen.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Author: CS Dong
 * @Date: 2022/03/02/10:16
 * @Description:
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String toIndex(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        Integer status = loginUser.getUserStatus();
        session.setAttribute("nowStatus", status);
        if(status == 1) {
            return "redirect:/index/admin";
        } else if(status == 2) {
            return "redirect:/index/teacher";
        } else {
            return "redirect:/index/student";
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
