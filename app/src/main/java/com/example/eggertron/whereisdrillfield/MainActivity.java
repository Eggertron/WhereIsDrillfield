package com.example.eggertron.whereisdrillfield;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ImageView arrow;
    TextView distance;
    GPSManager gpsManager;
    DirectionManager directionManager;
    DrillfieldCompass drillfieldCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrow = (ImageView)findViewById(R.id.arrow);
        distance = (TextView)findViewById(R.id.distance);
        gpsManager = new GPSManager(this);
        directionManager = new DirectionManager(this);
        drillfieldCompass = new DrillfieldCompass();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Here, thisActivity is the current activity
        gpsManager.register();
        directionManager.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsManager.unregister();
        directionManager.unregister();
    }

    public void updateUI (float degree, int dist) {
        arrow.setRotation(degree);
        distance.setText("Distance to Drillfield: " + dist + " meters");
    }
    public void updateGPSLocation(Location location) {
        drillfieldCompass.setCurrentLocation(location);
    }

    public void updateSensor(double angle) {
        drillfieldCompass.setAngle(angle);
        updateUI((float)drillfieldCompass.getDrillfieldAngle(), (int)drillfieldCompass.getDrillfieldDistance());
    }
}
