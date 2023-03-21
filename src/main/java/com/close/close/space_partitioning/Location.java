package com.close.close.space_partitioning;

public abstract class Location {
    private static long currentId = 0;
    private final long id;
    private V2 pos;


    public Location(V2 position) {
        this.id = currentId + 1;
        currentId++;
        this.pos = position;
    }


    public V2 getPosition() {
        return pos;
    }


    @Override
    public String toString() {
        return String.valueOf(id) + " | Position: " + pos;
    }
}
