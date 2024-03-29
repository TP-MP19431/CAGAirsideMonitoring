package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import static com.example.cagairsidemonitoring.App.CHANNEL_1_ID;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CreateFlight extends AppCompatActivity {
    private static final String TAG = "CreateFlight";
    private static final String KEY_FLIGHT = "FlightNo";
    private static final String KEY_BAY = "Bay";
    private static final String KEY_ETA = "mETA";
    private static final String KEY_TYPE = "mType";

    private NotificationManagerCompat notificationManager;

    private EditText FlightNo, Bay, ETA, acType;
    private Button Create, Update;

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);

        notificationManager = NotificationManagerCompat.from(this);

        FlightNo = (EditText) findViewById(R.id.etFlightNo);
        Bay = (EditText) findViewById(R.id.etBay);
        acType = (EditText) findViewById(R.id.etType);
        ETA = (EditText) findViewById(R.id.etETA);

        ETA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateFlight.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ETA.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        Create = (Button)findViewById(R.id.btnCreation);
        Update = (Button)findViewById(R.id.btncUpdate);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CreateFlight.this, updateFlight.class);
                startActivity(intent);
            }
        });
    }

    public void saveFlight(View v){

        String FlightNum = FlightNo.getText().toString();
        String BayNum = Bay.getText().toString();
        String mType = acType.getText().toString();
        String mETA = ETA.getText().toString();

        Map<String, Object> flightEntry = new HashMap<>();
        flightEntry.put(KEY_FLIGHT, FlightNum);
        flightEntry.put(KEY_BAY, BayNum);
        flightEntry.put(KEY_ETA, mETA);
        flightEntry.put(KEY_TYPE, mType);


        db.collection("FlightEntry").document().set(flightEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateFlight.this, "Entry for flight is successfully added", Toast.LENGTH_SHORT).show();

                        Notification notification = new NotificationCompat.Builder(CreateFlight.this, CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.ic_one)
                                .setContentTitle("Flight details sent")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();

                        notificationManager.notify(1, notification);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateFlight.this, "No permission to modify flight entries. Please contact system administrator.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                    }
                });


        /*db = FirebaseFirestore.getInstance();

        FlightNo = (EditText)findViewById(R.id.etFlightNo);
        Bay = (EditText)findViewById(R.id.etBay);
        ETA = (EditText)findViewById(R.id.etETA);

        Create = (Button)findViewById(R.id.btnCreation);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FlightNum = FlightNo.getText().toString();
                String BayNo = Bay.getText().toString();
                String mETA = ETA.getText().toString();

                Map<String, Object> userMap = new HashMap<>();

                userMap.put("FlightNo", FlightNum);
                userMap.put("Bay", BayNo);
                userMap.put("ETA", mETA);

                db.collection("Flights").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateFlight.this, "FlightAdded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String error = e.getMessage();

                        Toast.makeText(CreateFlight.this, "Error: " + error, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

*/
}
}
