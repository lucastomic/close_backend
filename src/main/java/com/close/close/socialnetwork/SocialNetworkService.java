package com.close.close.socialnetwork;

import com.close.close.user.User;
import com.close.close.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworkService {
    private final SocialNetworkRepository SOCIAL_NETWORK_REPOSITORY;
    private final UserRepository USER_REPOSITORY;

    @Autowired
    public SocialNetworkService(SocialNetworkRepository DUCK_REPOSITORY, UserRepository USER_REPOSITORY) {
        this.SOCIAL_NETWORK_REPOSITORY = DUCK_REPOSITORY;
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    void addSocialNetwork(User user, SocialNetwork socialNetwork){
        user.addSocialNetwork(socialNetwork);
        socialNetwork.setUser(user);
        SOCIAL_NETWORK_REPOSITORY.save(socialNetwork);
        USER_REPOSITORY.save(user);
    }
}
