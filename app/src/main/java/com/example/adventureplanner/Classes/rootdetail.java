package com.example.adventureplanner.Classes;

public class rootdetail {
    private String source;
    private double dLongitude;  // Using double for decimal coordinates
    private String routeType;
    private double sLatitude;   // Using double for decimal coordinates
    private double sLongitude;  // Using double for decimal coordinates
    private String destination;

    public rootdetail(String source, double dLongitude, String routeType, double sLatitude, double sLongitude, String destination) {
        this.source = source;
        this.dLongitude = dLongitude;
        this.routeType = routeType;
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getdLongitude() {
        return dLongitude;
    }

    public void setdLongitude(double dLongitude) {
        this.dLongitude = dLongitude;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public double getsLatitude() {
        return sLatitude;
    }

    public void setsLatitude(double sLatitude) {
        this.sLatitude = sLatitude;
    }

    public double getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
