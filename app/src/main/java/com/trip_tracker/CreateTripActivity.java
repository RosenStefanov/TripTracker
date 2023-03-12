package com.trip_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateTripActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mMapView;
    private RelativeLayout mapContainer;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private GoogleMap mGoogleMap;
    private LatLngBounds mMapBoundary;
    private FusedLocationProviderClient mFusedLocationClient;
    private EditText mSearchText;
    private LatLng startLocation;
    private LatLng finishLocation;
    private Marker startMarker;
    private Marker finishMarker;
    private GeoApiContext mGeoApiContext = null;
    private Button createTripBtn;
    private DatePickerDialog datePickerDialog;
    private String tripDate;
    private Button buttonDate;
    private String startLocationText;
    private String finishLocationText;
    private double distance;
    private Polyline savedPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mapContainer =  findViewById(R.id.map_container);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSearchText =  findViewById(R.id.input_search_ACT);
        createTripBtn =  findViewById(R.id.create_trip_btn_CTA);
        buttonDate = findViewById(R.id.datepicker_btn);
        createTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTripBtn();
            }
        });
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mMapView =  findViewById(R.id.map);
        mMapView.onCreate(mapViewBundle);
        init();
        mMapView.getMapAsync(this);
    }

    public void CreateTripBtn(){
        if (startLocation != null && finishLocation != null){
            if (tripDate == null){
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage(R.string.CTA_no_date_text);
                builder1.setCancelable(true);
                builder1.setNegativeButton(
                        R.string.cancel_string,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            else{Intent intent = new Intent(this, TripActivity.class);
                intent.putExtra("date", tripDate).putExtra("distance", String.valueOf(distance)).putExtra("startLocation", startLocationText)
                        .putExtra("finishLocation", finishLocationText).putExtra("task", "save").putExtra("startLat", String.valueOf(startLocation.latitude))
                        .putExtra("startLon", String.valueOf(startLocation.longitude)).putExtra("finishLat", String.valueOf(finishLocation.latitude))
                        .putExtra("finishLon", String.valueOf(finishLocation.longitude));
                startActivity(intent);}
        }
        else if (startLocation == null && finishLocation != null ){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.CTA_no_start_location_text);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    R.string.cancel_string,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else if (finishLocation == null && startLocation != null){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.CTA_no_finish_location_text);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    R.string.cancel_string,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.CTA_no_finish_and_start_location_text);
            builder1.setCancelable(true);
            builder1.setNegativeButton(
                    R.string.cancel_string,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void showDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        datePickerDialog= new DatePickerDialog(CreateTripActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        buttonDate.setText("Date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        Date date = new Date(cldr.getTimeInMillis());
                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        tripDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //Log.d("TimeTime", tripDate);
                    }
                }, year, month, day);
        datePickerDialog.show();

    }

    public void init(){
        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.maps_api_key)).build();
        }
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == keyEvent.ACTION_DOWN || keyEvent.getAction() == keyEvent.KEYCODE_ENTER){
                    hideKeyboard();
                    checkIsSearchTextEmpty();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void calculateDirections(){
        //Log.d("calculateDirections:", " calculating directions.");
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);
        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(
                        startLocation.latitude,
                        startLocation.longitude
                )
        );
        directions.destination(new com.google.maps.model.LatLng(
                finishLocation.latitude,
                finishLocation.longitude
        )).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
