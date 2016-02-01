package com.example.eric3.eric3_fueltrack;

import java.text.DecimalFormat;

// This is a LogEntry object. It holds all required information to track fuel entries into the application
public class LogEntry implements Entry {
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
        this.Fcost = (funit * famount)/ 100;
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

    public Double getOdom() {
        return odom;
    }

    public Double getFamount() {
        return Famount;
    }

    public Double getFunit() {
        return Funit;
    }

    public String getSFamount() {
        DecimalFormat df = new DecimalFormat("#0.000");
        return df.format(Famount);
    }

    public String getSFunit() {
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(Funit);
    }

    public String getSFcost() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Fcost);
    }
    public Double getFcost() {
        return Fcost;
    }

    // Override to concatenate all the data in the LogEntry object into a String.
    //      I Understand this is not a good practice and should have done this in
    //      ListActivity.java, but I could not figure out a way to avoid using
    //      this along with the ArrayAdapter.
    @Override
    public String toString() {
        return date + ", " + station + ", " + getSOdom() + " KM, " + Fgrade + ", " + getSFamount() + " L, " + getSFunit() + "¢/L, $" + getSFcost();
    }

    /*
    Last accessed: 2016, jan 18
    https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
     */
}
