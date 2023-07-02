package com.close.close.location.space_partitioning;
public class Vector2D {
    public static Vector2D ZERO = new Vector2D(0, 0);


    private double x;
    private double y;


    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
