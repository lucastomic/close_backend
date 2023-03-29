package com.close.close.location;

import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.location.space_partitioning.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@EnableScheduling
public class LocationController {

    //Endpoint URIs
    public static final String GET_SEARCH_USERS   = "/users/search/{latitude},{longitude},{radius}";
    public static final String GET_CLOSE_USERS    = "/users/{userId}/close/{radius}";
    public static final String GET_USER_LOCATIONS = "/users/locations";
    public static final String GET_USER_LOCATION  = "/users/{userId}/location";
    public static final String POST_USER_LOCATION = "/users/{userId}/location";

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;

    private final Map<Long, UserLocation> userLocationBuffer;
    private final QuadTree<UserLocation> userQuadTree;


    public LocationController() {
        userLocationBuffer = new HashMap<>();
        userQuadTree = new QuadTree<>(
                MAX_BRANCH_LEVEL,
                MAX_BRANCH_CAPACITY,
                new Rectangle(
                        Vector2D.ZERO,
                        new Vector2D(WIDTH, WIDTH)
                )
        );
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
        QueryResult<UserLocation> result = userQuadTree.search(new Vector2D(latitude, longitude), radius);
        return ResponseEntity.ok(result);
    }

    /**
     * Searches for users within a certain radius from a given user id
     * @param userId The id of the user to search from
     * @param radius The radius within which to search for users
     * @return ResponseEntity containing a QueryResult with the results of the search
     */
    @GetMapping(GET_CLOSE_USERS)
    public ResponseEntity<QueryResult<UserLocation>> closeUsers(@PathVariable Long userId,
                                                                @PathVariable double radius) {
        // UserLocation could instead be a column of User in the database...
        // This would also allow us to clear the buffer each time quadtree is updated
        if (!userLocationBuffer.containsKey(userId)) return ResponseEntity.badRequest().build();
        UserLocation userLocation = userLocationBuffer.get(userId);
        QueryResult<UserLocation> result = userQuadTree.search(userLocation.location().getPosition(), radius);
        return ResponseEntity.ok(result);
    }

    /**
     * Gets the locations of all users in the system. User Locations are not stored in database.
     * @return ResponseEntity containing a List of UserLocation objects representing the locations of all users
     */
    @GetMapping(GET_USER_LOCATIONS)
    public ResponseEntity<List<UserLocation>> getLocations() {
        List<UserLocation> locations = userQuadTree.getLocations();
        return ResponseEntity.ok(locations);
    }

    /**
     * Retrieves the location of a user given their user ID
     * @param userId The ID of the user whose location will be retrieved
     * @return ResponseEntity containing the user's location if found or a not found response if the user ID is not
     * present in the buffer
     */
    @GetMapping(GET_USER_LOCATION)
    public ResponseEntity<UserLocation> getUserLocation(@PathVariable Long userId) {
        if (!userLocationBuffer.containsKey(userId)) return ResponseEntity.notFound().build();
        UserLocation userLocation = userLocationBuffer.get(userId);
        return ResponseEntity.ok(userLocation);
    }

    /**
     * Sends the location of a user with the given userId to the server.
     * @param userId The ID of the user whose location will be sent
     * @param location The location to be sent
     * @return ResponseEntity with no content if successful
     */
    @PostMapping(POST_USER_LOCATION)
    public ResponseEntity<?> sendLocation(@PathVariable Long userId, @RequestBody Location location) {
        synchronized (userLocationBuffer) {
            //By using put, an already existing entry of userId in the Map will have its value replaced by the new one.
            userLocationBuffer.put(userId, new UserLocation(userId, location));
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Automatically updates the QuadTree each 2 seconds by building a new QuadTree from the ground up.
     * Every user that sent its location and did not close the application will continue to have its location inserted
     * in the new QuadTree.
     */
    @Scheduled(fixedRate = 2000)
    public void updateQuadtree() {
        userQuadTree.reset();
        synchronized (userLocationBuffer) {
            for (UserLocation userLocation : userLocationBuffer.values())
                userQuadTree.insert(userLocation);
        }
        LOGGER.info("Quadtree Updated");
    }
}
