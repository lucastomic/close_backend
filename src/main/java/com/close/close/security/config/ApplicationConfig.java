package com.close.close.security.config;

import com.close.close.user.UserNotFoundException;
import com.close.close.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
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

    /**
     * Defines our custom authenticationProvider as a DaoAuthenticationProvider.
     * @return The AuthenticationProvider object
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines our custom authentication manager
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines our password encoder. It's used the BCryptPasswordEncoder.
     * @return PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



