package com.close.close.duck;

import com.close.close.apirest.UserUtils;
import com.close.close.user.User;
import com.close.close.user.UserController;
import com.close.close.user.UserNotFoundException;
import com.close.close.user.UserRepository;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DuckService {

    /**
     * repository is the duck's implementations of the repository pattern.
     * Handles all the duck's DB interactions
     */
    private final DuckRepository DUCK_REPOSITORY;
    private final UserRepository USER_REPOSITORY;


    @Autowired
    public DuckService (DuckRepository duckRepository,
                        UserRepository userRepository) {
        this.DUCK_REPOSITORY = duckRepository;
        this.USER_REPOSITORY = userRepository;
    }


    public List<Duck> findAll() {
        return DUCK_REPOSITORY.findAll();
    }

    public List<Duck> findDucksReceived(Long userId){
        return DUCK_REPOSITORY.findDucksReceived(userId);
    }

    public List<Duck> findDucksSent(Long userId) {
        return DUCK_REPOSITORY.findDucksSent(userId);
    }

    @Transactional
    public Duck sendDuck(Long senderId, Long receiverId) {
        User sender = USER_REPOSITORY.findById(senderId)
                .orElseThrow( () -> new UserNotFoundException(senderId));
        User receiver = USER_REPOSITORY.findById(receiverId)
                .orElseThrow( () -> new UserNotFoundException(receiverId));
        Duck duckSent = new Duck(sender, receiver);
        DUCK_REPOSITORY.save(duckSent);
        return duckSent;
    }

    @Transactional
    public Duck removeDuck(Long reclaimerId, Long receivedId) {
        User sender = USER_REPOSITORY.findById(reclaimerId)
                .orElseThrow( () -> new UserNotFoundException(receivedId));
        User receiver = USER_REPOSITORY.findById(receivedId)
                .orElseThrow( () -> new UserNotFoundException(receivedId));
        Duck duck = DUCK_REPOSITORY.findBySenderReceiver(reclaimerId, receivedId)
                .orElseThrow( () -> new DuckNotFoundException(reclaimerId, receivedId));
        DUCK_REPOSITORY.delete(duck);
        return duck;
    }
}
