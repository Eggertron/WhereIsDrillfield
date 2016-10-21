package com.example.eggertron.whereisdrillfield;

import android.location.Location;

/**
 * Created by eggertron on 10/20/16.
 */
public class DrillfieldCompass {

    double angle;
    Location currentLocation;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    Location drillfieldLocation;

    public DrillfieldCompass() {
        drillfieldLocation = new Location("");
        drillfieldLocation.setLatitude(37.227429);
        drillfieldLocation.setLongitude(-80.422230);
    }

    public double getDrillfieldAngle() {
        if (currentLocation != null) {
            double mapAngle = currentLocation.bearingTo(drillfieldLocation); //calculates bearing angle
            if (mapAngle < 0 ) {
                mapAngle += 360;
            }
            return mapAngle - angle; //subtract bearing from phone angle
        }
        else {
            return 0;
        }
    }

    public double getDrillfieldDistance() {
        if (currentLocation != null) {
            return currentLocation.distanceTo(drillfieldLocation); //in meters.
        }
        else {
            return 0;
        }
    }
}
