package com.close.close.duck;

import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import com.close.close.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * DuckController is the controller for all duck's logic interactions
 */
@RestController
public class DuckController {

    public static final String GET_DUCKS               = "/ducks";
    public static final String GET_USER_RECEIVED_DUCKS = "/users/ducks/received";
    public static final String GET_DUCKS_SENT = "/ducks/sent";
    public static final String SEND_DUCK          = "/users/ducks/send";
    public static final String DELETE_RECLAIM_DUCK     = "/users/ducks/reclaim";

    private final DuckService DUCK_SERVICE;
    private final AuthenticationService AUTH_SERVICE;
    private final UserService USER_SERVICE;


    /**
     * class constructor with dependency injection
     */
    DuckController(DuckService duckService,
                   AuthenticationService authenticationService,
                   UserService userService){
        this.DUCK_SERVICE = duckService;
        this.AUTH_SERVICE = authenticationService;
        this.USER_SERVICE = userService;
    }


    /**
     * @return All ducks stored in the database.
     */
    @GetMapping(GET_DUCKS)
    public ResponseEntity<?> findAll() {
        List<Duck> ducks = DUCK_SERVICE.findAll();
        return ResponseEntity.ok(ducks);
    }

    /**
     * getDucksReceived return a collection of the users who have sent a
     * duck to the user currently authenticated
     * @return CollectionModel with all the users who have sent a duck to this user
     */
    @GetMapping(GET_USER_RECEIVED_DUCKS)
    public ResponseEntity<List<Duck>> getDucksReceived() {
        Long userId = AUTH_SERVICE.getIdAuthenticated();
        List<Duck> ducksReceived = DUCK_SERVICE.findDucksReceived(userId);
        return ResponseEntity.ok(ducksReceived);
    }

    /**
     * Retrieves the ducks which has been sent by the user who is currently authenticated
     * @return Response entity with a 200 OK status and all the ducks sent
     */
    @GetMapping(GET_DUCKS_SENT)
    public ResponseEntity<List<Duck>> getDucksSent() {
        Long userId = AUTH_SERVICE.getIdAuthenticated();
        List<Duck> ducksSent = DUCK_SERVICE.findDucksSent(userId);
        return ResponseEntity.ok(ducksSent);
    }

    /**
     * sendDuck sends a duck from the user currently authenticated to another one and save
     * the transaction on the database. The id of the transmitter and the receiver are sent by path.
     * @param receiverId id from the receiver
     * @return ResponseEntity with a 200 status code and a CollectionModel with the implied users in the body
     */
    //TODO: Must check whether the user is close enough to send a Duck
    @PostMapping(SEND_DUCK)
    public ResponseEntity<?> sendDuck(@RequestParam Long receiverId) {
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_SERVICE.findById(receiverId);
        Duck duckSent = DUCK_SERVICE.sendDuck(sender, receiver);
        return ResponseEntity.status(HttpStatus.CREATED).body(duckSent);
    }

    /**
     * Deletes a Duck object sent by the currently authenticated user with the given receiverId.
     * @param receiverId the ID of the receiver User
     * @return a ResponseEntity with a message indicating the success of the operation
     */
    @DeleteMapping(DELETE_RECLAIM_DUCK)
    @Transactional
    public ResponseEntity<?> removeDuck(@RequestParam Long receiverId) {
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_SERVICE.findById(receiverId);
        DUCK_SERVICE.removeDuck(sender, receiver);
        return ResponseEntity.noContent().build();
    }
}
