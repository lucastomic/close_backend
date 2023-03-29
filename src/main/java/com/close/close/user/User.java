/**
 * user package is in charge of managing the application user's logic.
 * This includes its modeling, API requests handling, DB interactions, etc.
 */
package com.close.close.user;

import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import jakarta.persistence.*;

import java.util.Objects;
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
    @Column(nullable = false)
    private int age;

    /**
     *
     */
    @Column(nullable = false)
    private String password;

    /**
     * ducksSent is the collection of DuckShipping where the user is the sender.
     */
    @OneToMany(mappedBy = "sender")
    private Set<Duck> ducksSent;

    /**
     * ducksSent is the collection of DuckShipping where the user is the receiver.
     */
    @OneToMany(mappedBy = "receiver")
    private Set<Duck> ducksReceived;

    /**
     * phone is a string with the user's phone number.
     * This column is mandatory
     */
    @Column(nullable = false, unique = true)
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


    /**
     * Return if this user is equal to another object
     * @param o object to compare with this user
     * @return whether the object is the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.id, user.getId());
    }


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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

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