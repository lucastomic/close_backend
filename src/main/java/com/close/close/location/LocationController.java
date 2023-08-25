package com.close.close.location;

import com.close.close.user.User;
import com.close.close.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationController {

    public static final String USER_LOCATION = "/location";

    private final LocationService LOCATION_SERVICE;
    private final UserService USER_SERVICE;

    @Autowired
    public LocationController(LocationService locationService, UserService userService) {
        LOCATION_SERVICE = locationService;
        USER_SERVICE = userService;
    }

    /**
     * Sends the location of the authenticated user to the server.
     * @param location The location to be sent
     * @return ResponseEntity with no content if successful
     */
    @MessageMapping(USER_LOCATION)
    public void sendLocation(@RequestBody Location location, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        // As the Authentication object is set up when the user establishes the WebSocket connection,
        // the object won't be updated until they make another WebSocket connection. Even if the user changes their
        // info (adds an interest, removes a social network, etc.). So, for real time info updating, the
        // Authentication object must be updated from the repository, for example, with:

        // user = USER_SERVICE.findById(user.getId());
        LOCATION_SERVICE.sendUserLocation(user, location);
    }
}
