package com.example.eric3.eric3_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    protected ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private String filename = "LogFile.bin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load all data and displays
        //TODO Load ArrayList entries from file
        FiletoEntry();
        displayTotal();
        displayList();

        // This sets a Long Click function to the ListView. It is used to edit the information in the entry
        final ListView lst = (ListView)findViewById(R.id.EntryList);
        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (entries.size() != 0) {
                    click("Edit", position);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // This adds an action bar item for adding a new entry.
        else if (id == R.id.action_add){
            click("Add", -1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If requestCode == 1, then it signifies that the recently obtained LogEntry object is to be added
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                //LogEntry recentEntry = createLog(data);
                LogEntry recentEntry = (LogEntry) data.getSerializableExtra("entry");
                this.entries.add(recentEntry);
                //TODO Save data
                displayTotal();
                displayList();
                EntrytoFile();
            }
        }
        // If requestCode == 0, then it signifies that the recent obtained LogEntry object is an edit to existing object
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                int position = data.getIntExtra("pos", -1);
                //LogEntry recentEntry = createLog(data);
                LogEntry recentEntry = (LogEntry) data.getSerializableExtra("entry");
                if (position >= 0) {
                    this.entries.set(position, recentEntry);
                }
                //TODO Save data
                displayTotal();
                displayList();
                EntrytoFile();
            }
        }
    }

    // This changes the resultCode depending on if it is an Add(1) or an Edit(0)
    public boolean click(String mode, int position){
        Intent intent = new Intent(ListActivity.this, windowEntryActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("pos", position);
        if (mode.equals("Add")) {
            startActivityForResult(intent, 1);
        }
        else {
            intent.putExtra("entry", entries.get(position));
            startActivityForResult(intent, 0);
        }
        return false;
    }
    /*
    public LogEntry createLog(Intent intent) {
        String date = intent.getStringExtra("date");
        String station = intent.getStringExtra("station");
        double odo = intent.getDoubleExtra("odo", 0.0);
        String fgrade = intent.getStringExtra("grade");
        double famount = intent.getDoubleExtra("amt", 0.0);
        double funitcost = intent.getDoubleExtra("Ucost", 0.0);
        LogEntry newEntry = new LogEntry(date, station, odo, fgrade, famount, funitcost);
        return newEntry;
    }
    */

    // This function refreshes the ListView
    public void displayList(){
        ArrayList<String> lst = new ArrayList<String>();
        int lstSize = entries.size();
        if (lstSize != 0){
            for (int count = 0; count < lstSize; count ++) {
                lst.add(count + entries.get(count).EntrytoString());
            }
        } else {
            lst.add("No entries :(");
        }
        ListAdapter adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lst);
        final ListView view1 = (ListView)findViewById(R.id.EntryList);

        view1.setAdapter(adapt);
    }

    // This function updates the accumulated cost of all fuel entries
    public void displayTotal(){
        int lstSize = entries.size();
        double total = 0.0;
        for (int count = 0; count < lstSize; count++) {
            total += entries.get(count).getFcost();
        }
        TextView tv_total = (TextView) findViewById(R.id.tv_list_total);
        DecimalFormat df = new DecimalFormat("#0.00");
        tv_total.setText("Total Cost of Fuel: " + df.format(total));
    }
    // This function reads a file and converts it to LogEntry objects placed in ArrayList<LogEntry>
    public void FiletoEntry() {
        try {
            File file = new File(this.getFilesDir(),filename);
            FileInputStream inputStream = new FileInputStream(file);
            if (inputStream != null) {
                ObjectInputStream objectInput = new ObjectInputStream(inputStream);
                LogEntry log;
                while ((log = (LogEntry) objectInput.readObject()) != null){
                    entries.add(log);
                }
                objectInput.close();
                inputStream.close();
            }
        } catch (Exception e) { }
    }

    // This function saves the ArrayList<LogEntry> into a file
    public void EntrytoFile(){
        try{
            File file = new File(this.getFilesDir(),filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
            int lstSize = entries.size();
            for (int count = 0; count < lstSize; count++) {
                objectOutput.writeObject(entries.get(count));
                objectOutput.flush();
            }
            objectOutput.close();
            outputStream.close();
        } catch (Exception e) {       }
    }
}
