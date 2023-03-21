package com.close.close.user;

/**
 * Exception thrown every time a user is looked for and not found.
 */
public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(){
        super("User not found");
    }
    public UserNotFoundException(Long id){
        super("Not found user with id " + id);
    }
}
