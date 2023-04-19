package com.close.close.location;

import com.close.close.location.space_partitioning.IPosition;
import com.close.close.location.space_partitioning.Vector2D;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

/**
 * @param userId   The unique identifier for the user associated with this UserLocation
 * @param location The location of the user
 */
public record UserLocation(Long userId, Location location) implements IPosition {
    /**
     * Constructs a new UserLocation object with the given user ID and location
     *
     * @param userId   the unique identifier for the user associated with this UserLocation
     * @param location the location of the user
     */
    public UserLocation(@NotNull Long userId, @NotNull Location location) {
        this.userId = userId;
        this.location = location;
    }

    @JsonIgnore
    @Override
    public Vector2D getPosition() {
        return location.getPosition();
    }
}