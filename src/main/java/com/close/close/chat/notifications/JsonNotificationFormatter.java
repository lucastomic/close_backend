package com.close.close.chat.notifications;

import com.close.close.user.User;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
class JsonNotificationFormatter {
    private User sender;
    private User receiver;

    public String getJson(User sender, User receiver){
        this.receiver = receiver;
        this.sender = sender;
        return getJsonBody().toString();
    }

    private JSONObject getJsonBody(){
        Map<String, Map<String,Object>> body = new HashMap<>();
        body.put("message", getMessage());
        return new JSONObject(body);
    }

    private Map<String,Object> getMessage(){
        Map<String,Object> message = new HashMap<>();
        message.put("token",receiver.getNotificationDeviceID());
        message.put("notification",getNotification());
        return  message;
    }

    private Map<String,String> getNotification(){
        Map<String,String> notification= new HashMap<>();
        notification.put("body","Haz recibido un mensaje de @" + sender.getUsername() );
        notification.put("title","¡Nuevo mensaje!");
        return notification;
    }
}
