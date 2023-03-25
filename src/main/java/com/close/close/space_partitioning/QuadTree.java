package com.close.close.space_partitioning;

import java.util.ArrayList;

/**
 * Data structure for spacial partition, which reduces the search time for nearby Locations.
 * Huge amounts of inserts can be heavy on performance, so continuous loadings are not recommended.
 * @param <T> The type of Location this tree will handle
 */
public class QuadTree<T extends Location> {
    /** Maximum branch level. */
    public final long MAX_LEVEL;
    /** Maximum capacity of Locations in a branch. */
    public final long MAX_CAPACITY;
    /** Root branch of a tree */
    public final QuadTreeBranch<T> ROOT;

    /**
     * Constructs a new QuadTree
     * @param maxLevel Maximum branch level of the tree.
     * @param maxCapacity Maximum capacity of Locations in a branch of the tree.
     * @param area Area that will be handled by this tree
     */
    public QuadTree(long maxLevel, long maxCapacity, Rectangle area) {
        MAX_LEVEL = maxLevel;
        MAX_CAPACITY = maxCapacity;
        ROOT = new QuadTreeBranch<T>(this, area);
    }

    /**
     * @return All Locations inside this tree.
     * If none is found, will return an empty array.
     */
    public ArrayList<T> getLocations() {
        ArrayList<T> result = new ArrayList<>();
        ROOT.getLocations(result);
        return result;
    }

    /**
     * Inserts a Location inside a tree.
     * @param location The Location to be inserted.
     * @return False if the Location could not be added.
     */
    public boolean insert(T location) {
        return ROOT.insert(location);
    }

    public QueryResult<T> query(Vector2D origin, double queryRadius) {
        float SQRT_2 = 1.41421356237f;
        double innerSquareRadius = queryRadius * SQRT_2 / 2;
        Rectangle queryArea = new Rectangle(origin, new Vector2D(innerSquareRadius, innerSquareRadius));
        return query(queryArea);
    }

    public QueryResult<T> query(Rectangle queryArea) {
        ArrayList<T> results = new ArrayList<>();
        ArrayList<T> potentialResults = new ArrayList<>();
        Long comparisons = ROOT.query(queryArea, results, potentialResults);
        return new QueryResult<T>(results, potentialResults, comparisons);
    }

    public void show()
    {
        System.out.println(" --- Quad Tree");
        ROOT.show();
    }
}
