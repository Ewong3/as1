package com.example.eric3.eric3_fueltrack;

/* This is the interface Entry. I have put get methods for all the data stored in
        a LogEntry class.
 */
public interface Entry {
    public String getDate();
    public String getStation();
    public Double getOdom();
    public String getSOdom();
    public String getFgrade();
    public Double getFamount();
    public String getSFamount();
    public Double getFunit();
    public String getSFunit();
    public Double getFcost();
}
