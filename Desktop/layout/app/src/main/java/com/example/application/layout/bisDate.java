package com.example.application.layout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.lang.*;

public class bisDate extends AppCompatActivity {
    String display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bis_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();


        //Extract the data…
        display = bundle.getString("date");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Lobster.ttf");
        TextView displaytv = (TextView) findViewById(R.id.displayTv);
        displaytv.setTypeface(custom_font);
        displaytv.setText(display);
    }
    public void bisInfo(View view) {
        Intent intent = new Intent(bisDate.this, Class.class);
        startActivity(intent);
    }



}

