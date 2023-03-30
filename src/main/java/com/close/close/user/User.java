/**
 * user package is in charge of managing the application user's logic.
 * This includes its modeling, API requests handling, DB interactions, etc.
 */
package com.close.close.user;

import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *User is the application user model.
 */
@Entity
public class User implements UserDetails {
    /**
     * id is the id of the user. This is a long type number.
     * It's marked as a generated value.
     */
    private @Id @GeneratedValue Long id;

    /**
     * username is a string with the user's name.
     * It's not nullable.
     */
    @Column(nullable=false)
    private String username;

    /**
     * age is the user's age
    */
    @Column(nullable = false)
    private int age;

    /**
     * password of the user
     */
    @Column(nullable = false)
    private String password;

    /**
     * role the user takes in the app
     */
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    public String getPassword() { return password; }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * Changes the password value
     * @param password new password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Indicates the User phone. In this class, the Phone is unique for every user
     * @return String with the User phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Changes the user phone value
     * @param phone new Phone value
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Indicates weather the user's phone is verified or not
     * @return <code>true</code> if the phone is verified. <code>false</code> if it's not
     */
    public boolean isPhoneIsVerified() {
        return phoneIsVerified;
    }

    /**
     * Changes the user's phone value
     * @param phoneIsVerified String with the new phone value
     */
    public void setPhoneIsVerified(boolean phoneIsVerified) {
        this.phoneIsVerified = phoneIsVerified;
    }

    /**
     * Gets a collections of strings with the User's photos
     * @return Collection of strings with the link to the user's photos
     */
    public Set<String> getPhotos() {
        return photos;
    }

    /**
     * Changes the user's photos
     * @param photos new user's photos
     */
    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }

    /**
     * Gets the user's interests
     * @return Collection of the Interest objects which are linked with the user
     */
    public Set<Interest> getInterests() {
        return interests;
    }

    /**
     * Changes the user's interests
     * @param interests New interests collection
     */
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
}