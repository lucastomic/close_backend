/**
 * user package is in charge of managing the application user's logic.
 * This includes its modeling, API requests handling, DB interactions, etc.
 */
package com.close.close.user;

import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import com.close.close.message.Message;
import com.close.close.socialnetwork.SocialNetwork;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * User is the application user model.
 */
@Entity
public class User implements UserDetails {
    public static final int MINIMUM_PASSWORD_LENGTH = 5;

    private @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String profileName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ElementCollection( fetch = FetchType.EAGER)
    @CollectionTable(name="socialNetworks")
    @MapKeyColumn(name="socialNetowrk")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "username")
    private Map<SocialNetwork, String> socialNetworks;

    @OneToMany(mappedBy = "sender")
    private Set<Duck> ducksSent;

    @OneToMany(mappedBy = "receiver")
    private Set<Duck> ducksReceived;

    @OneToMany(mappedBy = "sender")
    private Set<Message> messagesSent;
    @OneToMany(mappedBy = "receiver")
    private Set<Message> messageReceived;

    @Column()
    private String photo;

    /**
     * interests are the user's interests set.
     * As this is a M-N relation it's saved in a different table.
     */
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "has_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests;

    public User (Long id, String username, String profileName, String password, Role role){
        this.id=id;
        this.username=username;
        this.profileName=profileName;
        this.password=password;
        this.role=role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    public void addSocialNetwork(SocialNetwork socialNetwork, String username){
        this.socialNetworks.put(socialNetwork,username);
    }
    public void removeSocialNetowrk(SocialNetwork socialNetwork){
        this.socialNetworks.remove(socialNetwork);
    }

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
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<SocialNetwork,String> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Map<SocialNetwork,String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    /**
     * Changes the password value
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets a collections of strings with the User's photos
     *
     * @return Collection of strings with the link to the user's photos
     */
    public String getPhoto() {
        return photo;
    }


    public void setPhotos(String photo) {
        this.photo = photo;
    }

    /**
     * Gets the user's interests
     *
     * @return Collection of the Interest objects which are linked with the user
     */
    public Set<Interest> getInterests() {
        return interests;
    }

    /**
     * Changes the user's interests
     *
     * @param interests New interests collection
     */
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    /**
     * Adds an interest into the user's interests
     * @param interest interest to add
     */
    public void addInterest(Interest interest){
        this.interests.add(interest);
    }

    /**
     * Remove an interest from the user's interests
     * @param interest interest to add
     */
    public void removeInterest(Interest interest){
        this.interests.remove(interest);
    }

    /**
     * Gets the user profile name
     *
     * @return String with the profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Changes the profile name of the user
     *
     * @param profileName New profile name
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Return if this user is equal to another object
     *
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

}