package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;
import com.close.close.user.User;

public record UserAndLocation(User user, Location location) implements IPosition {
    @Override
    public Vector2D getPosition() {
        return location.getPosition();
    }
}
