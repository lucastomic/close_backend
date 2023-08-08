package com.close.close.chat.websockets;
import com.close.close.chat.Chat;
import com.close.close.chat.dto.ChatDTO;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatWebSocketService implements IChatWebSocketService {
    public final String WS_MESSAGE_TRANSFER_DESTINATION = "/queue/chat";
    private  final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatWebSocketService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    public void notifyChatMembers(Chat chat){
        chat.forEachMember(user -> sendToUser(user,chat));
    }
    private Void sendToUser(User user, Chat chat){
        simpMessagingTemplate.convertAndSendToUser(user.getUsername(),WS_MESSAGE_TRANSFER_DESTINATION, new ChatDTO(chat));
        return null;
    }
}

