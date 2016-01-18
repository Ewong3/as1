package com.example.eric3.eric3_fueltrack;

/**
 * Created by Eric on 2016-01-16.
 */
public class LogEntry{
    public String date;
    public String station;
    public Double odom;
    public String Fgrade;
    public Double Famount;
    public Double Funit;
    public Double Fcost;


    public LogEntry(String date, String station, Double odom, String fgrade, Double famount, Double funit) {
        this.date = date;
        this.station = station;
        this.odom = odom;
        this.Fgrade = fgrade;
        this.Famount = famount;
        this.Funit = funit;
        this.Fcost = funit * famount;
    }
/*
    public LogEntry(String station, Double odom, String fgrade, Double famount, Double funit) {
        this.date =
        this.station = station;
        this.odom = odom;
        this.Fgrade = fgrade;
        this.Famount = famount;
        this.Funit = funit;
        this.Fcost = funit * famount;
    }
*/

}
