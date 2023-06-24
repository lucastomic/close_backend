package com.close.close.location;

import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.Rectangle;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private final Map<Long, UserLocation> USER_LOCATION_BUFFER;
    private final QuadTree<UserLocation> USER_QUADTREE;
    private final UserRepository USER_REPOSITORY;

    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;


    @Autowired
    public LocationService(UserRepository userRepository) {
        USER_REPOSITORY = userRepository;
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

    public QueryResult<UserLocation> searchUsers(double latitude, double longitude, double radius) {
        return USER_QUADTREE.search(new Vector2D(latitude, longitude), radius);
    }

    public QueryResult<UserAndLocation> closeUsers(Long userId, double radius) {
        if (!USER_LOCATION_BUFFER.containsKey(userId))
            throw new UserNotFoundException(userId);

        UserLocation userLocation = USER_LOCATION_BUFFER.get(userId);
        QueryResult<UserLocation> queryResult = USER_QUADTREE.search(userLocation.location().getPosition(), radius);

        List<UserAndLocation> usersAndLocationsResults = new ArrayList<>();
        List<UserAndLocation> usersAndLocationsPotentialResults = new ArrayList<>();

        for (UserLocation uloc : queryResult.RESULTS)
            USER_REPOSITORY.findById(uloc.userId())
                    .ifPresent(user -> usersAndLocationsResults
                            .add(new UserAndLocation(user, uloc.location())));

        for (UserLocation uloc : queryResult.POTENTIAL_RESULTS)
            USER_REPOSITORY.findById(uloc.userId())
                    .ifPresent(user -> usersAndLocationsPotentialResults
                            .add(new UserAndLocation(user, uloc.location())));

        return new QueryResult<UserAndLocation> (
                usersAndLocationsResults,
                usersAndLocationsPotentialResults,
                queryResult.COMPARISONS
        );
    }

    public List<UserLocation> findAllUserLocations() {
        return USER_QUADTREE.getLocations();
    }

    public UserLocation findUserLocation(Long userId) {
        if (!USER_LOCATION_BUFFER.containsKey(userId))
            throw new IllegalArgumentException("userId not found");
        return USER_LOCATION_BUFFER.get(userId);
    }

    public void sendUserLocation(Long userId, Location location) {
        synchronized (USER_LOCATION_BUFFER) {
            USER_LOCATION_BUFFER.put(userId, new UserLocation(userId, location));
        }
    }


    @Scheduled(fixedRate = 2000)
    public void updateUserQuadTree() {
        USER_QUADTREE.reset();
        synchronized (USER_LOCATION_BUFFER) {
            for (UserLocation userLocation : USER_LOCATION_BUFFER.values())
                USER_QUADTREE.insert(userLocation);
        }
        LOGGER.info("Quadtree Updated");
    }
}
