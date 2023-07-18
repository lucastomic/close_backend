package com.close.close.message;

import com.close.close.chat.Chat;
import com.close.close.user.User;
import jakarta.persistence.*;

@Entity
public class Message {
    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name="senderId", nullable=false)
    private User sender;
    @Column(name="value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "chat")
    private Chat chat;

    public Message() {
    }

    public Message(User sender, Chat chat, String value) {
        this.sender = sender;
        this.value = value;
        this.chat = chat;
    }

    public Long getId(){
        return id;
    }
}
