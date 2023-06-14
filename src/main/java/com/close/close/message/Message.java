package com.close.close.message;

import com.close.close.user.User;
import jakarta.persistence.*;

@Entity
public class Message {
    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name="senderId", nullable=false)
    private User sender;
    @ManyToOne()
    @JoinColumn(name="receiverId", nullable=false)
    private User receiver;

    @Column(name="value")
    private String value;

    public Message(User receiver, User sender, String value) {
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
