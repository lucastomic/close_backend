package com.close.close.interest;
import com.close.close.user.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;


@Entity
public class Interest {
    @Id
    private String name;
    @ManyToMany(mappedBy = "interests")
    private Set<User> usersInterested;
}
