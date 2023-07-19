package com.close.close.message.dto;

import com.close.close.apirest.dto.DTO;
import com.close.close.message.Message;
import com.close.close.user.User;

import java.io.Serializable;

public class MessageDTO extends DTO<Message> implements Serializable {
    Long senderId;
    String value;
    Long id;

    public MessageDTO(Message object) {
        super(object);
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    protected void initializeFields(){
        value = (String) getPrivateField("value");
        User sender = (User)getPrivateField("sender");
        senderId = sender.getId();
        id = (Long) getPrivateField("id");
    }

}
