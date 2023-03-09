package com.close.close.interest;
import com.close.close.user.*;
import jakarta.persistence.ManyToMany;

import java.util.Set;


public class Interest {
    @ManyToMany(mappedBy = "interests")
    private Set<User> usersInterested;
}
