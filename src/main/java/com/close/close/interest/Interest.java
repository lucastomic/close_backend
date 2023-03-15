package com.close.close.interest;
import com.close.close.user.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;


/**
 * Interest are the user's interests
 */
@Entity
public class Interest {
    /**
     * Interest's name (for example "futbol", "videogames", etc).
     * It's the entity id
     */
    @Id
    private String name;
    /**
     * userInterested is a collection with the users who
     * have this interest.
     */
    @ManyToMany(mappedBy = "interests")
    private Set<User> usersInterested;
}
