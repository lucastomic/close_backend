package com.close.close.space_partitioning;

public abstract class Location {
    private static long currentId = 0;
    private final long id;
    private Vector2D position;


    public Location (Vector2D position) {
        this.id = currentId + 1;
        currentId++;
        this.position = position;
    }


    public Vector2D getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return String.valueOf(id) + " | Position: " + position;
    }
}
