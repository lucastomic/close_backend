package com.close.close.duck.dto;

import com.close.close.apirest.dto.DTO;
import com.close.close.duck.Duck;
import com.close.close.user.User;

import java.io.Serializable;

public class DuckDTO extends DTO<Duck> implements Serializable {
    private Long sender;
    private Long receiver;

    public DuckDTO(Duck duck) {
        super(duck);

    }


    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    private Long getUserIdFromField(String field){
        User user =  (User) getPrivateField(field);
        return user.getId();
    }

    @Override
    protected void initializeFields() {
        this.receiver = getUserIdFromField("receiver");
        this.sender = getUserIdFromField("sender");
    }

}
