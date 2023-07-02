package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents a location on the Earth, defined by its latitude and longitude coordinates.
 * It implements the IPosition interface, which requires it to have a getPosition() method
 * that returns a Vector2D object representing the location in 2D space.
 */
public class Location implements IPosition {
    private double latitude;
    private double longitude;

    /**
     * Constructor for Location, which takes in a latitude and longitude as doubles.
     * @param latitude the latitude coordinate of the location
     * @param longitude the longitude coordinate of the location
     */
    public Location (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter for the latitude coordinate of the location.
     * @return the latitude coordinate of the location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter for the longitude coordinate of the location.
     * @return the longitude coordinate of the location
     */
    public double getLongitude() {
        return longitude;
    }

    public double getLatitudeInRadians(){
        return Math.toRadians(latitude);
    }
    public double getLongitudeInRadians(){
        return Math.toRadians(longitude);
    }

    /**
     * Returns the location as a Vector2D object.
     * @return the location as a Vector2D object
     */
    @JsonIgnore
    public Vector2D getPosition() {
        return new Vector2D(latitude, longitude);
    }

    /**
     * Returns a string representation of the location, including its latitude and longitude coordinates.
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "Latitude: " + latitude + " | Longitude: " + longitude;
    }
}
