package com.close.close.location.space_partitioning;

import org.jetbrains.annotations.NotNull;

public class Vector2D {
    public static Vector2D ZERO = new Vector2D(0, 0);
    public static Vector2D UP = new Vector2D(0, 1);
    public static Vector2D DOWN = new Vector2D(0, -1);
    public static Vector2D LEFT = new Vector2D(-1, 0);
    public static Vector2D RIGHT = new Vector2D(1, 0);


    public static Vector2D sum(@NotNull Vector2D a, @NotNull Vector2D b) {
        return  new Vector2D(a.x + b.x, a.y + b.y);
    }

    public static Vector2D sub(@NotNull Vector2D a, @NotNull Vector2D b) {
        return  new Vector2D(a.x - b.x, a.y - b.y);
    }


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


    public boolean intersects(@NotNull Vector2D other) {
        return
                this.x < other.x && this.y > other.x
                || this.y > other.y && this.x < other.x;
    }

    public Vector2D scale(double value) {
        return new Vector2D(this.x * value, this.y * value);
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
