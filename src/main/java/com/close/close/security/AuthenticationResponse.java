package com.close.close.security;

/**
 * AuthenticationResponse is the object returned when a User makes a
 * authentication operation (sign in, register, etc)
 */
public class AuthenticationResponse {
    /**
     * Json Web Token obtained in the operation
     */
    private String token;

    /**
     Constructor for creating a new AuthenticationResponse object with the given token.
     @param token the token to be set in the new AuthenticationResponse object.
     */
    public AuthenticationResponse(String token) {
        this.token = token;
    }
    /**
     Gets the token currently set in the AuthenticationResponse object.
     @return the current token.
     */
    public String getToken() {
        return token;
    }
    /**
     Sets the token in the AuthenticationResponse object.
     @param token the token to be set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
