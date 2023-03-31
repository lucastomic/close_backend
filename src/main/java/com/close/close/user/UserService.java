package com.close.close.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public List<User> getAll() {
        return USER_REPOSITORY.findAll();
    }

    public User findById(Long userId) {
        return USER_REPOSITORY.findById(userId).orElseThrow();
    }

    public User create(User newUser) {
        //Check if there is a user already registered with such number.
        //TODO: CHANGE EXCEPTION (CREATE NEW ONE)
        if (USER_REPOSITORY.findByPhone(newUser.getPhone()).isPresent())
            throw new UserNotFoundException(newUser.getId());

        newUser.setPassword(PASSWORD_ENCODER.encode(newUser.getPassword()));
        USER_REPOSITORY.save(newUser);
        return newUser;
    }

    public void delete(Long userId) {
        Optional<User> user = USER_REPOSITORY.findById(userId);
        //If userId is not found
        if (user.isEmpty()) throw new UserNotFoundException(userId);

        USER_REPOSITORY.delete(user.get());
    }
}
