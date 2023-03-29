package com.close.close.duck;

import com.close.close.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * DuckRepository is the duck's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
    public interface DuckRepository extends JpaRepository<Duck, DuckId> {

    /**
     * getDucksReceived gets the duck which the user specified by parameter have received
     * @param id Long with the User's ID
     * @return List of users who have sent a Duck to the user
     */
    @Query("SELECT d.sender FROM Duck d WHERE d.receiver.id = :id")
    public List<User> getDucksReceived(@Param("id") Long id);
}
