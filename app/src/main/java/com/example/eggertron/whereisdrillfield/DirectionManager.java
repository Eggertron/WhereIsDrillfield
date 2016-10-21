package com.example.eggertron.whereisdrillfield;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by eggertron on 10/20/16.
 */
public class DirectionManager implements SensorEventListener { //for sensor data

    MainActivity mainActivity;
    SensorManager sensorManager;
    Sensor accSensor;
    Sensor magneticSensor;

    float[] accData;
    float[] magData;

    public DirectionManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        sensorManager = (SensorManager)mainActivity.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register() {

        if (accSensor != null) {
            sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (magneticSensor != null) {
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //when new data is available.

        if (event.sensor.equals(accSensor)) {
            accData = event.values;
            Log.d("test",""+event.sensor.toString());
        }
        if (event.sensor.equals(magneticSensor)) {
            magData = event.values;
        }
        if (accData != null && magData != null) {
            //use geometry?
            float R[] = new float[9];
            float I[] = new float[9];
            //calcualtions
            if (SensorManager.getRotationMatrix(R, I, accData, magData)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                double angle = orientation[0] * 360 / (2 * Math.PI); //z-axis in radians
                if (angle < 0) {
                    angle += 360; //always make it positive.
                }
                mainActivity.updateSensor(angle);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
