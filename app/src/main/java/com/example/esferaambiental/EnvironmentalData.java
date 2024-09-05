package com.example.esferaambiental;

public class EnvironmentalData {

    private String data;
    private double latitude;
    private double longitude;

    public EnvironmentalData() {
        // Default constructor required for calls to DataSnapshot.getValue(EnvironmentalData.class)
    }

    public EnvironmentalData(String data, double latitude, double longitude) {
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getData() {
        return data;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
