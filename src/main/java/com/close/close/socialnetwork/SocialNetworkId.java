package com.close.close.socialnetwork;

import com.close.close.user.User;

import java.io.Serializable;
import java.util.Objects;

public class SocialNetworkId implements Serializable {
    private User user;
    ESocialNetwork socialNetwork;

    public SocialNetworkId() {
    }

    public SocialNetworkId(User user, ESocialNetwork socialNetwork) {
        this.user = user;
        this.socialNetwork = socialNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialNetworkId that = (SocialNetworkId) o;
        return Objects.equals(user, that.user) && socialNetwork == that.socialNetwork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, socialNetwork);
    }
}
