package com.example.application.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActInfo extends AppCompatActivity {
    TextView name;
    TextView date;
    TextView time;
    TextView remarks;
    Button delete;
    String displaydate;
    String displaytime;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        displaydate = bundle.getString("date");
        displaytime = bundle.getString("time");
        name = (TextView) findViewById(R.id.Presentname);
        date = (TextView) findViewById(R.id.presentDate);
        time = (TextView) findViewById(R.id.presentTime);
        remarks = (TextView) findViewById(R.id.presentRemarks);
        delete = (Button) findViewById(R.id.delete);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public static final String TAG = "NAVI";

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                  //  toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            try {
                Activity aInfo = new Activity();
                aInfo.setActName(ds.child("users").child(userID).child("Activities").child(displaydate).child(displaytime).getValue(Activity.class).getActName());
                aInfo.setDate(ds.child("users").child(userID).child("Activities").child(displaydate).child(displaytime).getValue(Activity.class).getDate());
                aInfo.setTime(ds.child("users").child(userID).child("Activities").child(displaydate).child(displaytime).getValue(Activity.class).getTime());
                aInfo.setRemarks(ds.child("users").child(userID).child("Activities").child(displaydate).child(displaytime).getValue(Activity.class).getRemarks());
                //display all the information
                name.setText(aInfo.getActName());
                date.setText(aInfo.getDate());
                time.setText(aInfo.getTime());
                remarks.setText(aInfo.getRemarks());
            } catch (NullPointerException e) {

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void deleteclicked(View view) {
            DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("user").child("users").child(userID).child("Activities").child(displaydate).child(displaytime);
            db_node.setValue(null);
            Bundle bundle = new Bundle();
            Intent intent = new Intent(ActInfo.this, Date.class);
            //Add your data to bundle
            bundle.putString("date", displaydate);
            //Add the bundle to the intent
            intent.putExtras(bundle);
            startActivity(intent);
    }
    public void back(View view) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(ActInfo.this, Date.class);
        //Add your data to bundle
        bundle.putString("date", displaydate);
        //Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);
    }
}


