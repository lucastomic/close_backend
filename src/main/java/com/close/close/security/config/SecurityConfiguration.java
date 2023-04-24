package com.close.close.security.config;

import com.close.close.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 A configuration class for setting up the security filters and providers in a Spring application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final DaoAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     Constructs a new SecurityConfiguration object with the given authentication provider and JWT authentication filter.
     @param authenticationProvider the authentication provider to use in this configuration.
     @param jwtAuthFilter the JWT authentication filter to use in this configuration.
     */
    @Autowired
    public SecurityConfiguration(DaoAuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }
    /**
     Configures the security filter chain for the application, including CSRF protection and request authorization.
     @param http the HttpSecurity object to configure.
     @return a SecurityFilterChain object representing the configured filter chain.
     @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

