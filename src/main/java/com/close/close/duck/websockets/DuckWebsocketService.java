package com.close.close.duck.websockets;

import com.close.close.user.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DuckWebsocketService implements IDuckWebsocketService{
    public final String WS_TRANSFER_DESTINATION = "/queue/duck";
    private  final SimpMessagingTemplate simpMessagingTemplate;
    DuckWebsocketService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @Override
    public void notify(User user) {
        simpMessagingTemplate.convertAndSendToUser(user.getUsername(),WS_TRANSFER_DESTINATION,user.getDucksReceived());
    }
}
