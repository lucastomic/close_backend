package com.close.close.location;

import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.location.space_partitioning.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class LocationService<T extends Location> {
    private final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private final Map<Long, T> LOCATION_BUFFER;
    private final QuadTree<T> QUADTREE;

    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;


    @Autowired
    public LocationService() {
        LOCATION_BUFFER = new HashMap<>();
        QUADTREE = new QuadTree<>(
                MAX_BRANCH_LEVEL,
                MAX_BRANCH_CAPACITY,
                new Rectangle(
                        Vector2D.ZERO,
                        new Vector2D(WIDTH, WIDTH)
                )
        );
    }


    public QueryResult<T> searchLocations(double latitude, double longitude, double radius) {
        QueryResult<T> result = QUADTREE.search(new Vector2D(latitude, longitude), radius);
        return result;
    }

    public QueryResult<T> closeLocations(Long locationId, double radius) {
        if (!LOCATION_BUFFER.containsKey(locationId))
            throw new IllegalArgumentException("userId not found");
        T location = LOCATION_BUFFER.get(locationId);
        return QUADTREE.search(location.getPosition(), radius);
    }

    public List<T> findAllLocations() {
        return QUADTREE.getLocations();
    }

    public T findLocation(Long locationId) {
        if (!LOCATION_BUFFER.containsKey(locationId))
            throw new IllegalArgumentException("locationId not found");
        return LOCATION_BUFFER.get(locationId);
    }

    /**
    public void sendUserLocation(T location) {
        synchronized (LOCATION_BUFFER) {
            LOCATION_BUFFER.put(location.getId, new UserLocation(userId, location));
        }
    }
     **/


    @Scheduled(fixedRate = 2000)
    public void updateQuadtree() {
        QUADTREE.reset();
        synchronized (LOCATION_BUFFER) {
            for (T location : LOCATION_BUFFER.values())
                QUADTREE.insert(location);
        }
        LOGGER.info("Quadtree Updated");
    }
}
