package com.close.close.location;

import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class LocationController {

    //Endpoint URIs
    public static final String GET_SEARCH_USERS   = "/users/search/{latitude},{longitude},{radius}";
    public static final String GET_CLOSE_USERS    = "/users/{userId}/close/{radius}";
    public static final String GET_USER_LOCATIONS = "/users/locations";
    public static final String GET_USER_LOCATION  = "/users/{userId}/location";
    public static final String POST_USER_LOCATION = "/users/{userId}/location";

    private final LocationService LOCATION_SERVICE;


    @Autowired
    public LocationController(LocationService locationService) {
        LOCATION_SERVICE = locationService;
    }


    /**
     * Searches for users within a certain radius from a given latitude and longitude
     * @param latitude Search latitude center point
     * @param longitude Search longitude center point
     * @param radius Search radius
     * @return ResponseEntity containing a QueryResult with the results of the search
     */
    @GetMapping(GET_SEARCH_USERS)
    public ResponseEntity<QueryResult<UserLocation>> searchUsers(@PathVariable double latitude,
                                                                 @PathVariable double longitude,
                                                                 @PathVariable double radius) {
        QueryResult<UserLocation> result = LOCATION_SERVICE.searchUsers(latitude, longitude, radius);
        return ResponseEntity.ok(result);
    }

    /**
     * Searches for users within a certain radius from a given user id
     * @param userId The id of the user to search from
     * @param radius The radius within which to search for users
     * @return ResponseEntity containing a QueryResult with the results of the search
     */
    @GetMapping(GET_CLOSE_USERS)
    public ResponseEntity<?> closeUsers(@PathVariable Long userId,
                                        @PathVariable double radius) {
        ResponseEntity responseEntity;
        // UserLocation could instead be a column of User in the database...
        // This would also allow us to clear the buffer each time quadtree is updated
        try {
            QueryResult<UserAndLocation> result = LOCATION_SERVICE.closeUsers(userId, radius);
            responseEntity = ResponseEntity.ok(result);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    /**
     * Gets the locations of all users in the system. User Locations are not stored in database.
     * @return ResponseEntity containing a List of UserLocation objects representing the locations of all users
     */
    @GetMapping(GET_USER_LOCATIONS)
    public ResponseEntity<List<UserLocation>> getLocations() {
        List<UserLocation> locations = LOCATION_SERVICE.findAllUserLocations();
        return ResponseEntity.ok(locations);
    }

    /**
     * Retrieves the location of a user given their user ID
     * @param userId The ID of the user whose location will be retrieved
     * @return ResponseEntity containing the user's location if found or a not found response if the user ID is not
     * present in the buffer
     */
    @GetMapping(GET_USER_LOCATION)
    public ResponseEntity<?> getUserLocation(@PathVariable Long userId) {
        ResponseEntity responseEntity;
        try {
            UserLocation userLocation = LOCATION_SERVICE.findUserLocation(userId);
            responseEntity = ResponseEntity.ok(userLocation);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    /**
     * Sends the location of a user with the given userId to the server.
     * @param userId The ID of the user whose location will be sent
     * @param location The location to be sent
     * @return ResponseEntity with no content if successful
     */
    @PostMapping(POST_USER_LOCATION)
    public ResponseEntity<?> sendLocation(@PathVariable Long userId, @RequestBody Location location) {
        LOCATION_SERVICE.sendUserLocation(userId, location);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(location);
    }
}
