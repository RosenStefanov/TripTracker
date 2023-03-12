package com.trip_tracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button create_trip;
    private Button saved_trips;
    private boolean grantedLocationPermission = false;
    private boolean grantedNotificationPermission = false;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6002;
    private static final int PERMISSIONS_REQUEST_ACCESS_NOTIFICATIONS = 7002;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        create_trip =  findViewById(R.id.create_trip_btn);
        saved_trips =  findViewById(R.id.saved_trips_btn);
        if (grantedLocationPermission == false){
            getLocationPermission();
        }
        if(grantedNotificationPermission == false){
            getNotificationPermission();
        }
        create_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(grantedLocationPermission){
                    if(grantedNotificationPermission){
                        openCreateTrip();
                    }else{
                        getNotificationPermission();
                    }
                }else{
                    getLocationPermission();
                }

            }
        });
        saved_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSavedTrips();
            }
        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            grantedLocationPermission = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getNotificationPermission(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            grantedNotificationPermission = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSIONS_REQUEST_ACCESS_NOTIFICATIONS);
        }
    }

    public void openSavedTrips(){
        Intent intent = new Intent(this, SavedTripsActivity.class);
        startActivity(intent);
    }

    public void openCreateTrip(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Intent intent = new Intent(this, CreateTripActivity.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.no_GPS_message);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    R.string.ok_string,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}