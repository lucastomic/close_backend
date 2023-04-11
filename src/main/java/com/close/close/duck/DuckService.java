package com.close.close.duck;

import com.close.close.apirest.UserUtils;
import com.close.close.security.AuthenticationService;
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
    private final AuthenticationService AUTH_SERVICE;


    @Autowired
    public DuckService (DuckRepository duckRepository,
                        UserRepository userRepository,
                        AuthenticationService authenticationService) {
        this.DUCK_REPOSITORY = duckRepository;
        this.USER_REPOSITORY = userRepository;
        this.AUTH_SERVICE = authenticationService;
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

    /**
     * Get the Ducks which have been sent by the currently authenticated
     * user
     * @return List with all the ducks sent by the user
     */
    public List<Duck> getDucksSent(){
        User user = AUTH_SERVICE.getAuthenticated();
        return DUCK_REPOSITORY.findDucksSent(user.getId());
    }

    /**
     * Sends a duck from the user currently authenticated to the user whose
     * ID is specified by parameter.
     * @param receiverId parameter of the duck's receiver
     * @return Duck object of the Duck which has been just sended
     */
    @Transactional
    public Duck sendDuck(Long receiverId) {
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_REPOSITORY.findById(receiverId)
                .orElseThrow( () -> new UserNotFoundException(receiverId));
        Duck duckSent = new Duck(sender, receiver);
        DUCK_REPOSITORY.save(duckSent);
        return duckSent;
    }

    /**
     * Removes a Duck who have been sent previously by the
     * User currently authenticated, given the receiver ID.
     * @param receivedId Long with the receiver ID
     * @return Duck which has been just deleted
     */
    @Transactional
    public Duck removeDuck(Long receivedId) {
        User sender = AUTH_SERVICE.getAuthenticated();
        User receiver = USER_REPOSITORY.findById(receivedId)
                .orElseThrow( () -> new UserNotFoundException(receivedId));
        Duck duck = DUCK_REPOSITORY.findBySenderReceiver(sender.getId(), receivedId)
                .orElseThrow( () -> new DuckNotFoundException(sender.getId(), receivedId));
        DUCK_REPOSITORY.delete(duck);
        return duck;
    }
}
