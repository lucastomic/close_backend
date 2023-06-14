package com.close.close.message;

import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import com.close.close.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private MessageService MESSAGE_SERVICE;
    private AuthenticationService AUTH_SERVICE;
    private UserService USER_SERVICE;
    private MessageModelAssembler MODEL_ASSEMBLER;

    @Autowired
    public MessageController(
            MessageService MESSAGE_SERVICE,
            AuthenticationService AUTH_SERVICE,
            UserService USER_SERVICE,
            MessageModelAssembler MODEL_ASSEMBLER) {
        this.MESSAGE_SERVICE = MESSAGE_SERVICE;
        this.AUTH_SERVICE = AUTH_SERVICE;
        this.USER_SERVICE =USER_SERVICE;
        this.MODEL_ASSEMBLER = MODEL_ASSEMBLER;
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
        User receiver = USER_SERVICE.findById(receiverID);Message message = MESSAGE_SERVICE.sendMessage(receiver,sender,value);
        EntityModel<Message> response = MODEL_ASSEMBLER.toModel(message);
        return ResponseEntity.ok(response);
    }

    /**
     * Sends a messages from the authenticated user to the one with the ID specified
     * @param receiverId Long with the ID of the message receiver
     * @return Response Entity with status 200 OK and the message in the body if success
     */
    @GetMapping("/get/{receiverId}")
    public ResponseEntity<?> getMessage(@PathVariable Long receiverId){
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_SERVICE.findById(receiverId);
        List<Message> message = MESSAGE_SERVICE.getMessages(receiver,sender);
        CollectionModel<EntityModel<Message>> response = MODEL_ASSEMBLER.toCollectionModel(message);
        return ResponseEntity.ok(response);
    }
}
