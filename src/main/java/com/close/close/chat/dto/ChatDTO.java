package com.close.close.chat.dto;

import com.close.close.apirest.dto.DTO;
import com.close.close.chat.Chat;
import com.close.close.chat.Message;
import com.close.close.user.User;
import com.close.close.user.dto.UserDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatDTO extends DTO<Chat> implements Serializable {
    Long id;
    Set<UserDTO> users;
    List<MessageDTO> messages;

    public ChatDTO(Chat chat) {
        super(chat);
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public Long getId() {
        return id;
    }

    @Override
    protected void initializeFields(){
        users = getUsersParsed();
        id = (Long) getPrivateField("id");
        messages = getMessagesParsed();
    }

    private Set<UserDTO> getUsersParsed(){
        Set<User> rawUsers = (Set<User>) getPrivateField("users");
        Set<UserDTO> response = new HashSet<>();
        for (User user : rawUsers){
            response.add(new UserDTO(user));
        }
        return response;
    }

    private List<MessageDTO> getMessagesParsed(){
        List<Message> rawMessages = (List<Message>) getPrivateField("messages");
        List<MessageDTO> response = new ArrayList<>();
        for (Message message : rawMessages){
            response.add(new MessageDTO(message));
        }
        return response;
    }

}
