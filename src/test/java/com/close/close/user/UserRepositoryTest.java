package com.close.close.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;
    @Test
    void itShouldCheckIfUserFindByPhone() {
        // given

        String phoneNumber = "+34 663796810";
        User user = new User(
                1L,"AndreesBenito","AndresBenito",19,"1234",Role.USER, phoneNumber,true
        );
        underTest.save(user);
        // when

        // then
    }

    @Test
    void itShouldSelectFindByUsername() {
    }
}