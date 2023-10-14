package com.close.close.user;

import com.close.close.chat.Chat;
import com.close.close.chat.Message;
import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import com.close.close.socialnetwork.SocialNetwork;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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

    @Column(nullable = false)
    private String notificationDeviceID;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> messagesSent;

    @ElementCollection( fetch = FetchType.EAGER)
    @CollectionTable(name="socialNetworks")
    @MapKeyColumn(name="socialNetwork")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "username")
    @Fetch(FetchMode.SELECT)
    private Map<SocialNetwork, String> socialNetworks;

    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    private Set<Duck> ducksSent;

    @OneToMany(mappedBy = "receiver",fetch = FetchType.EAGER)
    private Set<Duck> ducksReceived;

    @Column()
    private String photo;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "has_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @Fetch(FetchMode.SELECT)
    private Set<Interest> interests;

    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    public User (Long id, String username, String profileName, String password, Role role){
        this.id=id;
        this.username=username;
        this.profileName=profileName;
        this.password=password;
        this.role=role;
        this.interests = new HashSet<>();
    }


    public User() {
    }

    public void addDuckSent(Duck duck){
        this.ducksSent.add(duck);
    }
    public void removeDuckSent(Duck duck){
        this.ducksSent.remove(duck);
    }
    public void addDuckReceived(Duck duck){
        this.ducksReceived.add(duck);
    }
    public void removeDuckReceived(Duck duck){
        this.ducksReceived.remove(duck);
    }

    public int getDucksReceived() {
        return ducksReceived.size();
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void addSocialNetwork(SocialNetwork socialNetwork, String username){
        this.socialNetworks.put(socialNetwork,username);
    }
    public void removeSocialNetwork(SocialNetwork socialNetwork){
        this.socialNetworks.remove(socialNetwork);
    }

    public void addInterest(Interest interest){
        this.interests.add(interest);
    }
    public void removeInterest(Interest interest){
        this.interests.remove(interest);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getNotificationDeviceID() {
        return notificationDeviceID;
    }

    public void setNotificationDeviceID(String notificationDeviceID) {
        this.notificationDeviceID = notificationDeviceID;
    }

    public Role getRole() {
        return role;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public Map<SocialNetwork, String> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Map<SocialNetwork, String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public Set<Duck> getDucksSent() {
        return ducksSent;
    }

    public void setDucksSent(Set<Duck> ducksSent) {
        this.ducksSent = ducksSent;
    }

    public void setDucksReceived(Set<Duck> ducksReceived) {
        this.ducksReceived = ducksReceived;
    }

    public String getPhoto() {
        return photo;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}