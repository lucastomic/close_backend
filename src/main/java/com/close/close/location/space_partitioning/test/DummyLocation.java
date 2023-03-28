package com.close.close.location.space_partitioning.test;

import com.close.close.location.Location;
import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;

public class DummyLocation implements IPosition {
    private final Dummy dummy;
    private Vector2D position;


    public DummyLocation(Dummy dummy, Vector2D position)
    {
        this.dummy = dummy;
        this.position = position;
    }


    public Dummy getDummy() {
        return dummy;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }
}
