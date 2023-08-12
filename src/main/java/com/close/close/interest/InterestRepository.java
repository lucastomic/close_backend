package com.close.close.interest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * InterestRepository is the interest's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface InterestRepository extends JpaRepository<Interest, String> {

    @Query("SELECT i, COUNT(*) AS total_rows FROM User u JOIN u.interests i GROUP BY i ORDER BY total_rows DESC LIMIT :number")
    Set<Interest> getMostPopular(@Param("number") Long number);

    @Query("SELECT i FROM Interest i WHERE i NOT IN (:exclude)")
    List<Interest> getExcluding(Set<String> exclude, Pageable pageable);
}
