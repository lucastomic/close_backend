package com.close.close.chat.service;

import com.close.close.chat.Chat;
import com.close.close.user.User;

public interface IChatService {
    Chat sendMessage(User sender, User receiver, String message);
    Chat getChat(User sender, User receiver);
}
