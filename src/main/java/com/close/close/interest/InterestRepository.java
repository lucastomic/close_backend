package com.close.close.interest;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * InterestRepository is the interest's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface InterestRepository extends JpaRepository<Interest, String> {
}
