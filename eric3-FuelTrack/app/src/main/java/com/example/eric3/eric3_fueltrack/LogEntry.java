package com.example.eric3.eric3_fueltrack;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Eric on 2016-01-16.
 */

// This is a LogEntry object. It holds all required information to track fuel entries into the application
public class LogEntry implements Serializable{
    private String date;
    private String station;
    private Double odom;
    private String Fgrade;
    private Double Famount;
    private Double Funit;
    private Double Fcost;

// Constructor for LogEntry
    public LogEntry(String date, String station, Double odom, String fgrade, Double famount, Double funit) {
        this.date = date;
        this.station = station;
        this.odom = odom;
        this.Fgrade = fgrade;
        this.Famount = famount;
        this.Funit = funit;
        this.Fcost = funit * famount;
    }

    public String getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public String getSOdom() {
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(odom);
    }

    public String getFgrade() {
        return Fgrade;
    }

    public String getSFamount() {
        DecimalFormat df = new DecimalFormat("#0.000");
        return df.format(Famount);
    }

    public String getSFunit() {
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(Funit);
    }

    public String getSFcost(){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Fcost);
    }
    public Double getFcost() {
        return Fcost;
    }
    // This function is used to concatenate the data in the object into a String
    public String EntrytoString(){
        return date + ", " + station + ", " + getSOdom() + " KM, " + Fgrade + ", " + getSFamount() + " L, " + getSFunit() + "¢/L, $" + getSFcost();
    }
}
