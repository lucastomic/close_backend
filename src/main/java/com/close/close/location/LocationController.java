package com.close.close.location;

import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class LocationController {

    public static final String USER_LOCATION = "/location";

    private final LocationService LOCATION_SERVICE;
    private final AuthenticationService AUTH_SERVICE;

    @Autowired
    public LocationController(LocationService locationService, AuthenticationService authenticationService) {
        LOCATION_SERVICE = locationService;
        AUTH_SERVICE = authenticationService;
    }

    /**
     * Sends the location of the authenticated user to the server.
     * @param location The location to be sent
     * @return ResponseEntity with no content if successful
     */
    @MessageMapping(USER_LOCATION)
    public void sendLocation(@RequestBody Location location, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        LOCATION_SERVICE.sendUserLocation(user, location);
    }
}
