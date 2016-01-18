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

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ArrayList<LogEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO Load ArrayList entries and put into adapter
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("2016-01-18, Costco, 200123.5 km, regular, 40.234 L, 79.4 cents/L");
        ListAdapter adapt = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lst);

        final ListView view1 = (ListView)findViewById(R.id.EntryList);

        view1.setAdapter(adapt);
        view1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Intent intent = new Intent(ListActivity.this, windowEntryActivity.class);
                startActivityForResult(intent, 1);
                return false;
            */
                click("Edit", position);
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
        else if (id == R.id.action_add){
            click("Add", -1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){

                entries.add(recentEntry);
                //TODO Save data and reload list view
                //TODO Serializable Problem
            }
        }
        if (requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                int position = data.getIntExtra("pos", -1);

                if (position >= 0) {
                    entries.add(position, recentEntry);
                }
                //TODO Save data and reload list view
            }
        }
    }

    public boolean click(String mode, int position){
        Intent intent = new Intent(ListActivity.this, windowEntryActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("pos", position);
        if (mode.equals("Add")) {
            startActivityForResult(intent, 1);
        }
        else {
            startActivityForResult(intent, 0);
        }
        return false;
    }




}
