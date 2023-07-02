package com.close.close.location.space_partitioning.geometry;

import com.close.close.location.space_partitioning.Vector2D;
import org.jetbrains.annotations.NotNull;

public class Circle {
    private double radius;
    private Point center;

    public Circle(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    public boolean intersectsWith(@NotNull Rectangle other) {
        for(Point vertex : other.vertices()){
            if(isContained(vertex))return true;
        }
        return other.includes(this.center.getVector());
    }

    public boolean contains(@NotNull Rectangle other) {
        for(Point vertex : other.vertices()){
            if(!isContained(vertex))return false;
        }
        return true;
    }

    private boolean isContained(Point point){
        return point.getDistance(center) < radius;
    }
}
