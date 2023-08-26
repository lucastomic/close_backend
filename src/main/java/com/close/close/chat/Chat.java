package com.close.close.chat;

import com.close.close.user.User;
import jakarta.persistence.*;
import java.util.*;
import java.util.function.Function;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "chat"),
            inverseJoinColumns = @JoinColumn(name = "user")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(Set<User> users) {
        this.users = users;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void forEachMember(Function<User, Void> func) {
        for (User user : this.users) {
            func.apply(user);
        }
    }

    public Long getId() {
        return id;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
