package com.close.close.location;

import com.close.close.location.distanceCalculator.DistanceCalculator;
import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.geometry.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;
import com.close.close.user.User;
import com.close.close.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LocationService uses a Quadtree Space Partitioning algorithm to reduce the number of iterations when searching for close users.
 * For more information see https://en.wikipedia.org/wiki/Quadtree
 */
@Service
@EnableScheduling
public class LocationService {
    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;
    private final Map<Long, UserLocation> USER_LOCATION_BUFFER;
    //Quadtree from Space Partitioning algorithm
    private final QuadTree<UserLocation> USER_QUADTREE;
    private final double queryRadius = 1;
    private final double MAX_DISTANCE_METERS = 20;


    public LocationService() {
        USER_LOCATION_BUFFER = new HashMap<>();
        USER_QUADTREE = new QuadTree<>(
                MAX_BRANCH_LEVEL,
                MAX_BRANCH_CAPACITY,
                new Rectangle(
                        Vector2D.ZERO,
                        new Vector2D(WIDTH, WIDTH)
                )
        );
    }

    public List<User> closeUsers(User user) {
        validateUserIsInBuffer(user);
        UserLocation userLocation = USER_LOCATION_BUFFER.get(user.getId());
        QueryResult<UserLocation> locationQueryResult =  USER_QUADTREE.search(userLocation.getPosition(), queryRadius);
        List<User> result= filterCloseUsers(locationQueryResult.POTENTIAL_RESULTS, userLocation.getLocation());
        result.addAll(filterCloseUsers(locationQueryResult.RESULTS,userLocation.getLocation()));
        result.remove(user);
        return result;
    }

    public void sendUserLocation(User user, Location location) {
        USER_LOCATION_BUFFER.put(user.getId(), new UserLocation(user, location));
    }

    @Scheduled(fixedRate = 1000)
    public void updateUserQuadTree() {
        USER_QUADTREE.reset();
        dumpBufferIntoQuadTree();
    }

    private void validateUserIsInBuffer(User user){
        if (!USER_LOCATION_BUFFER.containsKey(user.getId()))
            throw new UserNotFoundException(user.getUsername());
    }

    private List<User> filterCloseUsers(List<UserLocation> allLocations, Location userLocation){
        ArrayList<User> response = new ArrayList<>();
        for (UserLocation location : allLocations){
            if (DistanceCalculator.calculateDistance(userLocation, location.getLocation())< MAX_DISTANCE_METERS){
                response.add(location.getUser());
            }
        }
        return response;
    }

    private void dumpBufferIntoQuadTree(){
        // The array must be copied before iterating because the buffer can be modified in the iterations, what would throw a concurrency exception
        List<UserLocation> locationsCopy =  new ArrayList<>(USER_LOCATION_BUFFER.values());
        for (UserLocation userLocation :locationsCopy)
            insertOrRemoveLocation(userLocation);
    }

    private void insertOrRemoveLocation(UserLocation location){
        if (!location.hasExpired()) USER_QUADTREE.insert(location);
        else USER_LOCATION_BUFFER.remove(location.getUser().getId());
    }
}
