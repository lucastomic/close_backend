package com.close.close.security;

import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public static final String REGISTER = "/register";
    public static final String AUTHENTICATE = "/authenticate";

    private final AuthenticationService service;

    @Autowired
    public AuthController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping(AUTHENTICATE)
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(REGISTER)
    public ResponseEntity register(@RequestBody User newUser){
        AuthenticationResponse response = service.register(newUser);
        return ResponseEntity.ok(response);
    }
}
