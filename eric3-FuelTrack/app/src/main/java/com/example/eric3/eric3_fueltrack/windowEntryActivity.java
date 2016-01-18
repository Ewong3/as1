package com.example.eric3.eric3_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class windowEntryActivity extends AppCompatActivity {
    private String mode;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_entry);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int) (dm.widthPixels * 0.9);
        int height = (int) (dm.heightPixels * 0.9);
        getWindow().setLayout(width, height);

        Intent intent = getIntent();
        this.mode = intent.getStringExtra("mode");
        this.pos = intent.getIntExtra("pos", -1);

        TextView tv_title = (TextView) findViewById(R.id.tv_window_title);
        Button b_ok = (Button) findViewById(R.id.b_window_ok);
        tv_title.setText(mode + "ing");
        b_ok.setText(mode);

    }

    public void b_onClick_windowCancel(View view) {
        Intent retIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,retIntent);
        finish();
    }

    public void b_onClick_windowOk(View view) {
        Intent retIntent = new Intent();

        retIntent.putExtra("pos", pos);
        setResult(Activity.RESULT_OK, retIntent);
        finish();
    }

    public LogEntry getEntryData(){
        //TODO get data from XML EditText
        //TODO Serializable problem
        return newEntry;
    }
    /*
    http://www.technotalkative.com/android-send-object-from-one-activity-to-another-activity/
    http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
    http://stackoverflow.com/questions/32050647/how-to-create-simple-android-studio-pop-up-window-with-edittext-field-for-data-i
    */
}
