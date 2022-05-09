package com.upc.eden.auth.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hdh
 * @date 2022/5/9 - 11:56
 */

@ServerEndpoint("/openSocket/{userId}")
@Component
public class WebSocketServer{
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
//            addOnlineCount();
            //在线数加1
        }
//        System.out.println("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
//        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
//            log.error("用户:"+userId+",网络异常!!!!!!");
            System.out.println("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
//            subOnlineCount();
        }
//        System.out.println("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());

    }



    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static String sendInfo(String message,String userId) {
        try {
            System.out.println("发送消息到:"+userId+"，报文:"+message);

            webSocketMap.get(userId).sendMessage(message);
            return "1";
        }catch (Exception e){
            e.printStackTrace();
            return "0";
        }

    }

}
