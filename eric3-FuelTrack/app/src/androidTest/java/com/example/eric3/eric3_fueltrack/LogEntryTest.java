package com.example.eric3.eric3_fueltrack;

import android.test.ActivityInstrumentationTestCase2;

public class LogEntryTest extends ActivityInstrumentationTestCase2{

    public LogEntryTest() {
        super(LogEntry.class);
    }

    // Tests the formatting of decimal places.
    public void testEntryFormat(){
        LogEntry entry = new LogEntry("2016-01-01", "7-11", 123456.0, "premium", 34.4, 76.4);
        assertEquals(entry.getSOdom(),"123456.0");
        assertEquals(entry.getSFamount(),"34.400");
        assertEquals(entry.getSFunit(),"76.4");
        assertEquals(entry.getSFcost(),"26.28");
    }

    // Tests the calculation of the total cost of fuel. Want to multiply fuel amount by fuel cost and convert it to dollars.
    public void testEntrygetFcost() {
        LogEntry entry = new LogEntry("2016-01-01", "7-11", 123456.0, "premium", 34.4, 76.4);
        assertEquals(entry.getFcost(),34.4 * 76.4 / 100);
    }

    // Test the toString output format of Entry.
    public void testEntrytoString() {
        LogEntry entry = new LogEntry("2016-01-01", "7-11", 123456.0, "premium", 34.4, 76.4);
        assertEquals(entry.toString(), "2016-01-01, 7-11, 123456.0 KM, premium, 34.400 L, 76.4¢/L, $26.28" );
    }

}
