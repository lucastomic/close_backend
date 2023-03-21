package com.close.close.duck;

/**
 * DuckToItselfException is thrown when a user sends a duck to itself.
 * This is, when is tried to save a Duck entity with the same receiver and sender ID.
 */
public class DuckToItselfException extends RuntimeException{
   DuckToItselfException(Long id){
       super("A user can't send a duck to itself. ID:" + id);
   }
}
