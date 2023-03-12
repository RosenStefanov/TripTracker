package com.trip_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.trip_tracker.util.MyDataBaseHelper;
import com.trip_tracker.util.SaveNotificationService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class TripActivity extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.0");
    private TextView startLocationTextView;
    private TextView finishLocationTextView;
    private TextView distanceTextView;
    private TextView dateTextView;
    private Button costCalcBtn;
    private Button weatherBtn;
    private String startLocation;
    private String startLat;
    private String startLon;
    private String finishLocation;
    private String finishLat;
    private String finishLon;
    private String distanceText;
    private double distance;
    private String date;
    private String task;
    private Button saveTripBtn;
    private String tripId;
    private boolean openFragment = false;
    private Button delBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startLocationTextView =  findViewById(R.id.start_location_text_TA);
        finishLocationTextView = findViewById(R.id.finish_location_text_TA);
        distanceTextView =  findViewById(R.id.trip_distance_text_TA);
        dateTextView =  findViewById(R.id.trip_date_text_TA);
        saveTripBtn =  findViewById(R.id.btn_save_trip_TA);
        costCalcBtn =  findViewById(R.id.btn_calc_cost_TA);
        weatherBtn =  findViewById(R.id.btn_show_weather_TA);
        delBtn =  findViewById(R.id.btn_del_trip_TA);
        startLocation = getIntent().getStringExtra("startLocation");
        finishLocation = getIntent().getStringExtra("finishLocation");
        distanceText = getIntent().getStringExtra("distance");
        date = getIntent().getStringExtra("date");
        task = getIntent().getStringExtra("task");
        startLat = getIntent().getStringExtra("startLat");
        startLon = getIntent().getStringExtra("startLon");
        finishLat = getIntent().getStringExtra("finishLat");
        finishLon = getIntent().getStringExtra("finishLon");
        detailsForSaveOrView();
        startLocationTextView.setText(getString(R.string.start_location_text_TA, startLocation));
        finishLocationTextView.setText(getString(R.string.finish_location_text_TA, finishLocation));
        distanceTextView.setText(getString(R.string.trip_distance_text_TA, String.valueOf(distance)));
        dateTextView.setText(getString(R.string.date_of_trip_TA, date));

        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        saveTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrip(database);
            }
        });
        costCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcCost();
            }
        });
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weather();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delTrip();
            }
        });
    }

    public void convertDistanceTextToDouble(){
        distance = Double.parseDouble(distanceText);
        distance = distance / 1000;
        BigDecimal bd = new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP);
        distance = bd.doubleValue();
    }

    public void delTrip(){
        MyDataBaseHelper myDataBaseHelper = MyDataBaseHelper.instanceOfDatabase(this);
        myDataBaseHelper.delTrip(tripId);
        Intent intent = new Intent(this, SavedTripsActivity.class);
        startActivity(intent);
    }

    public void detailsForSaveOrView(){
        if (Objects.equals(task, "save")){
            saveTripBtn.setVisibility(View.VISIBLE);
            convertDistanceTextToDouble();
        }
        else{
            distance = Double.parseDouble(distanceText);
            costCalcBtn.setVisibility(View.VISIBLE);
            weatherBtn.setVisibility(View.VISIBLE);
            tripId = getIntent().getStringExtra("id");
            delBtn.setVisibility(View.VISIBLE);
        }
    }

    public void weather(){
        costCalcBtn.setVisibility(View.GONE);
        weatherBtn.setVisibility(View.GONE);
        delBtn.setVisibility(View.GONE);
        openFragment = true;
        Bundle bundle = new Bundle();
        bundle.putString("startLocation", startLocation);
        bundle.putString("finishLocation", finishLocation);
        bundle.putString("startLat", startLat);
        bundle.putString("startLon", startLon);
        bundle.putString("finishLat", finishLat);
        bundle.putString("finishLon", finishLon);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentCalCostView, WeatherFragment.class, bundle)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }



    public void calcCost(){
        costCalcBtn.setVisibility(View.GONE);
        weatherBtn.setVisibility(View.GONE);
        delBtn.setVisibility(View.GONE);
        openFragment = true;
        Bundle bundle = new Bundle();
        bundle.putDouble("distance", distance);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentCalCostView, CalcCostFragment.class, bundle)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (Objects.equals(task, "details")){
            if(openFragment){
                openFragment = false;
                costCalcBtn.setVisibility(View.VISIBLE);
                weatherBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
                super.onBackPressed();
            }
            else {
                Intent intent = new Intent(this, SavedTripsActivity.class);
                startActivity(intent);
            }
        }
        else{
            super.onBackPressed();
        }
    }

    public void saveTrip(SQLiteDatabase database){
        Thread save_thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        ContentValues values = new ContentValues();
                        values.put(MyDataBaseHelper.COLUMN_START_LOCATION, startLocation);
                        values.put(MyDataBaseHelper.COLUMN_FINISH_LOCATION, finishLocation);
                        values.put(MyDataBaseHelper.COLUMN_TRIP_DATE, date);
                        values.put(MyDataBaseHelper.COLUMN_DISTANCE, distance);
                        values.put(MyDataBaseHelper.COLUMN_START_LOCATION_LAT, startLat);
                        values.put(MyDataBaseHelper.COLUMN_START_LOCATION_LON, startLon);
                        values.put(MyDataBaseHelper.COLUMN_FINISH_LOCATION_LAT, finishLat);
                        values.put(MyDataBaseHelper.COLUMN_FINISH_LOCATION_LON, finishLon);
                        database.insert(MyDataBaseHelper.TABLE_NAME, null, values);

                    }
                }
        );
        save_thread.start();
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.TA_save_toast_text), Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(TripActivity.this, SaveNotificationService.class);
        intent.putExtra("startLoc", startLocation);
        intent.putExtra("finishLoc", finishLocation);
        intent.putExtra("date", date);
        startService(intent);
        onBackPressed();
    }
}