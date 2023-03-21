package com.close.close.space_partitioning.test;

import com.close.close.space_partitioning.Location;
import com.close.close.space_partitioning.V2;

public class DummyLocation extends Location {
    private final Dummy dummy;


    public DummyLocation(Dummy dummy, V2 position)
    {
        super(position);
        this.dummy = dummy;
    }


    public Dummy getDummy() {
        return dummy;
    }
}
