package com.example.michael.timeclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //getActionBar().setTitle("TimeClock App");
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
        CheckBox leftEarly = (CheckBox) findViewById(R.id.left_Early);
        CheckBox tookLunch = (CheckBox) findViewById(R.id.took_Lunch);
        TextView dateBox = (TextView) findViewById(R.id.date);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        //System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        dateBox.setText("Statement for " + dateFormat.format(date));

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

        DateFormat sdf = new SimpleDateFormat("HH:mm");

        Date am_in_time = sdf.parse(amIn.getText().toString());
        Date am_out_time = sdf.parse(amOut.getText().toString());
        Date pm_in_time = sdf.parse(pmIn.getText().toString());
        Date pm_out_time = sdf.parse(pmOut.getText().toString());

        long diffMs = am_out_time.getTime() - am_in_time.getTime();
        long diffSec = diffMs / 1000;
        long amTotalMinutes = diffSec / 60;

        diffMs = pm_out_time.getTime() - pm_in_time.getTime();
        diffSec = diffMs / 1000;
        long pmTotalMinutes = diffSec / 60;

        diffMs = pm_out_time.getTime() - am_in_time.getTime();
        diffSec = diffMs / 1000;
        long allDayMinutes = diffSec / 60;

        long day_minutes = 0;

        // If the "Took Lunch" Box is checked, subtract the time between am_out and pm_in from total
        if(tookLunch.isChecked()) {
            day_minutes = amTotalMinutes + pmTotalMinutes;
        } else {
            day_minutes = allDayMinutes;
        }

        // If the "Left Early" Box is checked, only read am_in and am_out
        if(leftEarly.isChecked()) {
            day_minutes = amTotalMinutes;
        }

        double day_hours = day_minutes / 60.0;
        dayHours.setText(String.format("%.2f", day_hours) + " HOURS");

        double day_wages = 1.0 * day_hours * Double.parseDouble(payRate.getText().toString());
        dayWages.setText("$" + String.format("%.2f", day_wages) + " (TAXES NOT COMPUTED)");
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
        CheckBox leftEarly = (CheckBox) findViewById(R.id.left_Early);
        CheckBox tookLunch = (CheckBox) findViewById(R.id.took_Lunch);
        TextView dateBox = (TextView) findViewById(R.id.date);

        leftEarly.setChecked(false);
        tookLunch.setChecked(true);

        amIn.requestFocus();
        amIn.setText("");
        amOut.setText("");
        pmIn.setText("");
        pmOut.setText("");

        dateBox.setText("Tap CALCULATE");

        payRate.setText("15.00");
        dayHours.setText("TOTAL DAILY HOURS");
        dayWages.setText("GROSS DAILY WAGES");
    }
}
