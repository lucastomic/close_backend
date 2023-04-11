package com.close.close.duck;

import com.close.close.user.UserNotFoundException;
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
    public static final String GET_USER_RECEIVED_DUCKS = "/users/{userId}/ducks/received";
    public static final String GET_USER_SENT_DUCKS     = "/users/{userId}/ducks/sent";
    public static final String GET_DUCKS_SENT     = "/users/ducks/sent";
    public static final String SEND_DUCK          = "/users/ducks/send";
    public static final String DELETE_RECLAIM_DUCK     = "/users/ducks/reclaim";

    private final DuckService DUCK_SERVICE;


    /**
     * class constructor with dependency injection
     */
    DuckController(DuckService duckService){
        this.DUCK_SERVICE = duckService;
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
     * duck to the user with the specified ID.
     * @param userId id of the user to know who have sent a duck to
     * @return CollectionModel with all the users who have sent a duck to this user
     */
    @GetMapping(GET_USER_RECEIVED_DUCKS)
    public ResponseEntity<List<Duck>> getDucksReceived(@PathVariable Long userId) {
        List<Duck> ducksReceived = DUCK_SERVICE.findDucksReceived(userId);
        return ResponseEntity.ok(ducksReceived);
    }

    @GetMapping(GET_USER_SENT_DUCKS)
    public ResponseEntity<List<Duck>> getDucksSent(@PathVariable Long userId) {
        List<Duck> ducksSent = DUCK_SERVICE.findDucksSent(userId);
        return ResponseEntity.ok(ducksSent);
    }

    /**
     * Get the Ducks which have been sent by the currently authenticated
     * user
     * @return Response entity with a 200 OK status code and the list of ducks in the body
     */
    @GetMapping(GET_DUCKS_SENT)
    public ResponseEntity<List<Duck>> getDucksSent() {
        List<Duck> ducksSent = DUCK_SERVICE.getDucksSent();
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
        Duck duckSent = DUCK_SERVICE.sendDuck(receiverId);
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
        DUCK_SERVICE.removeDuck(receiverId);
        return ResponseEntity.noContent().build();
    }
}
