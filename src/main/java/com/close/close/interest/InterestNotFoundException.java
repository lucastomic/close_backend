package com.close.close.interest;

/**
 * InterestNotFoundException indicates that no interest have been found with the specifications given
 */
public class InterestNotFoundException extends RuntimeException{
    InterestNotFoundException(){
        super("No interest found");
    }
}
