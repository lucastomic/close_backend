/**
 * user package is in charge of managing the application user's logic.
 * This includes its modeling, API requests handling, DB interactions, etc.
 */
package com.close.close.user;

import com.close.close.interest.Interest;
import jakarta.persistence.*;
import java.util.Set;

/**
 *User is the application user model.
 */
@Entity
public class User{
    /**
     * id is the id of the user. This is a long type number.
     * It's marked as a generated value.
     */
    private @Id @GeneratedValue Long id;
    /**
     * name is a string with the user's name.
     * It's not nullable.
     */
    @Column(nullable=false)
    private String name;
    /**
     * age is the user's age
    */
    private int age;
    /**
     * duckedUsers is the list of users who the user has sent ducks.
     * This is a N-M relation. So, it generates another table called "sendsDuck".
     */
    @ManyToMany
    @JoinTable(
            name = "sendsDuck",
            joinColumns = @JoinColumn(name = "sender"),
            inverseJoinColumns = @JoinColumn(name = "receiver")
    )
    private Set<User> duckedUsers;
    /**
     * receivesDuckFrom is the list of users who have sent a duck to the user.
     * This relation is mapped before in the duckedUsers atribute.
     */
    @ManyToMany(mappedBy = "duckedUsers")
    private Set<User> receivesDuckFrom;
    /**
     * phone is a string with the user's phone number.
     * This column is mandatory
     */
    @Column(nullable = false)
    private String phone;


    /**
     * phoneIsVerified checks whether the user's phone number has been verified or not.
     * This verification is compulsory for the app usage.
     * It's not nullable and has a default value un false (because the user can't verify his phone number
     * before the user is created)
     */
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean phoneIsVerified;
    /**
     * photos is the list of user's photos. They are in string format to a URL.
     * Photos are saved in a different table
     */
    @ElementCollection
    private Set<String> photos;
    /**
     * interests are the user's interests set.
     * As this is a M-N relation it's saved in a different table.
     */
    @ManyToMany
    @JoinTable(
        name = "hasInterest",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests;


    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<User> getDuckedUsers() {
        return duckedUsers;
    }

    public void setDuckedUsers(Set<User> duckedUsers) {
        this.duckedUsers = duckedUsers;
    }

    public Set<User> getReceivesDuckFrom() {
        return receivesDuckFrom;
    }

    public void setReceivesDuckFrom(Set<User> receivesDuckFrom) {
        this.receivesDuckFrom = receivesDuckFrom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneIsVerified() {
        return phoneIsVerified;
    }

    public void setPhoneIsVerified(boolean phoneIsVerified) {
        this.phoneIsVerified = phoneIsVerified;
    }

    public Set<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
}