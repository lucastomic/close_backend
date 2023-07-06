package com.close.close.socialnetwork;

import com.close.close.user.User;
import com.close.close.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;

@Service
public class SocialNetworkService {
    private final UserRepository USER_REPOSITORY;

    @Autowired
    public SocialNetworkService(UserRepository USER_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    void addSocialNetwork(User user, SocialNetwork socialNetwork, String username){
        user.addSocialNetwork(socialNetwork, username);
        USER_REPOSITORY.save(user);
    }

    void removeSocialNetwork(User user, SocialNetwork socialNetwork){
        user.removeSocialNetwork(socialNetwork);
        USER_REPOSITORY.save(user);
    }
}
