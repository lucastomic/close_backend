package com.close.close.chat.notifications;

import com.close.close.user.User;
import com.close.close.user.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
class JsonNotificationFormatter {
    private User sender;
    private User receiver;

    public String getJson(User sender, User receiver) throws JsonProcessingException {
        this.receiver = receiver;
        this.sender = sender;
        return getJsonBody().toString();
    }

    private JSONObject getJsonBody() throws JsonProcessingException {
        Map<String, Map<String,Object>> body = new HashMap<>();
        body.put("message", getMessage());
        return new JSONObject(body);
    }

    private Map<String,Object> getMessage() throws JsonProcessingException {
        Map<String,Object> message = new HashMap<>();
        message.put("token",receiver.getNotificationDeviceID());
        message.put("notification",getNotification());
        message.put("data", getData());
        return  message;
    }

    private Map<String,String> getNotification(){
        Map<String,String> notification= new HashMap<>();
        notification.put("body","Haz recibido un mensaje de @" + sender.getUsername() );
        notification.put("title","¡Nuevo mensaje!");
        return notification;
    }

    private Map<String,Object> getData() throws JsonProcessingException {
        Map<String,Object> data= new HashMap<>();
        data.put("user", getSenderAsJson());
        return data;
    }

    private String getSenderAsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(new UserDTO(receiver));
    }
}
