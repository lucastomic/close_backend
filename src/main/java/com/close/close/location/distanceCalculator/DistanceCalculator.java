package com.close.close.location.distanceCalculator;

import com.close.close.location.Location;

public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371;

    public static double calculateDistance(Location location1, Location location2) {
        double radLat1 = location1.getLatitudeInRadians();
        double radLon1 = location1.getLongitudeInRadians();
        double radLat2 = location2.getLatitudeInRadians();
        double radLon2 = location2.getLongitudeInRadians();

        double latitudeDifference = radLat2 - radLat1;
        double longitudeDifference = radLon2 - radLon1;

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) *
                        Math.pow(Math.sin(longitudeDifference / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;

        return distance * 1000;
    }


}
