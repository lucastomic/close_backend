package com.close.close.space_partitioning;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class QuadTreeBranch<T extends Location> {
    private final QuadTree<T> tree;
    private QuadTreeBranch<T> parentBranch;
    private long level;
    private final Rectangle area;
    private final ArrayList<T> locations;
    private final ArrayList<QuadTreeBranch<T>> childBranches;


    public QuadTreeBranch(@NotNull QuadTreeBranch<T> parentBranch, Rectangle area) {
        this(parentBranch.tree, area);
        this.parentBranch = parentBranch;
        this.level = parentBranch.getLevel() + 1;
    }

    public QuadTreeBranch(QuadTree<T> tree, Rectangle area) {
        this.tree = tree;
        this.parentBranch = null;
        this.level = 0;
        this.area = area;
        childBranches = new ArrayList<>();
        locations = new ArrayList<>();
    }


    public long getLevel() {
        return level;
    }

    public void getLocations(@NotNull ArrayList<T> result) {
        result.addAll(locations);
        for (QuadTreeBranch<T> child : childBranches)
            child.getLocations(result);
    }
    public Rectangle getArea() { return area; }

    public ArrayList<QuadTreeBranch<T>> getChildBranches() {
        return childBranches;
    }

    public boolean isBranched() {
        return childBranches.size() > 0;
    }

    public boolean insert(@NotNull T location) {
        if (!area.includes(location.getPosition())) return false;

        if (isBranched()) findAndInsert(location);
        else if (locations.size() <= tree.MAX_CAPACITY) {
            locations.add(location);
            if (locations.size() == tree.MAX_CAPACITY) branch();
        }

        return true;
    }

    public void show() {
        System.out.print("   ");
        for (int i = 0; i <= level - 1; i++) System.out.print("|  ");
        System.out.println("|--- Level: " + level + " | Position: " + area.getPosition() + " | Radius: " + area.getDimensions());

        for (Location location : locations) {
            System.out.print("   ");
            for (int i = 0; i <= level; i++) System.out.print("|  ");
            System.out.println(location);
        }

        for (QuadTreeBranch<T> children : childBranches) children.show();
    }


    private void findAndInsert(T location) {
        Iterator<QuadTreeBranch<T>> iterator = childBranches.iterator();
        while(iterator.hasNext() && !iterator.next().insert(location));
    }

    private void branch() {
        double newRadius = area.getDimensions().getX() / 2;

        for (int i = 1; i >= -1; i -= 2)
            for (int j = 1; j >= -1; j -= 2)
                childBranches.add(
                        new QuadTreeBranch<T>(
                                this,
                                new Rectangle(
                                        new Vector2D (
                                                area.getPosition().getX() + i * newRadius,
                                                area.getPosition().getY() + j * newRadius
                                        ),
                                        new Vector2D(
                                                newRadius,
                                                newRadius
                                        )
                                )
                        )
                );

        for (T location : locations) findAndInsert(location);
        locations.clear();
    }

    public Long query(Rectangle queryArea, ArrayList<T> results, ArrayList<T> potentialResults) {
        Long comparisons = 1L;

        if (area.isContainedBy(queryArea)) getLocations(results);
        else if (area.intersectsWith(queryArea)) {
            potentialResults.addAll(locations);
            for (QuadTreeBranch<T> child : childBranches)
                comparisons += child.query(queryArea, results, potentialResults);
        }

        return comparisons;
    }
}