package com.example.application.layout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Date extends AppCompatActivity {
    private static final String TAG = "activity";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    public static HashMap<String, TextView> MAP;
    public static String[] TIME = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};
    String display;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MAP = new HashMap<String, TextView>();
        MAP.put("8:00", (TextView) findViewById(R.id.eight));
        MAP.put("9:00", (TextView) findViewById(R.id.nine));
        MAP.put("10:00", (TextView) findViewById(R.id.ten));
        MAP.put("11:00", (TextView) findViewById(R.id.eleven));
        MAP.put("12:00", (TextView) findViewById(R.id.twelve));
        MAP.put("13:00", (TextView) findViewById(R.id.thirteen));
        MAP.put("14:00", (TextView) findViewById(R.id.fourteen));
        MAP.put("15:00", (TextView) findViewById(R.id.fifteen));
        MAP.put("16:00", (TextView) findViewById(R.id.sixteen));
        MAP.put("17:00", (TextView) findViewById(R.id.seventeen));
        MAP.put("18:00", (TextView) findViewById(R.id.eighteen));
        MAP.put("19:00", (TextView) findViewById(R.id.nineteen));
        MAP.put("20:00", (TextView) findViewById(R.id.twenty));
        MAP.put("21:00", (TextView) findViewById(R.id.twentyone));
        MAP.put("22:00", (TextView) findViewById(R.id.twentytwo));
        MAP.put("23:00", (TextView) findViewById(R.id.twentythree));
        MAP.put("24:00", (TextView) findViewById(R.id.twentyfour));

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        display = bundle.getString("date");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Lobster.ttf");
        TextView displaytv = (TextView) findViewById(R.id.displayTv);
        displaytv.setTypeface(custom_font);
        displaytv.setText(display);
        display = display.replaceAll("\\s+", "");


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
                   // toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                   // toastMessage("Successfully signed out.");
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
            for (String time : TIME) {
                Activity aInfo = new Activity();
                Log.d(TAG, "showData: name: " + time);
                try {
                    String thisname = ds.child("users").child(userID).child("Activities").child(display).child(time).getValue(Activity.class).getActName();
                    aInfo.setActName(thisname);
                    //display all the information
                    name = (TextView) MAP.get(time);
                    name.setText(thisname);
                } catch (NullPointerException e) {

                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void appointmentbutton(View view) {
        Intent intent = new Intent(Date.this, Explore.class);
        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void actInfo(View view) {
        TextView thistext = (TextView)findViewById(view.getId());
       // String id = thistext.toString();
       final String time = getTime(thistext);
      //  toastMessage( +"this is" + time);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("user").child("users").child(userID).child("Activities").child(display);
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(time).exists()) {
                    // run some code
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(Date.this, ActInfo.class);
                    //Add your data to bundle
                    bundle.putString("date",display);
                    bundle.putString("time",time);
                    //Add the bundle to the intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{toastMessage("No Activity!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public String getTime(TextView id){
        for(String key : MAP.keySet()) {
            TextView current = MAP.get(key);
            if (current.equals(id)) {
                return key;
            }
        }
        return null;
    }
}





