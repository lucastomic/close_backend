package com.close.close.user.dto;
import com.close.close.duck.Duck;
import com.close.close.interest.Interest;
import com.close.close.message.Message;
import com.close.close.socialnetwork.SocialNetwork;
import com.close.close.user.Role;
import com.close.close.user.User;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.Serializable;
import java.util.*;

public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String profileName;
    private Map<SocialNetwork, String> socialNetworks;
    private String photo;
    private Set<Interest> interests;

    public UserDTO(Long id, String username, String profileName, Map<SocialNetwork, String> socialNetworks, String photo, Set<Interest> interests) {
        this.id = id;
        this.username = username;
        this.profileName = profileName;
        this.socialNetworks = socialNetworks;
        this.photo = photo;
        this.interests = interests;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Map<SocialNetwork,String> getSocialNetworks() {
        return socialNetworks;
    }
    public void setSocialNetworks(Map<SocialNetwork,String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhotos(String photo) {
        this.photo = photo;
    }
    public Set<Interest> getInterests() {
        return interests;
    }
    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }
    public String getProfileName() {
        return profileName;
    }
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}