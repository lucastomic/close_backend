package com.close.close.user;

import com.close.close.interest.Interest;
import com.close.close.security.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

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
        return USER_REPOSITORY.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    /**
     * Retrieves a user given its username
     * @param username String with username
     * @return User with the given username. If there isn't, it throws an exception
     */
    public User findByUsername(String username){
        return USER_REPOSITORY.findByUsername(username).orElseThrow();
    }


    /**
     * Adds an interest to a user
     * @param user user to add the interest
     * @param interest interest to be added
     */
    public void addInterest(User user, Interest interest){
        user.addInterest(interest);
        USER_REPOSITORY.save(user);
    }

    /**
     * Deletes an interest to a user
     * @param user user to delete the interest
     * @param interest interest to be deleted
     */
    public void removeInterest(User user, Interest interest){
        user.removeInterest(interest);
        USER_REPOSITORY.save(user);
    }

    public User create(User newUser) {
        if (!validatePassword(newUser.getPassword())){
            throw new InvalidPasswordException();
        }
        this.encodePassword(newUser);
        try{
            USER_REPOSITORY.save(newUser);
        }catch (DataIntegrityViolationException e){
            throw new UsernameDuplicatedException(newUser.getUsername());
        }
        return newUser;
    }

    private boolean validatePassword(String password){
        return password.length() > User.MINIMUM_PASSWORD_LENGTH;
    }

    /**
     * Encodes a user password, given the user
     * @param user user to encode the password
     */
    private void encodePassword(User user){
        String decryptedPassword = user.getPassword();
        String encodedPassword = PASSWORD_ENCODER.encode(decryptedPassword);
        user.setPassword(encodedPassword);
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
    public Optional<User> getInfoById(Long userId){
        return USER_REPOSITORY.findById(userId);
    }

    public void editProfilePhoto(User user,String photoURL){
        user.setPhoto(photoURL);
        USER_REPOSITORY.save(user);
    }
}
