package com.example.application.layout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PersonalAdd extends AppCompatActivity {
    public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    EditText displayhere;
    EditText timehere;
    EditText remarkshere;
    EditText namehere;
    Button press;
    DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        displayhere = (EditText) findViewById(R.id.dateselect1);
        timehere = (EditText) findViewById(R.id.timeselect1);
        press = (Button) findViewById(R.id.addbutton);
        remarkshere = (EditText) findViewById(R.id.remarks);
        namehere = (EditText) findViewById(R.id.activityname);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("user");
        displayhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PersonalAdd.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                displayhere.setText(dayOfMonth + MONTHS[monthOfYear]+ year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        timehere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
               int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PersonalAdd.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute == 0) {
                            timehere.setText(selectedHour + ":00");
                        } else if (selectedMinute < 10) {
                            timehere.setText(selectedHour + ":0" + selectedMinute);
                        } else {
                            timehere.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

            }
        });

    }

    public void onClick(View v) {
        AddActivity(namehere.getText().toString(), displayhere.getText().toString(), timehere.getText().toString(),
                remarkshere.getText().toString());
    }

    private void AddActivity(String name, final String date, final String time, String remarks) {
        final FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("user").child("users").child(user.getUid()).child("Activities");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(date).child(time).exists()) {
                    // run some code
                    toastMessage("clash");
                }else {
                    writeActivity(user.getUid());
                    Bundle bundle = new Bundle();
                    bundle.putString("date", displayhere.getText().toString());
                    namehere.setText(null);
                    displayhere.setText(null);
                    timehere.setText(null);
                    remarkshere.setText(null);
                    toastMessage("Added");
                    Intent intent = new Intent(PersonalAdd.this, Date.class);
                    //Add your data to bundle

                    //Add the bundle to the intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void writeActivity(String userId){
        String name = namehere.getText().toString();
        String date = displayhere.getText().toString();
        String time= timehere.getText().toString();
        String remarks = remarkshere.getText().toString();
        Activity Activity = new Activity(name,date,time,remarks);

        mDatabase.child("users").child(userId).child("Activities").child(date).child(time).setValue(Activity);

    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

class Activity {

    public String name;
    public String date;
    public String time;
    public String remarks;

    public Activity() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Activity(String name, String date,String time, String remarks) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.remarks = remarks;

    }
    public String getActName() {
        return name;
    }
    public void setActName(String name) {
        this.name= name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}

