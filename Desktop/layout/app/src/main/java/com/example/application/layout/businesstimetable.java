package com.example.application.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class businesstimetable extends AppCompatActivity {
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesstimetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               // Toast.makeText(getApplicationContext(),dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(businesstimetable.this,bisDate.class);
                String display = dayOfMonth+" "+MONTHS[month]+" "+year;
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("date",display);
                //Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

        );



    }

}
