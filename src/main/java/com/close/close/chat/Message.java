package com.close.close.chat;

import com.close.close.chat.Chat;
import com.close.close.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Message {
    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name="senderId", nullable=false)
    private User sender;
    @Column(name="value")
    private String value;
    @ManyToOne()
    @JoinColumn(name = "chat")
    private Chat chat;

    public Message() {
    }

    public Message(User sender, Chat chat, String value) {
        this.sender = sender;
        this.value = value;
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(this.id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
