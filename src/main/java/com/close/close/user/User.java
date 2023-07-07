package com.close.close.user;

import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import com.close.close.message.Message;
import com.close.close.socialnetwork.SocialNetwork;
import com.close.close.user.dto.UserDTO;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ElementCollection( fetch = FetchType.EAGER)
    @CollectionTable(name="socialNetworks")
    @MapKeyColumn(name="socialNetwork")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "username")
    private Map<SocialNetwork, String> socialNetworks;

    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    private Set<Duck> ducksSent;

    @OneToMany(mappedBy = "receiver",fetch = FetchType.EAGER)
    private Set<Duck> ducksReceived;

    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER)
    private Set<Message> messagesSent;
    @OneToMany(mappedBy = "receiver",fetch = FetchType.EAGER)
    private Set<Message> messageReceived;

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

    public String getProfileName() {
        return profileName;
    }

    public Role getRole() {
        return role;
    }

    public Map<SocialNetwork, String> getSocialNetworks() {
        return socialNetworks;
    }

    public Set<Duck> getDucksSent() {
        return ducksSent;
    }

    public Set<Duck> getDucksReceived() {
        return ducksReceived;
    }

    public Set<Message> getMessagesSent() {
        return messagesSent;
    }

    public Set<Message> getMessageReceived() {
        return messageReceived;
    }

    public String getPhoto() {
        return photo;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public int getNumberOfDucksReceived(){return this.ducksReceived.size();}

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
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}