package com.close.close.chat.notifications;

import com.close.close.user.User;

public interface IChatNotificationsService {
    void notify(User sender, User receiver);
}
