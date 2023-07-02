package com.close.close.location.space_partitioning;

import com.close.close.location.UserLocation;
import com.close.close.location.space_partitioning.geometry.Circle;
import com.close.close.location.space_partitioning.geometry.Rectangle;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuadTreeBranch<T extends IPosition> {
    private final QuadTree<T> tree;
    private QuadTreeBranch<T> parentBranch;
    private long level;
    private final Rectangle area;
    private final ArrayList<T> positions;
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
        positions = new ArrayList<>();
    }


    public QuadTree<T> getTree() { return tree; }
    public QuadTreeBranch<T> getParentBranch() { return parentBranch; }
    public long getLevel() {
        return level;
    }
    public Rectangle getArea() { return area; }
    public List<T> getPositions() {
        List<T> response = new ArrayList<>(positions);
        for (QuadTreeBranch<T> child : childBranches)
            response.addAll(child.getPositions());
        return response;
    }
    public ArrayList<QuadTreeBranch<T>> getChildBranches() {
        return childBranches;
    }
    public boolean isBranched() {
        return childBranches.size() > 0;
    }

    public boolean insert(@NotNull T position) {
        if (!area.includes(position.getPosition())) return false;

        if (isBranched()) findAndInsert(position);
        else if (positions.size() <= tree.MAX_CAPACITY) {
            positions.add(position);
            if (positions.size() == tree.MAX_CAPACITY) branch();
        }

        return true;
    }
    private void findAndInsert(T position) {
        Iterator<QuadTreeBranch<T>> iterator = childBranches.iterator();
        while(iterator.hasNext() && !iterator.next().insert(position));
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

        for (T position : positions) findAndInsert(position);
        positions.clear();
    }

    public boolean remove(@NotNull T position) {
        if (!area.includes(position.getPosition())) return false;

        if (isBranched()) findAndRemove(position);
        else {
            assert positions.contains(position) : "Position: " + position + " in " + this + " is missing";
            positions.remove(position);
        }

        return true;
    }
    private void findAndRemove(T position) {
        Iterator<QuadTreeBranch<T>> iterator = childBranches.iterator();
        while(iterator.hasNext() && !iterator.next().remove(position));
    }

    public QueryResult<T> query(Circle queryArea) {
        QueryResult<T> response = new  QueryResult<>();
        if (queryArea.contains(area)) response.RESULTS.addAll(getPositions());
        else if (queryArea.intersectsWith(area)) {
            if(!isBranched())response.POTENTIAL_RESULTS.addAll(positions);
            for (QuadTreeBranch<T> child : childBranches){
                response.add(child.query(queryArea));
            }
        }
        return response;
    }

    public void show() {
        System.out.print("   ");
        for (int i = 0; i <= level - 1; i++) System.out.print("|  ");
        System.out.println("|--- Level: " + level + " | Position: " + area.getPosition() + " | Radius: "
                + area.getDimensions());

        for (T position : positions) {
            System.out.print("   ");
            for (int i = 0; i <= level; i++) System.out.print("|  ");
            System.out.println(position);
        }

        for (QuadTreeBranch<T> children : childBranches) children.show();
    }

}