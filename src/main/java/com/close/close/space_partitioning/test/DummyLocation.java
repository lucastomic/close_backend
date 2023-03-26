package com.close.close.space_partitioning.test;

import com.close.close.space_partitioning.Location;
import com.close.close.space_partitioning.Vector2D;

public class DummyLocation extends Location {
    private final Dummy dummy;


    public DummyLocation(Dummy dummy, Vector2D position)
    {
        super(position);
        this.dummy = dummy;
    }


    public Dummy getDummy() {
        return dummy;
    }
}
