package com.close.close.space_partitioning;

import org.jetbrains.annotations.NotNull;

public class Rectangle {
    private Vector2D dimensions;
    private Vector2D position;


    public Rectangle(Vector2D position, Vector2D dimensions) {
        this.dimensions = dimensions;
        this.position = position;
    }


    public Vector2D getDimensions() {
        return dimensions;
    }
    public void setDimensions(Vector2D dimensions) { this.dimensions = dimensions; }

    public Vector2D getPosition() {
        return position;
    }
    public void setPosition(Vector2D position) { this.position = position; }


    public boolean includes(@NotNull Vector2D point) {
        return     point.getX() >= position.getX() - dimensions.getX()
                && point.getX() <= position.getX() + dimensions.getX()
                && point.getY() >= position.getY() - dimensions.getY()
                && point.getY() <= position.getY() + dimensions.getY();
    }

    public boolean intersectsWith(@NotNull Rectangle other) {
        return     other.getPosition().getX() - other.getDimensions().getX() <= position.getX() + dimensions.getX()
                && other.getPosition().getX() + other.getDimensions().getX() >= position.getX() - dimensions.getX()
                && other.getPosition().getY() - other.getDimensions().getY() <= position.getY() + dimensions.getY()
                && other.getPosition().getY() + other.getDimensions().getY() >= position.getY() - dimensions.getY();
    }

    public boolean isContainedBy(@NotNull Rectangle other) {
        return     other.getPosition().getX() - other.getDimensions().getX() <= position.getX() - dimensions.getX()
                && other.getPosition().getX() + other.getDimensions().getX() >= position.getX() + dimensions.getX()
                && other.getPosition().getY() - other.getDimensions().getY() <= position.getY() - dimensions.getY()
                && other.getPosition().getY() + other.getDimensions().getY() >= position.getY() + dimensions.getY();
    }
}
