package com.example.michael.timeclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Define ui members

    //TextView amOut = (TextView)findViewById(R.id.am_out);
    //TextView pmIn = (TextView)findViewById(R.id.pm_in);
    //TextView pmOut = (TextView)findViewById(R.id.pm_out);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateWages(View view) throws ParseException {
        // Read the input boxes, and calculate the wages
        EditText amIn = (EditText) findViewById(R.id.am_in);
        EditText amOut = (EditText) findViewById(R.id.am_out);
        EditText pmIn = (EditText) findViewById(R.id.pm_in);
        EditText pmOut = (EditText) findViewById(R.id.pm_out);
        EditText payRate = (EditText) findViewById(R.id.wage_hr);
        TextView dayHours = (TextView) findViewById(R.id.hours_view);
        TextView dayWages = (TextView) findViewById(R.id.wages_view);

        // If anything is blank, assume it to be zero
        if (amIn.getText().toString().equals("")) {
            amIn.setText("00:00");
        }
        if (amOut.getText().toString().equals("")) {
            amOut.setText("00:00");
        }
        if (pmIn.getText().toString().equals("")) {
            pmIn.setText("00:00");
        }
        if (pmOut.getText().toString().equals("")) {
            pmOut.setText("00:00");
        }
        if (payRate.getText().toString().equals("")) {
            payRate.setText("15");
        }

        DateFormat sdf = new SimpleDateFormat("HH:mm"); // or "hh:mm" for 12 hour format

        // Sum am_out and pm_in, subtract that sum from the sum of am_in and pm_out
        Date am_in_time = sdf.parse(amIn.getText().toString());
        Date am_out_time = sdf.parse(amOut.getText().toString());

        long diffMs = am_out_time.getTime() - am_in_time.getTime();
        long diffSec = diffMs / 1000;
        long amTotalMinutes = diffSec / 60;

        Date pm_in_time = sdf.parse(pmIn.getText().toString());
        Date pm_out_time = sdf.parse(pmOut.getText().toString());

        diffMs = pm_out_time.getTime() - pm_in_time.getTime();
        diffSec = diffMs / 1000;
        long pmTotalMinutes = diffSec / 60;


        long day_minutes = amTotalMinutes + pmTotalMinutes;

        double day_hours = day_minutes / 60.0;
        dayHours.setText(Double.toString(day_hours) + " HOURS");

        double day_wages = 1.0 * day_hours * Double.parseDouble(payRate.getText().toString());
        dayWages.setText("$" + Double.toString(day_wages));
    }

    private int getHours(String timeString) {
        return Integer.parseInt(timeString.split(":")[0].trim());
    }
    private int getMinutes(String timeString) {
        return Integer.parseInt(timeString.split(":")[1].trim());
    }


    public void clearFields(View view) {
        // Clear all fields and set back to default values
        EditText amIn = (EditText) findViewById(R.id.am_in);
        EditText amOut = (EditText) findViewById(R.id.am_out);
        EditText pmIn = (EditText) findViewById(R.id.pm_in);
        EditText pmOut = (EditText) findViewById(R.id.pm_out);
        EditText payRate = (EditText) findViewById(R.id.wage_hr);
        TextView dayHours = (TextView) findViewById(R.id.hours_view);
        TextView dayWages = (TextView) findViewById(R.id.wages_view);

        amIn.requestFocus();
        amIn.setText(" ");
        amOut.setText(" ");
        pmIn.setText(" ");
        pmOut.setText(" ");

        payRate.setText("15");
        dayHours.setText("TOTAL DAILY HOURS");
        dayWages.setText("GROSS DAILY WAGES");
    }
}
