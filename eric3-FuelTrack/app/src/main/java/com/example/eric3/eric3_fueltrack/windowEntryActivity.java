package com.example.eric3.eric3_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class windowEntryActivity extends AppCompatActivity {
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_entry);

        setWindow();

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        this.pos = intent.getIntExtra("pos", -1);

        // Set title of the popup window
        TextView tv_title = (TextView) findViewById(R.id.tv_window_title);
        Button b_ok = (Button) findViewById(R.id.b_window_ok);
        tv_title.setText(mode + "ing");
        b_ok.setText(mode);

        if (mode.equals("Edit")){
            LogEntry editEntry = (LogEntry) intent.getSerializableExtra("entry");
            editMode(editEntry);
        }
    }

    // Send a canceled result back to ListActivity
    public void b_onClick_windowCancel(View view) {
        Intent retIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, retIntent);
        finish();
    }

    // Send a successful result back to ListActivity
    public void b_onClick_windowOk(View view) {
        Intent retIntent = new Intent();
        retIntent.putExtra("pos", pos);
        LogEntry newestLog = getEntryData();
        Gson gson = new Gson();

        retIntent.putExtra("entry", gson.toJson(newestLog));
        setResult(Activity.RESULT_OK, retIntent);
        finish();
    }

    // This function pulls information from the initial entry into the window
    public void editMode(LogEntry editEntry){
        EditText et_date = (EditText) findViewById(R.id.et_window_date);
        EditText et_station = (EditText) findViewById(R.id.et_window_station);
        EditText et_odo = (EditText) findViewById(R.id.et_window_odo);
        EditText et_grade = (EditText) findViewById(R.id.et_window_grade);
        EditText et_amount = (EditText) findViewById(R.id.et_window_amount);
        EditText et_Ucost = (EditText) findViewById(R.id.et_window_Ucost);

        et_date.setText(editEntry.getDate());
        et_station.setText(editEntry.getStation());
        et_odo.setText(editEntry.getSOdom());
        et_grade.setText(editEntry.getFgrade());
        et_amount.setText(editEntry.getSFamount());
        et_Ucost.setText(editEntry.getSFunit());
    }

    // This function will get all on the user inputs from the EditText boxes and return a LogEntry object
    public LogEntry getEntryData(){
        EditText et_date = (EditText) findViewById(R.id.et_window_date);
        EditText et_station = (EditText) findViewById(R.id.et_window_station);
        EditText et_odo = (EditText) findViewById(R.id.et_window_odo);
        EditText et_grade = (EditText) findViewById(R.id.et_window_grade);
        EditText et_amount = (EditText) findViewById(R.id.et_window_amount);
        EditText et_Ucost = (EditText) findViewById(R.id.et_window_Ucost);

        String date = et_date.getText().toString();
        String station = et_station.getText().toString();
        String fgrade = et_grade.getText().toString();
        String Sodo = et_odo.getText().toString();
        String Sfamount = et_amount.getText().toString();
        String Sfunitcost = et_Ucost.getText().toString();

        double odo = 0.0;
        if (!Sodo.isEmpty()) {
            odo = Double.parseDouble(Sodo);
        }

        double famount = 0.0;
        if (!Sfamount.isEmpty()) {
            famount = Double.parseDouble(Sfamount);
        }

        double funitcost = 0.0;
        if (!Sfunitcost.isEmpty()) {
          funitcost  = Double.parseDouble(Sfunitcost);
        }

        return new LogEntry(date, station, odo, fgrade, famount, funitcost);
    }

    // The setWindow function shrinks the window size of the activity to look similar to a popup
    public void setWindow(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.9);
        int height = (int) (dm.heightPixels * 0.9);
        getWindow().setLayout(width, height);
    }
    /*
    http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
    http://stackoverflow.com/questions/32050647/how-to-create-simple-android-studio-pop-up-window-with-edittext-field-for-data-i
    https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
    */
}
