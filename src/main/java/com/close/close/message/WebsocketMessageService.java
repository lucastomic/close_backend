package com.close.close.message;

import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketMessageService {
    public final String WS_MESSAGE_TRANSFER_DESTINATION = "/queue/messages";
    private  final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    WebsocketMessageService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    public void sendMessage(User user, Message message){
        simpMessagingTemplate.convertAndSendToUser(user.getUsername(),WS_MESSAGE_TRANSFER_DESTINATION, message);
    }
}
