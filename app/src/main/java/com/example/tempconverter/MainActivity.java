package com.example.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences.Editor;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener {


    // define variables for the widgets

    private EditText fahrTextView;
    private EditText celsTextView;


    // define SharedPreferences object
    private SharedPreferences SavedValue;


    // define instance variables that should be saved
    private String tempValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fahrTextView = (EditText) findViewById(R.id.fahrTextView);
        celsTextView = (EditText) findViewById(R.id.celsTextView);

        //set the listeners
        fahrTextView.setOnEditorActionListener(this);

        // get shared preferences object
        SavedValue = getSharedPreferences("SavedValue", MODE_PRIVATE);

    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = SavedValue.edit();
        editor.putString("tempValue", tempValue);
        editor.commit();

        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        tempValue = SavedValue.getString("tempValue", "");

        // set the temp amount on widget
        fahrTextView.setText(tempValue);

        // use getTemp to calculate conversion
        getTemp();
    }

    public void getTemp () {

        // get the temp
        tempValue = fahrTextView.getText().toString();
        float tempConv;
        if (tempValue.equals("")) {
            tempConv = 0;
        } else {
            tempConv = Float.parseFloat(tempValue);
        }

        // calculate conversion to celsius
        float temp = (tempConv - 32) * 5/9;

        //display results
        NumberFormat t = NumberFormat.getNumberInstance();
        celsTextView.setText(t.format(temp));


    }

    public void clear( View v) {
        fahrTextView.setText("");
        celsTextView.setText("");
    }


    @Override
    public boolean onEditorAction( TextView v, int actionId, KeyEvent event ) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            getTemp();
        }
        return false;
    }
}
