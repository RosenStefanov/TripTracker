package com.trip_tracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.trip_tracker.util.MyDataBaseHelper;
import com.trip_tracker.util.Trip;
import com.trip_tracker.util.TripAdapter;

public class SavedTripsActivity extends AppCompatActivity {

    private ListView tripsList;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_trips);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tripsList =  findViewById(R.id.trips_list_STA);
        loadFromDBToMemory();
        setTripAdapter();
        tripsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Trip selectedTrip = (Trip) tripsList.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), TripActivity.class);
                intent.putExtra("date", selectedTrip.getDate()).putExtra("distance", String.valueOf(selectedTrip.getDistance()))
                        .putExtra("startLocation", selectedTrip.getStartLocation()).putExtra("finishLocation", selectedTrip.getFinishLocation())
                        .putExtra("task", "details").putExtra("id", String.valueOf(selectedTrip.getId())).putExtra("startLat", selectedTrip.getStartLat())
                        .putExtra("startLon", selectedTrip.getStartLon()).putExtra("finishLat", selectedTrip.getFinishLat()).putExtra("finishLon", selectedTrip.getFinishLon());;
                Trip.tripArrayList.clear();
                startActivity(intent);
            }
        });
    }
    private void loadFromDBToMemory()
    {
        MyDataBaseHelper myDataBaseHelper = MyDataBaseHelper.instanceOfDatabase(this);
        myDataBaseHelper.populateTripListArray();
    }

    public void setTripAdapter(){
        TripAdapter tripAdapter = new TripAdapter(getApplicationContext(), Trip.tripArrayList);
        tripsList.setAdapter(tripAdapter);
    }

    @Override
    public void onBackPressed() {
        Trip.tripArrayList.clear();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}