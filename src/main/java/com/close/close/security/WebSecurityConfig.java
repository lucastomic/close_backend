package com.close.close.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 This class configures the Spring Security for web application.
 */
@EnableWebSecurity
@Configuration
class WebSecurityConfig{
    /**
     * This method returns the security filter chain for the http requests.
     @param http HttpSecurity object to configure the security
     @return SecurityFilterChain object for the http requests
     @throws Exception If there is an exception while configuring the security
     */
    //TODO: Correct the routes
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(antMatcher(HttpMethod.POST,"/users")).permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Creates a Spring Bean definition for a password encoder, used to encode and compare passwords.
     * @return An instance of BCryptPasswordEncode, a widely used implementation of PasswordEncoder Interface.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
