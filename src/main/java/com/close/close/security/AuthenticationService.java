package com.close.close.security;

import com.close.close.user.User;
import com.close.close.user.UserRepository;
import com.close.close.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * AuthenticationService manages the operations wihch implies
 * authentication logic
 */
@Service
public class AuthenticationService {
    @Autowired
    public AuthenticationService(
            UserService userService,
            JwtService jwtService,
            UserRepository repository,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }
    private final UserService userService;

    private final JwtService jwtService;
    private final UserRepository repository;
    private AuthenticationManager authenticationManager;

    /**
     * Creates a new user and generates a JWT (Json Web Token) given his information
     * @param newUser User to create
     * @return AuthenticationResponse object with the JWT created
     */
    public AuthenticationResponse register(User newUser){
        User user = userService.create(newUser);
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }

    /**
     * Authenticates a user given his credentials and returns a JWT if the authentication is successful
     * @param request AuthenticationRequest object with the user credentials
     * @return If the authentication is successful it returns an AuthenticationResponse object
     * with the JWT generated, otherwise it returns an error
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);

    }
}
