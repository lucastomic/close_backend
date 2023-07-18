package com.close.close.chat.controller;
import com.close.close.chat.Chat;
import com.close.close.chat.service.IChatService;
import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import com.close.close.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private IChatService CHAT_SERVICE;
    private AuthenticationService AUTH_SERVICE;
    private UserService USER_SERVICE;

    @Autowired
    public ChatController(
            IChatService CHAT_SERVICE,
            AuthenticationService AUTH_SERVICE,
            UserService USER_SERVICE) {
        this.CHAT_SERVICE = CHAT_SERVICE;
        this.AUTH_SERVICE = AUTH_SERVICE;
        this.USER_SERVICE =USER_SERVICE;
    }

    /**
     * Sends a messages from the authenticated user to the one with the ID specified
     * @param receiverID Long with the ID of the message receiver
     * @param value String with the message value to be sent
     * @return Response Entity with status 200 OK and the message in the body if success
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam Long receiverID, @RequestParam String value){
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_SERVICE.findById(receiverID);
        Chat chat = CHAT_SERVICE.sendMessage(receiver,sender,value);
        return ResponseEntity.ok(chat);
    }

    /**
     * Retrieves the chat between the user authenticated and the user specified by parameter
     * @param receiverId id of the User whose chat will be retrieved
     * @return Chat with the user specified. It can be an empty chat.
     */
    @GetMapping("/get/{receiverId}")
    public ResponseEntity<?> getChat(@PathVariable Long receiverId){
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_SERVICE.findById(receiverId);
        Chat chat = CHAT_SERVICE.getChat(receiver,sender);
        return ResponseEntity.ok(chat);
    }
}
