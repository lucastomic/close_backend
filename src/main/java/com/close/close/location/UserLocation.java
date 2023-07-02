package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;
import com.close.close.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class UserLocation implements IPosition {
    private final User user;
    private Location location;
    private final Date expirationTime;
    private final int expirationTimeInSeconds = 5;

    public UserLocation(@NotNull User user, @NotNull Location location) {
        this.user = user;
        this.location = location;
        this.expirationTime = getExpirationTime();
    }

    @JsonIgnore
    @Override
    public Vector2D getPosition() {
        return location.getPosition();
    }

    public boolean hasExpired(){
        boolean res = expirationTime.before(new Date());
        return res;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Date getExpirationTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, expirationTimeInSeconds);
        return calendar.getTime();
    }
}