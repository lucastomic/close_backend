package com.close.close.chat.dto;

import com.close.close.apirest.dto.DTO;
import com.close.close.chat.Message;
import com.close.close.user.User;

import java.io.Serializable;

public class MessageDTO extends DTO<Message> implements Serializable {
    private User sender;
    private String value;
    private Long id;

    public MessageDTO(Message object) {
        super(object);
    }

    public User getSender() {
        return sender;
    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    protected void initializeFields(){
        value = (String) getPrivateField("value");
        sender = (User)getPrivateField("sender");
        id = (Long) getPrivateField("id");
    }

}
