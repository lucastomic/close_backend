package com.close.close.location;

import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.geometry.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;
import com.close.close.user.User;
import com.close.close.user.UserNotFoundException;
import com.close.close.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@EnableScheduling
public class LocationService {
    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);
    private final Map<Long, UserLocation> USER_LOCATION_BUFFER;
    private final QuadTree<UserLocation> USER_QUADTREE;
    private final double radius = 10;

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

    public QueryResult<UserLocation> closeUsers(User user) {
        validateUserIsInBuffer(user);
        UserLocation userLocation = USER_LOCATION_BUFFER.get(user.getId());
        return USER_QUADTREE.search(userLocation.getPosition(), radius);
    }

    public void sendUserLocation(User user, Location location) {
        synchronized (USER_LOCATION_BUFFER) {
            USER_LOCATION_BUFFER.put(user.getId(), new UserLocation(user, location));
        }
    }

    @Scheduled(fixedRate = 2000)
    public void updateUserQuadTree() {
        USER_QUADTREE.reset();
        synchronized (USER_LOCATION_BUFFER) {
            for (UserLocation userLocation : USER_LOCATION_BUFFER.values())
                insertOrRemoveLocation(userLocation);
        }
        LOGGER.info("Quadtree Updated");
    }

    private void validateUserIsInBuffer(User user){
        if (!USER_LOCATION_BUFFER.containsKey(user.getId()))
            throw new UserNotFoundException(user.getUsername());
    }

    private void insertOrRemoveLocation(UserLocation location){
        if(!location.hasExpired())USER_QUADTREE.insert(location);
        else USER_LOCATION_BUFFER.remove(location.getUser().getId());
    }
}
