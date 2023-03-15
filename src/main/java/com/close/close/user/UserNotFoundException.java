package com.close.close.user;

/**
 * Exception thrown every time a user is looked for and n ot found.
 */
public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(){
        super("User not found");
    }
}
