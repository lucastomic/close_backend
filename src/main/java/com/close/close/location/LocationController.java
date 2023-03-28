package com.close.close.location;

import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
public class LocationController {

    public static final String CMD_LOCATIONS = "/locations";
    public static final String CMD_QUERY = CMD_LOCATIONS + "/query";

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
    private static final Long MAX_BRANCH_LEVEL = 9L;
    private static final Long MAX_BRANCH_CAPACITY = 4L;
    private static final Long WIDTH = 100000L;

    private final Map<Long, UserLocation> userUpdateLocationBuffer;
    private final QuadTree<UserLocation> quadTree;


    public LocationController() {
        userUpdateLocationBuffer = new HashMap<>();
        quadTree = new QuadTree<>(
                MAX_BRANCH_LEVEL,
                MAX_BRANCH_CAPACITY,
                new Rectangle(
                        Vector2D.ZERO,
                        new Vector2D(WIDTH, WIDTH)
                )
        );
    }


    @GetMapping(CMD_LOCATIONS)
    public ResponseEntity<List<UserLocation>> getLocations() {
        List<UserLocation> locations = quadTree.getLocations();
        return ResponseEntity.ok(locations);
    }

    @PostMapping(CMD_LOCATIONS)
    public ResponseEntity<?> setLocation(@RequestBody UserLocation userLocation) {
        synchronized (userUpdateLocationBuffer) {
            userUpdateLocationBuffer.put(userLocation.getUserID(), userLocation);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(CMD_QUERY)
    public ResponseEntity<?> queryLocation(@RequestBody Rectangle searchArea) {
        ArrayList<UserLocation> results = quadTree.query(searchArea).RESULTS;
        return ResponseEntity.ok(results);
    }

    @Scheduled(fixedRate = 2000)
    public void updateQuadtree() {
        quadTree.reset();
        synchronized (userUpdateLocationBuffer) {
            for (UserLocation userLocation : userUpdateLocationBuffer.values())
                quadTree.insert(userLocation);
            userUpdateLocationBuffer.clear();
        }
        LOGGER.info("Quadtree Updated");
    }
}
