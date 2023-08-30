package com.close.close.chat.persistence;

import com.close.close.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    // I couldn't find a SQL sentence which doesn't retrieve a Chat without all the messages duplicated.
    // It would be much better to find a method getChat which returns a Chat with the messages without duplicity.
    // Meanwhile, this is the "patch". Having two methods: one which retrieves the Chat with duplicity and one for override
    // these duplicated messages. This is the link with the StackOverflow post which explains the porblem:
    // https://stackoverflow.com/questions/76731778/jparepository-returns-duplicated-child-entities
    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id IN :usersIDs GROUP BY c HAVING COUNT(DISTINCT u) = :numberOfUsers")
    Optional<Chat> getChat(@Param("usersIDs") List<Long> usersIDs, int numberOfUsers);
}