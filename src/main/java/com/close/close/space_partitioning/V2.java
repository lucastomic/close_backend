package com.close.close.space_partitioning;

public class V2 {
    private double x;
    private double y;


    public V2(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public static V2 sum(V2 a, V2 b) {
        return  new V2(a.x + b.x, a.y + b.y);
    }

    public boolean intersects(V2 other) {
        return
                this.x < other.x && this.y > other.x
                || this.y > other.y && this.x < other.x;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
