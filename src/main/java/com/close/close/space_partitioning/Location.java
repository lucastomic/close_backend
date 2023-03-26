package com.close.close.space_partitioning;

public abstract class Location {
    private Vector2D position;


    public Location (Vector2D position) {
        this.position = position;
    }


    public Vector2D getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return "Position: " + position;
    }
}
