package com.close.close.security;

import com.close.close.user.Role;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * AuthController is the controller for authentication logic,
 * such as sign in, sign out, register, etc.
 */
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

    /**
     * Signs in a user, given their credentials
     * For example, this could be a body to authenticate a user:
     * {
     *     "username":"enzoFernandez5",
     *     "password":"secretPassword",
     * }
     * @param request an object with the user's credentials
     * @return Response entity with a 200 status code if the credentials are correct.
     */
    @PostMapping(AUTHENTICATE)
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Registers a user in the system, given their information.
     * For example, this could be a body to register a user:
     * {
     *     "profileName":"Enzo",
     *     "username":"enzoFernandez5",
     *     "age":22,
     *     "phone":"624 423 123",
     *     "password":"secretPassword",
     * }
     *
     * @param newUser User object with the new user information
     * @return If the user is created properly, it returns a 201 (created) status code, with
     * a response with token
     */
    @PostMapping(REGISTER)
    public ResponseEntity register(@RequestBody User newUser){
        newUser.setRole(Role.USER);
        AuthenticationResponse response = service.register(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
