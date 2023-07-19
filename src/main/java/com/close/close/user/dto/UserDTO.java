package com.close.close.user.dto;
import com.close.close.apirest.dto.DTO;
import com.close.close.interest.Interest;
import com.close.close.socialnetwork.SocialNetwork;
import com.close.close.user.User;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class UserDTO extends DTO<User> implements Serializable {
    private Long id;
    private String username;
    private String profileName;
    private Map<SocialNetwork, String> socialNetworks;
    private String photo;
    private Set<Interest> interests;

    public UserDTO(User user) {
        super(user);
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


    protected void initializeFields(){
            this.id = (Long)getPrivateField("id");
            this.username = (String)getPrivateField("username");
            this.profileName = (String)getPrivateField("profileName");
            this.photo = (String)getPrivateField("photo");
            this.interests = (Set<Interest>)getPrivateField("interests");
            this.socialNetworks = (Map<SocialNetwork, String>)getPrivateField("socialNetworks");
    }

}