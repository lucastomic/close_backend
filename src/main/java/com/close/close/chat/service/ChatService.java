package com.close.close.chat.service;

import com.close.close.chat.Chat;
import com.close.close.chat.ChatRepository;
import com.close.close.message.Message;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatService implements IChatService{
    private final ChatRepository CHAT_REPOSITORY;

    @Autowired
    public ChatService(ChatRepository CHAT_REPOSITORY) {
        this.CHAT_REPOSITORY = CHAT_REPOSITORY;
    }

    public Chat sendMessage(User sender, User receiver, String messageValue){
        Chat chat = getChat(sender,receiver);
        Message message = new Message(sender,chat,messageValue);
        chat.addMessage(message);
        CHAT_REPOSITORY.save(chat);
        return chat;
    }

    public Chat getChat(User sender, User receiver){
        List<Long> ids = getIDsList(sender,receiver);
        Chat emptyChat = getEmptyChat(sender,receiver);
        return CHAT_REPOSITORY.getChat(ids, ids.size()).orElse(emptyChat);
    }
    private List<Long> getIDsList(User sender, User receiver){
        return List.of(sender.getId(),receiver.getId());
    }
    private Chat getEmptyChat(User sender, User receiver){
        return new Chat(Set.copyOf(Set.of(sender,receiver)));
    }
}
