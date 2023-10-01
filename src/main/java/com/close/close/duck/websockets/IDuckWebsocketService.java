package com.close.close.duck.websockets;

import com.close.close.user.User;
import org.springframework.stereotype.Service;

public interface IDuckWebsocketService {
    void notify(User user);
}
