package com.upc.eden.commen.clients;

import com.upc.eden.commen.domain.auth.SecurityUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Author: CS Dong
 * @Date: 2022/02/28/10:00
 * @Description:
 */

@FeignClient(value = "auth-service")
public interface AuthClient {

//    @GetMapping("/user/{userName}")
//    User findByUserName(@PathVariable String userName);

    @GetMapping("/client/current")
    SecurityUser getCurrentUser();
}
