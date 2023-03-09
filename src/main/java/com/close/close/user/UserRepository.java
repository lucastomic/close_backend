package com.close.close.user;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is the user's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface UserRepository extends JpaRepository<User, Long>{

}
