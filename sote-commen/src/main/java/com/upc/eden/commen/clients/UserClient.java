//package com.upc.eden.commen.clients;
//
//import com.upc.eden.commen.entity.User;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
///**
// * @Author: CS Dong
// * @Date: 2022/02/28/10:00
// * @Description:
// */
//
//@FeignClient(value = "user-service")
//public interface UserClient {
//
//    @GetMapping("/user/{userId}")
//    User findByUserId(@PathVariable Integer userId);
//}
