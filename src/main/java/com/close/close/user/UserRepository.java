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
     * getUserFromPhone gets the user given a number phone
     * @param phone phone of the user to look for
     * @return user with that phone
     */
    @Query("SELECT u " +
             "FROM User u " +
            "WHERE u.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);
}
