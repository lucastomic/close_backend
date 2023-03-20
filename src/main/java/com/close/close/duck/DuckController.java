package com.close.close.duck;


import com.close.close.apirest.UserUtils;
import com.close.close.user.User;
import com.close.close.user.UserModelAssembler;
import com.close.close.user.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class DuckController {
    private DuckRepository repository;
    private UserRepository userRepository;
    private UserModelAssembler userModelAssembler;
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
    ResponseEntity sendDuck(@RequestParam Long transmitterId, @RequestParam Long receiverId) {
        UserUtils userUtils = new UserUtils(userRepository,userModelAssembler);
        User transmitter = userUtils.findOrThrow(transmitterId);
        User receiver = userUtils.findOrThrow(receiverId);
        Duck duckSent = new Duck(transmitter, receiver);
        repository.save(duckSent);
        List<User> usersList = List.of(receiver, transmitter);
        CollectionModel<EntityModel<User>> body = userUtils.collectionModelFromList(usersList);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/ducksReceived/{id}")
    CollectionModel<EntityModel<User>> getDucksReceived(@PathVariable Long id){

        //TODO: implement
    }

}
