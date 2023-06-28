package com.close.close.socialnetwork;

import com.close.close.user.User;
import jakarta.persistence.*;

@Entity
@IdClass(SocialNetworkId.class)
public class SocialNetwork {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ESocialNetwork socialNetwork;

    @Id
    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    @Column(nullable = false)
    String username;

    SocialNetwork(ESocialNetwork socialNetwork, String username){
        this.socialNetwork = socialNetwork;
        this.username = username;
    };

    public SocialNetwork() {
    }

    public ESocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(ESocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
