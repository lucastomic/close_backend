package com.close.close.duck;

import com.close.close.apirest.UserUtils;
import com.close.close.user.User;
import com.close.close.user.UserModelAssembler;
import com.close.close.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * DuckController is the controller for all duck's logic interactions
 */
@RestController
public class DuckController {
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
     * sendDuck sends a duck from a user to another one and save the transaction on the database.
     * The id of the transmitter and the receiver are sent by path.
     * @param transmitterId id from the transmitter
     * @param receiverId id from the receiver
     * @return ResponseEntity with a 200 status code and a CollectionModel with the implied users in the body
     */
    @PostMapping("/sendDuck")
    public ResponseEntity sendDuck(@RequestParam Long transmitterId, @RequestParam Long receiverId) {
        UserUtils userUtils = new UserUtils(userRepository,userModelAssembler);
        User transmitter = userUtils.findOrThrow(transmitterId);
        User receiver = userUtils.findOrThrow(receiverId);
        Duck duckSent = new Duck(transmitter, receiver);
        repository.save(duckSent);
        List<User> usersList = List.of(receiver, transmitter);
        CollectionModel<EntityModel<User>> body = userUtils.collectionModelFromList(usersList);
        return ResponseEntity.ok().body(body);
    }

    /**
     * getDucksReceived return a collection of the users who have sent a
     * duck to the user with the specified ID.
     * @param id id of the user to know who have sent a duck to
     * @return CollectionModel with all the users who have sent a duck to this user
     */
    @GetMapping("/ducksReceived/{id}")
    CollectionModel<EntityModel<User>> getDucksReceived(@PathVariable Long id){
       String queryString = "SELECT d.sender FROM Duck d WHERE d.receiver.id = :id";
       Query query = entityManager.createQuery(queryString).setParameter("id",id);
       List<User> users = query.getResultList();
       UserUtils userUtils = new UserUtils(userRepository,userModelAssembler);
       return userUtils.collectionModelFromList(users);
    }

}
