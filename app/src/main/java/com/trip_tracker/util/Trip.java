package com.trip_tracker.util;

import java.util.ArrayList;

public class Trip {
    public static ArrayList<Trip> tripArrayList = new ArrayList<>();
    private int id;
    private String startLocation;
    private String finishLocation;
    private double distance;
    private String Date;
    private String startLat;
    private String startLon;
    private String finishLat;
    private String finishLon;

    public Trip(int id, String startLocation, String finishLocation, String date, Double distance, String startLat, String startLon, String finishLat, String finishLon) {
        this.id = id;
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.distance = distance;
        Date = date;
        this.startLat = startLat;
        this.startLon = startLon;
        this.finishLat = finishLat;
        this.finishLon = finishLon;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLon() {
        return startLon;
    }

    public void setStartLon(String startLon) {
        this.startLon = startLon;
    }

    public String getFinishLat() {
        return finishLat;
    }

    public void setFinishLat(String finishLat) {
        this.finishLat = finishLat;
    }

    public String getFinishLon() {
        return finishLon;
    }

    public void setFinishLon(String finishLon) {
        this.finishLon = finishLon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setFinishLocation(String finishLocation) {
        this.finishLocation = finishLocation;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getFinishLocation() {
        return finishLocation;
    }

    public double getDistance() {
        return distance;
    }

    public String getDate() {
        return Date;
    }
}
