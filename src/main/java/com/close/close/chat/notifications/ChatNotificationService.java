package com.close.close.chat.notifications;

import com.close.close.user.User;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationService implements IChatNotificationsService{
    private NotificationRequestService requestService;
    ChatNotificationService(NotificationRequestService requestService){
        this.requestService = requestService;
    }
    @Override
    public void notify(User sender, User receiver)  {
        try{
            requestService.send(sender,receiver);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}


