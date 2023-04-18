package com.close.close.security;

import com.close.close.user.User;
import com.close.close.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * AuthenticationService manages the operations which implies
 * authentication logic
 */
@Service
public class AuthenticationService {
    @Autowired
    public AuthenticationService(
            UserService userService,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Generates a JWT (Json Web Token) given a User
     * @param user user to create the JWT from
     * @return AuthenticationResponse with the token just created
     */
    public AuthenticationResponse generateToken(User user){
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }

    /**
     * Authenticates a user given them request AuthenticationRequest object with the user credentials
     * @param user user to be authenticated
     * @return If the authentication is successful it returns an AuthenticationResponse object
     * with the JWT generated, otherwise it returns an error
     */
    public AuthenticationResponse authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);

    }

    /**
     * Gets the user currently authenticated
     */
    public User getAuthenticated(){
        User userToReturn =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.findById(userToReturn.getId());
    }

    /**
     * Gets the ID from the user currently authenticated
     */
    public Long getIdAuthenticated(){
        User userToReturn =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userToReturn.getId();
    }
}
