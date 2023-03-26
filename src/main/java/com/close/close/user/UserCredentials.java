package com.close.close.user;

/**
 * Stores both user phone number and user password (not encrypted) for future user login checks.
 */
public class UserCredentials {
    /** users phone number **/
    private String phone;

    /** users plain password **/
    private String password;


    /**
     * Cronstructs a new UserCredential with a phone and password.
     * @param phone
     * @param password
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
     * @param userName Users new name.
     */
    public void setUserName(String userName) { this.phone = userName; }

    /**
     * @return The users password.
     */
    public String getPassword() { return password; }
    /**
     * @param password Users new password
     */
    public void setPassword(String password) { this.password = password; }
}
