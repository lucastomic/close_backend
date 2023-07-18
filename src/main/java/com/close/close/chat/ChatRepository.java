package com.close.close.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id IN :usersIDs GROUP BY c HAVING COUNT(DISTINCT u) = SIZE(:usersIDs)")
    Optional<Chat> getChat(@Param("usersIDs") List<Long> usersIDs);
}
