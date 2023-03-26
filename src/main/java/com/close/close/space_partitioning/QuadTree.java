package com.close.close.space_partitioning;

import java.util.ArrayList;

/**
 * Data structure for spacial partition, which reduces the search time for nearby Locations.
 * Huge amounts of inserts can be heavy on performance, so continuous loadings are not recommended.
 * @param <T> The type of Location this tree will handle
 */
public class QuadTree<T extends Location> {
    /**
    *  W = width of a quadTree
    *  i = level of the branch
    *  As we know:
    *      W[i+1] = W[i]/2   - Recursively
    *
    *  Let W[0] = 10:
    *      W[1] = W[0]/2 = 10/2
    *      W[2] = W[1]/2 = 10/2/2 = 10/4
    *      W[3] = W[2]/2 = 10/2/2/2 = 10/8
    *      W[4] = W[3]/2 = 10/2/2/2/2 = 10/16
    *
    *   As we can see, each iteration multiplies the division by 2
    *   Which translates to:
    *       n = final i
    *       W[n] = W[0]/2^n
    *
    *   Now if we want to find a specific value for n that makes W[n] given W[0] and W[n]:
    *   2^n = W[0]/W[n]
    *       n = log2(W[0]/W[n])
    *
    *   And we know that: log2(x) = log(x)/log(2)
    *   So: n = log(W[0]/W[n]) / log(2)
     **/
    public static long findMaxLevel(double areaWidth, double desiredFinalWidth) {
        return (long) (Math.log(areaWidth/desiredFinalWidth) / Math.log(2));
    }

    /** Maximum branch level. */
    public final long MAX_LEVEL;
    /** Maximum capacity of Locations in a branch. */
    public final long MAX_CAPACITY;
    private final Rectangle AREA;
    /** Root branch of a tree */
    public QuadTreeBranch<T> root;


    /**
     * Constructs a new QuadTree
     * @param maxLevel Maximum branch level of the tree.
     * @param maxCapacity Maximum capacity of Locations in a branch of the tree.
     * @param area Area that will be handled by this tree
     */
    public QuadTree(long maxLevel, long maxCapacity, Rectangle area) {
        MAX_LEVEL = maxLevel;
        MAX_CAPACITY = maxCapacity;
        AREA = area;
        root = new QuadTreeBranch<T>(this, area);
    }

    /**
     * @return All Locations inside this tree.
     * If none is found, will return an empty array.
     */
    public ArrayList<T> getLocations() {
        ArrayList<T> result = new ArrayList<>();
        root.getLocations(result);
        return result;
    }

    /**
     * Inserts a Location inside a tree.
     * @param location The Location to be inserted.
     * @return False if the Location could not be added.
     */
    public boolean insert(T location) {
        return root.insert(location);
    }

    public boolean remove(T location) {
        return root.remove(location);
    }

    public void reset() {
        root = new QuadTreeBranch<T>(this, AREA);
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
        Long comparisons = root.query(queryArea, results, potentialResults);
        return new QueryResult<T>(results, potentialResults, comparisons);
    }

    public void show()
    {
        System.out.println(" --- Quad Tree");
        root.show();
    }
}
