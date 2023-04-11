package com.example.commmon;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.entity.Messages;
import com.example.entity.User;
import com.example.service.MessagesService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/chatServer/{username}")
@Component
public class WebSocketServer {

    private static ApplicationContext applicationContext;

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //将全部用户查找出来（除自己外）
        UserService userService = applicationContext.getBean(UserService.class);
        MessagesService messagesService = applicationContext.getBean(MessagesService.class);
        List<User> list = userService.lambdaQuery().ne(User::getUsername, username).list();

        for (User userName : list){
            String username1 = userName.getUsername();
            sessionMap.put(username1, session);
        }
        //sessionMap.put(username, session);
        //log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
//        List<Messages> messages = messagesService.lambdaQuery()
//                .eq(Messages::getType, "私信")
//                .eq(Messages::getStatus, 0)
//                .eq(Messages::getReceiverId,username)
//                .or().eq(Messages::getSenderId,username).list();

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);

        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
//            for (Messages message : messages) {
//                User sendUser = userService.lambdaQuery().eq(User::getId, message.getSenderId()).list().get(0);
//                User receiverUser = userService.lambdaQuery().eq(User::getId, message.getReceiverId()).list().get(0);
//                if (receiverUser.getUsername().equals(key)){
//                    jsonObject.set("priText", message.getContent());
//                }else if (receiverUser.getUsername().equals(username)){
//
//                }
//            }
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        //log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }
    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        UserService userService = applicationContext.getBean(UserService.class);
        MessagesService messagesService = applicationContext.getBean(MessagesService.class);
        if (message.equals("获取历史消息")){
                    List<Messages> messages = messagesService.lambdaQuery()
                        .eq(Messages::getType, "私信")
                        .eq(Messages::getReceiverId,username)
                        .or().eq(Messages::getSenderId,username).list();
        }else {
            //log.info("服务端收到用户username={}的消息:{}", username, message);
            JSONObject obj = JSONUtil.parseObj(message);
            String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
            String text = obj.getStr("text"); // 发送的消息文本  hello
            // {"to": "admin", "text": "聊天文本"}
            Messages messages = new Messages();
            messages.setContent(text);
            Integer receiverId = userService.lambdaQuery().eq(User::getUsername, toUsername).list().get(0).getId();
            Integer senderId = userService.lambdaQuery().eq(User::getUsername, username).list().get(0).getId();
            messages.setReceiverId(receiverId);
            messages.setSenderId(senderId);
            messages.setType("私信");
            messages.setStatus("0");
            messagesService.save(messages);

            Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
            if (toSession != null) {
                // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
                // {"from": "zhang", "text": "hello"}
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("from", username);  // from 是 zhang
                jsonObject.set("text", text);  // text 同上面的text
                this.sendMessage(jsonObject.toString(), toSession);
                //log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
            } else {
                log.info("发送失败，未找到用户username={}的session", toUsername);
            }
        }

    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            //log.error("服务端发送消息给客户端失败", e);
        }
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                //log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            //log.error("服务端发送消息给客户端失败", e);
        }
    }
}
