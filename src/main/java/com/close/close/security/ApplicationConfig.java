package com.close.close.security;

import com.close.close.user.UserNotFoundException;
import com.close.close.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**

 Configuration class for the Spring application that defines a bean for the user details service.
 */
@Configuration
public class ApplicationConfig {

    private final UserRepository repository;

    /**

     Constructor for the ApplicationConfig class.
     @param repository user repository
     */
    public ApplicationConfig(UserRepository repository) {
        this.repository = repository;
    }
    /**

     Defines a bean for the user details service.
     @return user details service
     @throws UserNotFoundException if the user cannot be found by the provided username
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
    }
}



