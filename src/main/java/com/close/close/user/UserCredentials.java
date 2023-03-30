package com.close.close.user;

/**
 * Stores both user phone number and user password (not encrypted) for future user login checks.
 * @param phone Users phone number
 * @param password Users plain password
 */
public record UserCredentials (String phone, String password) {

    /**
     * Constructs a new UserCredential with a phone and password.
     * @param phone Users phone number
     * @param password Users plain password
     */
    public UserCredentials(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }


    /**
     * @return The users phone number.
     */
    public String getPhone() { return phone; }

    /**
     * @return The users' password.
     */
    public String getPassword() { return password; }

}
