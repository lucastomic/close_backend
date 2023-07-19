package com.close.close.chat.websockets;

import com.close.close.chat.Chat;
public interface IChatWebSocketService {
    void notifyChatMembers(Chat message);
}
