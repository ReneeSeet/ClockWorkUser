package com.example.application.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

public class Explore extends Main3Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_explore, contentFrameLayout);

    }

    public void fitnessclicked(View view) {
        Intent intent = new Intent(Explore.this,fitness.class);
        startActivity(intent);
    }
    public void navopen(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

}