//                Log.d("calculateDirections: routes: ",  result.routes[0].toString());
//                Log.d("calculateDirections: duration: ", result.routes[0].legs[0].duration.toString());
//                Log.d("calculateDirections: distance: ", result.routes[0].legs[0].distance.toString());
//                Log.d("calculateDirections: geocodedWayPoints: ", result.geocodedWaypoints[0].toString());
                distance = result.routes[0].legs[0].distance.inMeters;
                addPolylinesToMap(result);
            }
            @Override
            public void onFailure(Throwable e) {
                //Log.e("Failed to get directions: ", e.getMessage() );
            }
        });
    }

    public void checkIfStartAndFinishAreNone(){
        if (startLocation != null && finishLocation != null){
            calculateDirections();
        }
    }

    public void checkIsSearchTextEmpty(){
        if (mSearchText.length() > 0){
            startOrFinishLocationPrompt();
        }
    }

    public void startOrFinishLocationPrompt(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.dialog_message);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "Set as Start",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        geoLocate("Start");
                        checkIfStartAndFinishAreNone();
                    }
                });
        builder1.setPositiveButton(
                "Set as Finish",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        geoLocate("Finish");
                        checkIfStartAndFinishAreNone();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void startOrFinishLongClickPrompt(LatLng latLng){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.dialog_message);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "Set as Start",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        moveCameraOnLongClick(latLng, 14.5f, "Start");
                        try {
                            startLocationText = getPlaceFromLatLon(latLng);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        checkIfStartAndFinishAreNone();
                    }
                });

        builder1.setPositiveButton(
                "Set as Finish",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        moveCameraOnLongClick(latLng, 14.5f, "Finish");
                        try {
                            finishLocationText = getPlaceFromLatLon(latLng);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        checkIfStartAndFinishAreNone();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void geoLocate(String string){
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(CreateTripActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch(IOException e){
            Log.e("GeoLocate Error", e.getMessage());
        }
        if(list.size() > 0){
            Address address = list.get(0);
            moveCameraOnSearch(new LatLng(address.getLatitude(), address.getLongitude()), 14.5f, string);
        }
    }

    public void moveCameraOnSearch(LatLng latLng, float zoom, String title){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (Objects.equals(title, "Start")){
            if (startLocation != null) {
                startMarker.remove();
            }
            startMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title));
            startLocationText = mSearchText.getText().toString();
            startLocation = new LatLng(latLng.latitude, latLng.longitude);
        }
        else{
            if (finishLocation != null) {
                finishMarker.remove();
            }
            finishMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title));
            finishLocation = new LatLng(latLng.latitude, latLng.longitude);
            finishLocationText = mSearchText.getText().toString();
        }
    }

    public void moveCameraOnLongClick(LatLng latLng, float zoom, String title){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (Objects.equals(title, "Start")){
            if (startLocation != null) {
                startMarker.remove();
            }
            startMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title));
            startLocation = new LatLng(latLng.latitude, latLng.longitude);
        }
        else{
            if (finishLocation != null) {
                finishMarker.remove();
            }
            finishMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title));
            finishLocation = new LatLng(latLng.latitude, latLng.longitude);
        }
    }

    public void centerMapOnUser() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    double bottomBoundary = location.getLatitude() - .1;
                    double leftBoundary = location.getLongitude() - .1;
                    double topBoundary = location.getLatitude() + .1;
                    double rightBoundary = location.getLongitude() + .1;
                    mMapBoundary = new LatLngBounds(
                            new LatLng(bottomBoundary, leftBoundary),
                            new LatLng(topBoundary, rightBoundary)
                    );
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));
                }
            }
        });
    }

    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d("polylines", "run: result routes: " + result.routes.length);
                for(DirectionsRoute route: result.routes){
                    Log.d("polylines", "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());
                    List<LatLng> newDecodedPath = new ArrayList<>();
                    for(com.google.maps.model.LatLng latLng: decodedPath){
                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    if(savedPolyline != null){
                        savedPolyline.remove();
                    }

                    Polyline polyline = mGoogleMap.addPolyline(new PolylineOptions().addAll(newDecodedPath).color(R.color.teal_700));
                    savedPolyline = polyline;
                }
            }
        });
    }

    public String getPlaceFromLatLon(LatLng latLng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        return addresses.get(0).getAddressLine(0);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        map.setMyLocationEnabled(true);
        mGoogleMap = map;
        centerMapOnUser();
        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                startOrFinishLongClickPrompt(latLng);
            }
        });

    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}