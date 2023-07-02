package com.close.close.location;

import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationController {

    public static final String POST_USER_LOCATION = "/users/location";

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
    @PostMapping(POST_USER_LOCATION)
    public ResponseEntity<?> sendLocation(@RequestBody Location location) {
        User user = AUTH_SERVICE.getAuthenticated();
        LOCATION_SERVICE.sendUserLocation(user, location);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(location);
    }
}
