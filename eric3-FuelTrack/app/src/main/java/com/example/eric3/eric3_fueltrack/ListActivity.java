package com.example.eric3.eric3_fueltrack;

import android.app.Activity;
import android.content.Context;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

    //TODO in windowEntryActivity.java, send Intent using GSON instead of serializable
public class ListActivity extends AppCompatActivity {
    protected ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private ArrayAdapter<LogEntry> entryAdapter;
    private String filename = "LogFile.bin";
    private ListView entryList;
    private TextView entryTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entryList = (ListView) findViewById(R.id.lv_list_entryList);
        entryTotal = (TextView) findViewById(R.id.tv_list_total);

        // This sets a Long Click function to the ListView. It is used to edit the information in the entry
        entryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (entries.size() != 0) {
                    click("Edit", position);
                }
                return false;
            }
        });
    }

    // Load or update all data and displays
    @Override
    protected void onStart() {
        super.onStart();
        FiletoEntry();
        displayTotal();
        entryAdapter = new ArrayAdapter<LogEntry>(this,R.layout.itemlist, entries);
        entryList.setAdapter(entryAdapter);
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
        // This action bar item will clear all entries
        else if (id == R.id.action_delete){
            entries.clear();
            entryAdapter.notifyDataSetChanged();
            EntrytoFile();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If requestCode == 1, then it signifies that the recently obtained LogEntry object is to be added
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                Gson gson = new Gson();

                LogEntry recentEntry = gson.fromJson(data.getStringExtra("entry"), LogEntry.class);
                this.entries.add(recentEntry);
                entryAdapter.notifyDataSetChanged();
                EntrytoFile();
            }
        }
        // If requestCode == 0, then it signifies that the recent obtained LogEntry object is an edit to existing object
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                int position = data.getIntExtra("pos", -1);
                Gson gson = new Gson();
                LogEntry recentEntry = gson.fromJson(data.getStringExtra("entry"), LogEntry.class);
                if (position >= 0) {
                    this.entries.set(position, recentEntry);
                }
                entryAdapter.notifyDataSetChanged();
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
            Gson gson = new Gson();
            intent.putExtra("entry", gson.toJson(entries.get(position)));
            startActivityForResult(intent, 0);
        }
        return false;
    }

    // This function updates the accumulated cost of all fuel entries
    public void displayTotal(){
        int lstSize = entries.size();
        double total = 0.0;
        for (int count = 0; count < lstSize; count++) {
            total += entries.get(count).getFcost();
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        entryTotal.setText("Total Cost of Fuel: " + df.format(total));
    }

    // This function reads a file and converts it to LogEntry objects placed in ArrayList<LogEntry>
    public void FiletoEntry() {
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader bin = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<LogEntry>>() {}.getType();
            entries = gson.fromJson(bin, type);
            fis.close();
        } catch (Exception e) { }
    }

    // This function saves the ArrayList<LogEntry> into a file
    public void EntrytoFile(){
        try{
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(entries, bout);
            bout.flush();
            fos.close();
            bout.close();
        } catch (Exception e) {       }
    }
}
