package com.close.close.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests((requests) -> requests
            .anyRequest().authenticated()
        ).formLogin(form ->form
            .loginPage("/login")
            .permitAll()
        )
        .httpBasic(withDefaults());
        return http.build();
    }
}
