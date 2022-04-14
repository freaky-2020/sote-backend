package com.upc.eden.commen.clients;

import com.upc.eden.commen.config.FeignRequestConfig;
import com.upc.eden.commen.domain.auth.SecurityUser;
import com.upc.eden.commen.domain.auth.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author: CS Dong
 * @Date: 2022/02/28/10:00
 * @Description:
 */

@FeignClient(value = "gateway-service", contextId = "auth", configuration = FeignRequestConfig.class)
public interface AuthClient {

    @GetMapping("/auth/client/get")
    User getInfoByUserName(@RequestParam String userName);

    @GetMapping("/auth/client/current")
    SecurityUser getCurrentUser();
}
