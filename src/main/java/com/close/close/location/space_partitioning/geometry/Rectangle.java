package com.close.close.location.space_partitioning.geometry;

import com.close.close.location.space_partitioning.Vector2D;
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


    public boolean includes(@NotNull Vector2D point) {
        return     point.getX() >= position.getX() - dimensions.getX()
                && point.getX() <= position.getX() + dimensions.getX()
                && point.getY() >= position.getY() - dimensions.getY()
                && point.getY() <= position.getY() + dimensions.getY();
    }

    public Point[] vertices(){
        Point[] vertices = new Point[4];
        vertices[0] = new Point(this.position.getX() - this.dimensions.getX(), this.position.getY()-this.dimensions.getY());
        vertices[1] = new Point(this.position.getX() + this.dimensions.getX(), this.position.getY()-this.dimensions.getY());
        vertices[2] = new Point(this.position.getX() - this.dimensions.getX(), this.position.getY()+this.dimensions.getY());
        vertices[3] = new Point(this.position.getX() + this.dimensions.getX(), this.position.getY()+this.dimensions.getY());
        return vertices;
    }
}
