package com.close.close.interest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * InterestRepository is the interest's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface InterestRepository extends JpaRepository<Interest, String> {

    @Query("SELECT i FROM User u JOIN u.interests i WHERE u.id = :userId")
    List<Interest> findByUserId(@Param("userId") Long userId);
}
