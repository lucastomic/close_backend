package com.close.close.duck;

import com.close.close.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * DuckRepository is the duck's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
    public interface DuckRepository extends JpaRepository<Duck, DuckId> {

    /**
     * getDucksReceived gets the duck which the user specified by parameter have received
     * @param userId Long with the User's ID
     * @return List of users who have sent a Duck to the user
     */
    @Query("SELECT d FROM Duck d WHERE d.receiver.id = :userId")
    List<Duck> findDucksReceived(@Param("userId") Long userId);

    @Query("SELECT d FROM Duck d WHERE d.sender.id = :userId")
    List<Duck> findDucksSent(@Param("userId") Long userId);

    @Query("SELECT d " +
             "FROM Duck d " +
            "WHERE d.sender.id = :senderId " +
              "AND d.receiver.id = :receiverId")
    Optional<Duck> findBySenderReceiver(@Param("senderId") Long reclaimerId,
                                        @Param("receiverId") Long receivedId);
}
