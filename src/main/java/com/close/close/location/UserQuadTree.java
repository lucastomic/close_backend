package com.close.close.location;

import com.close.close.location.space_partitioning.QuadTree;
import com.close.close.location.space_partitioning.Rectangle;
import com.close.close.location.space_partitioning.Vector2D;

import java.util.HashMap;

public class UserQuadTree extends QuadTree<UserLocation> {
    private static UserQuadTree instance;
    private static final double QUAD_MAX_WIDTH = 10000;
    private static final double QUAD_MIN_WIDTH = 10;
    private static final Long MAX_CAPACITY = 4L;
    private static final Long MAX_LEVEL = QuadTree.findMaxLevel(QUAD_MAX_WIDTH, QUAD_MIN_WIDTH);

    public HashMap<Long, UserLocation> updateRequestBuffer;


    public static UserQuadTree getInstance() {
        if (instance == null) instance = new UserQuadTree(
                MAX_LEVEL,
                MAX_CAPACITY,
                new Rectangle(
                        new Vector2D(0, 0),
                        new Vector2D(QUAD_MAX_WIDTH, QUAD_MAX_WIDTH)
                )
        );
        return instance;
    }


    /**
     * Constructs a new QuadTree
     *
     * @param maxLevel    Maximum branch level of the tree.
     * @param maxCapacity Maximum capacity of Locations in a branch of the tree.
     * @param area        Area that will be handled by this tree
     */
    private UserQuadTree(long maxLevel, long maxCapacity, Rectangle area) {
        super(maxLevel, maxCapacity, area);
    }


    public void requestUpdate(UserLocation userLocation) {
        if (!updateRequestBuffer.containsKey(userLocation.getUserID()))
            updateRequestBuffer.put(userLocation.getUserID(), userLocation);
        else {
            updateRequestBuffer.remove(userLocation.getUserID());
            updateRequestBuffer.put(userLocation.getUserID(), userLocation);
        }
    }

    public void update() {
        reset();
        for (UserLocation userLocation : updateRequestBuffer.values())
            insert(userLocation);
    }
}
