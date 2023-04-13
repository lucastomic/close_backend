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


    @Autowired
    public DuckService (DuckRepository duckRepository) {
        this.DUCK_REPOSITORY = duckRepository;
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
    public List<Duck> getDucksSent(User user){
        return DUCK_REPOSITORY.findDucksSent(user.getId());
    }

    /**
     * Sends a duck from a user to other
     * @param sender User who sends the Duck
     * @param receiver User who receives the Duck
     * @return Duck object with the duck which has been just sent
     */
    @Transactional
    public Duck sendDuck(User sender, User receiver) {
        Duck duckSent = new Duck(sender, receiver);
        DUCK_REPOSITORY.save(duckSent);
        return duckSent;
    }

    /**
     * Removes a Duck who have been previously sent given the receiver and sender ID.
     * @param sender The user who has sent the duck
     * @param receiver The user who has received the duck
     * @return Duck which has been just deleted
     */
    @Transactional
    public Duck removeDuck(User sender, User receiver) {
        Duck duck = DUCK_REPOSITORY.findBySenderReceiver(sender.getId(), receiver.getId())
                .orElseThrow( () -> new DuckNotFoundException(sender.getId(), receiver.getId()));
        DUCK_REPOSITORY.delete(duck);
        return duck;
    }
}
