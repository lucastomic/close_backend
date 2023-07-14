package com.close.close.message;

import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository MESSAGE_REPOSITORY;
    private final WebsocketMessageService WEBSOCKET_MESSAGE_SERVICE;

    @Autowired
    public MessageService(MessageRepository MESSAGE_REPOSITORY, WebsocketMessageService WEBSOCKETMESSAGESERVICE) {
        this.MESSAGE_REPOSITORY = MESSAGE_REPOSITORY;
        this.WEBSOCKET_MESSAGE_SERVICE = WEBSOCKETMESSAGESERVICE;
    }

    public Message sendMessage(User receiver, User sender, String value){
        Message message = new Message(receiver, sender, value);
        MESSAGE_REPOSITORY.save(message);
        WEBSOCKET_MESSAGE_SERVICE.sendMessage(receiver,message);
        return message;
    }

    public List<Message> getMessages(User receiver, User sender){
        return MESSAGE_REPOSITORY.findMessage(receiver.getId(), sender.getId());
    }
}
