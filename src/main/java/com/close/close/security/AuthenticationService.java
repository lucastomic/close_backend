package com.close.close.security;

import com.close.close.user.User;
import com.close.close.user.UserRepository;
import com.close.close.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    public AuthenticationService(UserService userService, JwtService jwtService, UserRepository repository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }
    private final UserService userService;

    private final JwtService jwtService;
    private final UserRepository repository;
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User newUser){
        User user = userService.create(newUser);
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }
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
