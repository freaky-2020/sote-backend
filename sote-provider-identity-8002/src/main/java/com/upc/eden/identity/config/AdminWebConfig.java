package com.upc.eden.identity.config;

import com.upc.eden.identity.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: CS Dong
 * @Date: 2022/02/23/10:42
 * @Description:
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/register",
                        "/css/**", "/fonts/**", "/images/**", "/js/**", "/druid/**");
    }
}
