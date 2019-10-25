package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;


public class CreateFlight extends AppCompatActivity {
    private static final String TAG = "CreateFlight";
    private static final String KEY_FLIGHT = "FlightNo";
    private static final String KEY_BAY = "Bay";
    private static final String KEY_ETA = "mETA";

    private EditText FlightNo, Bay, ETA;
    private Button Create;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flight);

        FlightNo = (EditText) findViewById(R.id.etFlightNo);
        Bay = (EditText) findViewById(R.id.etBay);
        ETA = (EditText) findViewById(R.id.etETA);
        Create = (Button)findViewById(R.id.btnCreation);

    }

    public void saveFlight(View v){

        String FlightNum = FlightNo.getText().toString();
        String BayNum = Bay.getText().toString();
        String mETA = ETA.getText().toString();

        Map<String, Object> flightEntry = new HashMap<>();
        flightEntry.put(KEY_FLIGHT, FlightNum);
        flightEntry.put(KEY_BAY, BayNum);
        flightEntry.put(KEY_ETA, mETA);


        db.collection("Flight Entry").document().set(flightEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateFlight.this, "Entry for flight is successfully added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateFlight.this, "Error", Toast.LENGTH_SHORT).show();
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
