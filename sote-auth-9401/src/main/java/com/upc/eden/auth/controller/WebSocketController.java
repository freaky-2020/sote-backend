package com.upc.eden.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hdh
 * @date 2022/5/9 - 12:04
 */
@RestController
public class WebSocketController {
    @RequestMapping("sendMessageTest")
    public String sendMessageTest(@RequestParam(value = "user_id")String user_id, @RequestParam(value = "message")String message){
        return WebSocketServer.sendInfo(message,user_id);
    }
}
