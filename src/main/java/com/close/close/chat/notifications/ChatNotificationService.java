package com.close.close.chat.notifications;

import com.close.close.user.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationService implements IChatNotificationsService{
    private final FirebaseMessaging firebaseMessaging;

    public ChatNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }
    @Override
    public void notify(User sender, User receiver)  {
        try{
            Message msg = Message.builder()
                    .setToken(receiver.getNotificationDeviceID())
                    .setNotification(Notification.builder()
                            .setTitle("Some title")
                            .setBody("You have received a message from @" + sender.getUsername())
                            .build()).build();
            String message = firebaseMessaging.send(msg);
            System.out.println(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}


