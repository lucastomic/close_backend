package com.close.close.security;

/**
 * AuthenticationRequest is the request sent when a user wants to sign in.
 * It contains the credential a user needs to authenticate.
 */
public class AuthenticationRequest {
    private String username;
    private String password;
    private String deviceID;

    public AuthenticationRequest(String username, String password, String deviceID) {
        this.username = username;
        this.password = password;
        this.deviceID = deviceID;
    }

    /**

     Gets the username currently set in the object.
     @return the current username.
     */
    public String getUsername() {
        return username;
    }
    /**

     Sets the username in the object.
     @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**

     Gets the password currently set in the object.
     @return the current password.
     */
    public String getPassword() {
        return password;
    }
    /**

     Sets the password in the object.
     @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
