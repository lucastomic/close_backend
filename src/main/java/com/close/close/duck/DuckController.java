package com.close.close.duck;

import com.close.close.apirest.UserUtils;
import com.close.close.user.User;
import com.close.close.user.UserModelAssembler;
import com.close.close.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    public static final String POST_SEND_DUCK          = "/users/{senderId}/ducks/send/{receiverId}";
    public static final String DELETE_RECLAIM_DUCK     = "/users/{reclaimerId}/ducks/reclaim/{receivedId}";

    /**
     * repository is the duck's implementations of the repository pattern.
     * Handles all the duck's DB interactions
     */
    private DuckRepository repository;
    /**
     * userRepository is the user's implementation of the repository pattern.
     * Handles all the user's DB interactions
     */
    private UserRepository userRepository;
    /**
     * userModelAssmebler manages the User entity modeling
     */
    private UserModelAssembler userModelAssembler;
    /**
     * entityManager is in charge of managing SQL queries.
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * class constructor with dependency injection
     */
    DuckController(DuckRepository repository, UserRepository userRepository, UserModelAssembler userModelAssembler){
        this.userModelAssembler = userModelAssembler;
        this.userRepository = userRepository;
        this.repository = repository;
    }


    /**
     * @return All ducks stored in the database.
     */
    @GetMapping(GET_DUCKS)
    public ResponseEntity<?> allDucks() {
        List<Duck> ducks = repository.findAll();
        return ResponseEntity.ok(ducks);
    }

    /**
     * getDucksReceived return a collection of the users who have sent a
     * duck to the user with the specified ID.
     * @param userId id of the user to know who have sent a duck to
     * @return CollectionModel with all the users who have sent a duck to this user
     */
    @GetMapping(GET_USER_RECEIVED_DUCKS)
    public CollectionModel<EntityModel<User>> getDucksReceived(@PathVariable Long userId){
        String queryString = "SELECT d.sender FROM Duck d WHERE d.receiver.id = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", userId);
        List<User> users = query.getResultList();
        UserUtils userUtils = new UserUtils(userRepository,userModelAssembler);
        return userUtils.collectionModelFromList(users);
    }

    //TODO Get User Sent Ducks

    /**
     * sendDuck sends a duck from a user to another one and save the transaction on the database.
     * The id of the transmitter and the receiver are sent by path.
     * @param senderId id from the transmitter
     * @param receiverId id from the receiver
     * @return ResponseEntity with a 200 status code and a CollectionModel with the implied users in the body
     */
    @PostMapping(POST_SEND_DUCK)
    public ResponseEntity<?> sendDuck(@RequestParam Long senderId, @RequestParam Long receiverId) {
        UserUtils userUtils = new UserUtils(userRepository,userModelAssembler);
        User transmitter = userUtils.findOrThrow(senderId);
        User receiver = userUtils.findOrThrow(receiverId);
        Duck duckSent = new Duck(transmitter, receiver);
        repository.save(duckSent);
        List<User> usersList = List.of(receiver, transmitter);
        CollectionModel<EntityModel<User>> body = userUtils.collectionModelFromList(usersList);
        return ResponseEntity.ok().body(body);
    }

    /**
     * Deletes a Duck object with the given senderId and receiverId.
     * @param reclaimerId the ID of the sender User
     * @param receivedId the ID of the receiver User
     * @return a ResponseEntity with a message indicating the success of the operation
     */
    //TODO Complete/Review this
    @DeleteMapping(DELETE_RECLAIM_DUCK)
    @Transactional
    public ResponseEntity<?> removeDuck(@PathVariable Long reclaimerId, @PathVariable Long receivedId) {
        UserUtils userUtils = new UserUtils(userRepository, userModelAssembler);
        User sender = userUtils.findOrThrow(reclaimerId);
        User receiver = userUtils.findOrThrow(receivedId);
        Duck removedDuck = entityManager.find(Duck.class, new DuckId(reclaimerId, receivedId));
        if (removedDuck == null) {
            throw new DuckNotFound(reclaimerId, receivedId);
        }
        try {
            entityManager.remove(removedDuck);
            return ResponseEntity.ok().body("Duck object has been deleted successfully.");
        } catch (Exception e) {
            throw new DuckNotFound(reclaimerId, receivedId);
        }
    }
}
