package com.close.close.location.space_partitioning.geometry;

import com.close.close.location.space_partitioning.Vector2D;

public class Point {
    private final double x;
    private final double y;

    public Point(Vector2D vector){
        this.x = vector.getX();
        this.y = vector.getY();
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Point other){
        return Math.sqrt(Math.pow(other.x - this.x, 2) +Math.pow(other.y - this.y, 2));
    }

    public Vector2D getVector(){
        return new Vector2D(this.x, this.y);
    }
}
