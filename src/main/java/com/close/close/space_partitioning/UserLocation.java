package com.close.close.space_partitioning;

public class UserLocation extends Location {
    private final Long USER_ID;


    public UserLocation(Vector2D position, Long userId) {
        super(position);
        this.USER_ID = userId;
    }


    public Long getUserId() { return USER_ID; }
}
