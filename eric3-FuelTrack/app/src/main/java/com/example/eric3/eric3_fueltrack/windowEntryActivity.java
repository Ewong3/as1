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
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class windowEntryActivity extends AppCompatActivity {
    private int pos;
    private LogEntry recentEntry;
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

        String title = mode + "ing";
        tv_title.setText(title);
        b_ok.setText(mode);

        //
        if (mode.equals("Edit")){
            Gson gson = new Gson();
            LogEntry editEntry = gson.fromJson(intent.getStringExtra("entry"), LogEntry.class);
            editMode(editEntry);
        }
    }

    // Send a canceled result back to ListActivity.java
    public void b_onClick_windowCancel(View view) {
        Intent retIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, retIntent);
        finish();
    }

    // Send a successful result back to ListActivity.java
    public void b_onClick_windowOk(View view) {
        Intent retIntent = new Intent();
        retIntent.putExtra("pos", pos);
        if (getEntryData()) {
            Gson gson = new Gson();

            retIntent.putExtra("entry", gson.toJson(this.recentEntry));
            setResult(Activity.RESULT_OK, retIntent);
            finish();
        }
    }

    // This function pulls information from the selected entry into the window
    //      I made made this so the user does not have to re-fill all EditText
    //      views when editing.
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

    // This function will get all on the user inputs from the EditText boxes
    //      and return a Boolean depending on failure or success.
    public boolean getEntryData(){
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

        /* I have assumed that if the User does not input any data for the Date, then the app
               initialize a yyyy-mm-dd format automatically.
               If Station or Grade is left blank by the user, it will remain blank.
         */
        if (date.equals("")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(cal.getTime());
        }

        /* This section of getEntryData() checks if the EditText views for double types
              are empty. If they are, they will be initialized to 0.0. If they are invalid
              numbers, then an error will display.
        */
        double odo= 0.0;
        double famount = 0.0;
        double funitcost = 0.0;
        try {
            if (!Sodo.isEmpty()) {
                odo = Double.parseDouble(Sodo);
            }

            if (!Sfamount.isEmpty()) {
                famount = Double.parseDouble(Sfamount);
            }

            if (!Sfunitcost.isEmpty()) {
                funitcost = Double.parseDouble(Sfunitcost);
            }
            this.recentEntry = new LogEntry(date, station, odo, fgrade, famount, funitcost);

        } catch (NumberFormatException except) {
            Toast toast = Toast.makeText(this, "Invalid numbers", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    // The setWindow function shrinks the window size of the activity to look
    //      similar to a popup or a window.
    public void setWindow(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.9);
        int height = (int) (dm.heightPixels * 0.9);
        getWindow().setLayout(width, height);
    }

    /*
    Last accessed: 2016, Jan.17
    User: Nishant
        http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android

    Last accessed: 2-16, Jan.17
    User: Tushar Saha
        http://stackoverflow.com/questions/32050647/how-to-create-simple-android-studio-pop-up-window-with-edittext-field-for-data-i
    */
}
