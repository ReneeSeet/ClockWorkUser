package com.example.application.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;


public class MainActivity extends Main3Activity{
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        calendar = (CalendarView) findViewById(R.id.simpleCalendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
               // Toast.makeText(getApplicationContext(),dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,Date.class);
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

        TextView tx = (TextView)findViewById(R.id.textView);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "Lobster.ttf");

        tx.setTypeface(custom_font);

    }


    public void appointmentbutton (View view ){
        Intent intent = new Intent(MainActivity.this,Explore.class);
        startActivity(intent);
    }

    public void personalbutton (View view ){
        Intent intent = new Intent(MainActivity.this,PersonalAdd.class);
        startActivity(intent);

    }
    public void navopen(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

}
