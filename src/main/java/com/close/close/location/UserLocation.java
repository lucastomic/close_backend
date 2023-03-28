package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;

public class UserLocation implements IPosition {

    private Long userID;
    private Long latitude;
    private Long longitude;


    public UserLocation(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }


    @Override
    public Vector2D getPosition() {
        return new Vector2D(latitude, longitude);
    }
}
