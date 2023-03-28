package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;

public abstract class Location implements IPosition {
    private double latitude;
    private double longitude;


    public Location (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Vector2D getPosition() {
        return new Vector2D(latitude, longitude);
    }

    public abstract Long getId();


    @Override
    public String toString() {
        return "Latitude: " + latitude + " | Longitude: " + longitude;
    }
}
