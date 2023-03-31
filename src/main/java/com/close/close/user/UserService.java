package com.close.close.user;

import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserService manages all the User logic. Such as creating a user, finding one, etc.
 */
@Service
public class UserService {

    private final UserRepository USER_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        USER_REPOSITORY = userRepository;
        PASSWORD_ENCODER = passwordEncoder;
    }

    /**
     * Retrieves all the users stored in the system
     * @return Collection of all the User objects
     */
    public List<User> getAll() {
        return USER_REPOSITORY.findAll();
    }

    /**
     * Retrieves a user given its ID
     * @param userId Long with the user's ID
     * @return User object with the user with the given ID if it exists. If
     * there isn't it throws an exception
     */
    public User findById(Long userId) {
        return USER_REPOSITORY.findById(userId).orElseThrow();
    }

    /**
     * Creates a new User storing it in the database, if there is no other user with the same
     * number phone and username
     * @param newUser User object to create
     * @return If the user was created successfully, it returns the user created
     */
    public User create(User newUser) {
        //Check if there is a user already registered with such number.
        //TODO: CHANGE EXCEPTION (CREATE NEW ONE)
        //TODO: REVIEW (LUCAS TOMIC)
        if (USER_REPOSITORY.findByPhone(newUser.getPhone()).isPresent())
            throw new UserNotFoundException(newUser.getId());
        //TODO: CHECK USERNAME ALSO

        newUser.setPassword(PASSWORD_ENCODER.encode(newUser.getPassword()));
        USER_REPOSITORY.save(newUser);
        return newUser;
    }

    /**
     * Deletes a user given their ID. If there is not any user with this Id, it
     * throws an exception
     * @param userId Long with the id of the User to delete
     */
    public void delete(Long userId) {
        Optional<User> user = USER_REPOSITORY.findById(userId);
        //If userId is not found
        if (user.isEmpty()) throw new UserNotFoundException(userId);

        USER_REPOSITORY.delete(user.get());
    }
}
