package com.close.close.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User(
                1L,"testUsername","profileName",19,
                "1234",Role.USER,"123456789",false);
        entityManager.persist(testUser);
        entityManager.flush();
    }

    @Test
    public void findByPhone_userExists_returnsUser() {
        Optional<User> foundUser = userRepository.findByPhone(testUser.getPhone());

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getPhone()).isEqualTo(testUser.getPhone());
    }

    @Test
    public void findByPhone_userNotExists_returnsEmptyOptional() {
        Optional<User> foundUser = userRepository.findByPhone("987654321");

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    public void findByUsername_userExists_returnsUser() {
        Optional<User> foundUser = userRepository.findByUsername(testUser.getUsername());

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getUsername()).isEqualTo(testUser.getUsername());
    }

    @Test
    public void findByUsername_userNotExists_returnsEmptyOptional() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistentUsername");

        assertThat(foundUser.isPresent()).isFalse();
    }
}

