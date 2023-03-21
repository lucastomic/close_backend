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
     * @param box Area that will be handled by this tree
     */
    public QuadTree(long maxLevel, long maxCapacity, V2 box) {
        MAX_LEVEL = maxLevel;
        MAX_CAPACITY = maxCapacity;
        ROOT = new QuadTreeBranch<T>(this, new V2(0, 0), box);
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

    public QueryResult<T> query(V2 origin, double rangeRadius) {
        float SQRT_2 = 1.41421356237f;
        double innerSquareSide = rangeRadius * SQRT_2;
        V2 innerSquare = new V2(innerSquareSide/2, innerSquareSide/2);
        return query(origin, innerSquare);
    }

    public QueryResult<T> query(V2 origin, V2 box) {
        ArrayList<T> results = new ArrayList<>();
        ArrayList<T> potentialResults = new ArrayList<>();
        Long comparisons = ROOT.query(origin, box, results, potentialResults);
        return new QueryResult<T>(results, potentialResults, comparisons);
    }

    public void show()
    {
        System.out.println(" --- Quad Tree");
        ROOT.show();
    }
}
