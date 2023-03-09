package com.close.close.user;

import com.close.close.interest.Interest;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User{
            private @Id @GeneratedValue Long id;
            @Column(nullable=false)
            private String name;
            private int age;
            @Column(nullable = false, columnDefinition = "integer default 0")
            private int ducksNumber;
            @ManyToMany
            @JoinTable(
                    name = "sendsDuck",
                    joinColumns = @JoinColumn(name = "sender"),
                    inverseJoinColumns = @JoinColumn(name = "receiver")
            )
            private Set<User> duckedUsers;
            @ManyToMany(mappedBy = "duckerUsers")
            private Set<User> recivesDuckFrom;
            @Column(nullable = false)
            private String phone;
            @Column(nullable = false, columnDefinition = "boolean default false")
            private boolean phoneIsVerified;
            @ElementCollection
            private Set<String> photos;

            @ManyToMany
            @JoinTable(
                name = "hasInterest",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "interest_id")
            )
            private Set<Interest> interests;
}