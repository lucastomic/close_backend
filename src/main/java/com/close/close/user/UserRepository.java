package com.close.close.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * UserRepository is the user's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * Gets the user with the username specified
     * @param username username to look for
     * @return User with that username
     */

    public Optional<User> findByUsername(@Param("username") String username);

}
