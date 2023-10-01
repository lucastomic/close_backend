package com.close.close.user;

import com.close.close.apirest.dto.DTOParsingException;
import com.close.close.interest.Interest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void addInterest() {
        User user = new User(
                0L,
                "johndoe",
                "John Doe",
                "secretPassword",
                Role.USER
        );
        Interest interest = new Interest("Chess");
        user.addInterest(interest);
        Set<Interest> interests = getInterests(user);
        Assertions.assertTrue(interests.contains(interest));
    }

    private Set<Interest> getInterests(User user)  {
        try{
            Field field = User.class.getDeclaredField("interests");
            field.setAccessible(true);
            return (Set<Interest>) field.get(user);
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new DTOParsingException();
        }
    }
}
