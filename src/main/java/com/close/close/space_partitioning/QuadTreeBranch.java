package com.close.close.space_partitioning;

import java.util.ArrayList;
import java.util.Iterator;

public class QuadTreeBranch<T extends Location> {
    private final QuadTree<T> tree;
    private QuadTreeBranch<T> parentBranch;
    private long level;
    private final V2 position;
    private final V2 box;
    private final ArrayList<QuadTreeBranch<T>> childBranches;
    private final ArrayList<T> locations;


    public QuadTreeBranch(QuadTreeBranch<T> parentBranch, V2 position, V2 box) {
        this(parentBranch.tree, position, box);
        this.parentBranch = parentBranch;
        this.level = parentBranch.getLevel() + 1;
    }

    public QuadTreeBranch(QuadTree<T> tree, V2 position, V2 box) {
        this.tree = tree;
        this.parentBranch = null;
        this.level = 0;
        this.position = position;
        this.box = box;
        childBranches = new ArrayList<>();
        locations = new ArrayList<>();
    }


    public long getLevel() {
        return level;
    }

    public void getLocations(ArrayList<T> result) {
        result.addAll(locations);
        for (QuadTreeBranch<T> child : childBranches)
            child.getLocations(result);
    }

    public V2 getPosition() {
        return position;
    }

    public V2 getBox() {
        return box;
    }

    public ArrayList<QuadTreeBranch<T>> getChild() {
        return childBranches;
    }

    public boolean isBranched() {
        return childBranches.size() > 0;
    }

    public boolean includes(V2 position) {
        return     position.getX() >= this.position.getX() - this.box.getX()
                && position.getX() <= this.position.getX() + this.box.getX()
                && position.getY() >= this.position.getY() - this.box.getY()
                && position.getY() <= this.position.getY() + this.box.getY();
    }

    public boolean isContainedBy(V2 position, V2 box) {
        return     position.getX() - box.getX() <= this.position.getX() - this.box.getX()
                && position.getX() + box.getX() >= this.position.getX() + this.box.getX()
                && position.getY() - box.getY() <= this.position.getY() - this.box.getY()
                && position.getY() + box.getY() >= this.position.getY() + this.box.getY();
    }

    public boolean intersectsWith(V2 position, V2 box) {
        return     position.getX() - box.getX() <= this.position.getX() + this.box.getX()
                && position.getX() + box.getX() >= this.position.getX() - this.box.getX()
                && position.getY() - box.getY() <= this.position.getY() + this.box.getY()
                && position.getY() + box.getY() >= this.position.getY() - this.box.getY();
    }


    public boolean insert(T location) {
        if (!includes(location.getPosition())) return false;

        if (isBranched()) findAndInsert(location);
        else if (locations.size() <= tree.MAX_CAPACITY) {
            locations.add(location);
            if (locations.size() == tree.MAX_CAPACITY) branch();
        }

        return true;
    }

    private void findAndInsert(T location) {
        Iterator<QuadTreeBranch<T>> iterator = childBranches.iterator();
        while(iterator.hasNext() && !iterator.next().insert(location));
    }

    private void branch() {
        V2 newBox = new V2(box.getX()/2, box.getY()/2);

        for (int i = 1; i >= -1; i -= 2)
            for (int j = 1; j >= -1; j -= 2)
                childBranches.add(
                        new QuadTreeBranch<T>(
                                this,
                                new V2 (
                                        position.getX() + i * newBox.getX(),
                                        position.getY() + j * newBox.getY()
                                ),
                                newBox
                        )
                );

        for (T location : locations) findAndInsert(location);
        locations.clear();
    }

    public Long query(V2 position, V2 box, ArrayList<T> results, ArrayList<T> potentialResults) {
        Long comparisons = 1L;

        if (isContainedBy(position, box)) getLocations(results);
        else if (intersectsWith(position, box)) {
            potentialResults.addAll(locations);
            for (QuadTreeBranch<T> child : childBranches)
                comparisons += child.query(position, box, results, potentialResults);
        }

        return comparisons;
    }

    public void show() {
        System.out.print("   ");
        for (int i = 0; i <= level - 1; i++) System.out.print("|  ");
        System.out.println("|--- Level: " + level + " | Position: " + position + " | Box: " + box);

        for (Location location : locations) {
            System.out.print("   ");
            for (int i = 0; i <= level; i++) System.out.print("|  ");
            System.out.println(location);
        }

        for (QuadTreeBranch<T> children : childBranches) children.show();
    }
}