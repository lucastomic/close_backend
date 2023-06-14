package com.close.close.message;

import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository MESSAGE_REPOSITORY;

    @Autowired
    MessageService(MessageRepository messageRepository){
        this.MESSAGE_REPOSITORY = messageRepository;
    }

    public Message sendMessage(User receiver, User sender, String value){
        Message message = new Message(receiver, sender, value);
        MESSAGE_REPOSITORY.save(message);
        return message;
    }

    public List<Message> getMessages(User receiver, User sender){
        return MESSAGE_REPOSITORY.findMessage(receiver.getId(), sender.getId());
    }
}
