package com.example.adventureplanner.Classes;

public class route {
    private String source;
    private String destination;
    private double sLatitude;  // Corrected typo: sLatitude
    private double sLongitude;
    private double dLatitude;  // Corrected typo: dLatitude
    private double dLongitude;
    private String routeType;
    private String name;
    private int tId;

    public route(String source, String destination, double sLatitude, double sLongitude, double dLatitude, double dLongitude, String routeType, String name, int tId) {
        this.source = source;
        this.destination = destination;
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.dLatitude = dLatitude;
        this.dLongitude = dLongitude;
        this.routeType = routeType;
        this.name = name;
        this.tId = tId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public double getdLatitude() {
        return dLatitude;
    }

    public void setdLatitude(double dLatitude) {
        this.dLatitude = dLatitude;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }
}
