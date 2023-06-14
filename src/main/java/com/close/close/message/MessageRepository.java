package com.close.close.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.receiver.id = :receiverId AND m.sender.id = :senderId")
    List<Message> findMessage(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

}
