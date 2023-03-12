package com.trip_tracker.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static MyDataBaseHelper myDataBaseHelper;
    private static final String DATABASE_NAME = "trip_tracker.db";
    private static final int DATABASE_VERSION = 1;

    public MyDataBaseHelper(Context context) {    super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    public final static String TABLE_NAME = "trips";
    public final static String UID = "_id";
    public final static String COLUMN_START_LOCATION = "start_location";
    public final static String COLUMN_START_LOCATION_LAT = "start_location_lat";
    public final static String COLUMN_START_LOCATION_LON = "start_location_lon";
    public final static String COLUMN_FINISH_LOCATION = "finish_location";
    public final static String COLUMN_FINISH_LOCATION_LAT = "finish_location_lat";
    public final static String COLUMN_FINISH_LOCATION_LON = "finish_location_lon";
    public final static String COLUMN_DISTANCE = "distance";
    public final static String COLUMN_TRIP_DATE = "trip_date";

    @Override
    public void onCreate(SQLiteDatabase database) {String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_START_LOCATION + " TEXT NOT NULL, " + COLUMN_FINISH_LOCATION + " TEXT NOT NULL, "
            + COLUMN_TRIP_DATE + " TEXT NOT NULL, " + COLUMN_DISTANCE + " REAL, " + COLUMN_START_LOCATION_LAT + " TEXT, "+ COLUMN_START_LOCATION_LON + " TEXT, "+ COLUMN_FINISH_LOCATION_LAT +" TEXT, "+ COLUMN_FINISH_LOCATION_LON +" TEXT);";
        database.execSQL(SQL_CREATE_ITEMS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public void populateTripListArray(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (result.getCount() != 0){
            while(result.moveToNext()){
                int id = result.getInt(0);
                String startLocation = result.getString(1);
                String finishLocation = result.getString(2);
                String tripDate = result.getString(3);
                double distance = result.getDouble(4);
                String startLat = result.getString(5);
                String startLon = result.getString(6);
                String finishLat = result.getString(7);
                String finishLon = result.getString(8);
                Trip trip = new Trip(id, startLocation, finishLocation, tripDate, distance, startLat, startLon, finishLat, finishLon);
                Trip.tripArrayList.add(trip);
            }
        }
    }

    public void delTrip(String id){
        //Log.d("deldata", String.valueOf(id));
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "_id=?", new String[]{id});
        sqLiteDatabase.close();
    }

    public static MyDataBaseHelper instanceOfDatabase(Context context)
    {
        if(myDataBaseHelper == null)
            myDataBaseHelper = new MyDataBaseHelper(context);
        return myDataBaseHelper;
    }
}




