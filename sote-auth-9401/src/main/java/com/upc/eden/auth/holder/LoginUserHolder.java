package com.upc.eden.auth.holder;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.upc.eden.auth.domain.SecurityUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CS Dong
 * @Date: 2022/03/14/17:36
 * @Description: 获取登录用户信息
 */
@Component
public class LoginUserHolder {

    public SecurityUser getCurrentUser(){

        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(userJsonObject.getStr("user_name"));
        securityUser.setId(Convert.toInt(userJsonObject.get("id")));
        securityUser.setRole(String.valueOf(userJsonObject.get("authorities")));
        return securityUser;
    }
}
