package com.close.close.chat.persistence;

import com.close.close.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    @Query("SELECT DISTINCT c FROM Chat c INNER JOIN FETCH c.users u LEFT JOIN FETCH c.messages m WHERE c.id IN (SELECT c2.id FROM Chat c2 INNER JOIN c2.users u2 WHERE u2.id IN :usersIDs GROUP BY c2 HAVING COUNT(DISTINCT u2) = :numberOfUsers)")
    Optional<Chat> getChat(@Param("usersIDs") List<Long> usersIDs, int numberOfUsers);

}