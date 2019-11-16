package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class updateFlight extends AppCompatActivity {

    private EditText flight;
    private EditText newEta;
    private EditText newBay;
    private Button update, search;
    private FirebaseFirestore mDatabase;

    private TextView flightData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flightRef = db.collection("FlightEntry");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flight);

        flight = (EditText)findViewById(R.id.etUflight);
        search = (Button)findViewById(R.id.btnSearch);
        flightData = (TextView)findViewById(R.id.textView5);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updateFlight = flight.getText().toString();

                flightRef.whereEqualTo("FlightNo",  updateFlight)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                                {
                                    Flights flights = documentSnapshot.toObject(Flights.class);

                                    String flight = flights.getFlightNo();
                                    String eta = flights.getmETA();
                                    String bay = flights.getBay();


                                    System.out.println(eta);
                                    System.out.println(bay);

                                    flightData.setText("Flight details for "+ flight + "\nCurrent bay: " + bay + "\nCurrent ETA: " + eta);

                                }
                            }
                        });
            }
        });

       /* flight = (EditText)findViewById(R.id.etUflight);
        newEta = (EditText)findViewById(R.id.etUeta);
        update = (Button)findViewById(R.id.btnUpdate);
        mDatabase = FirebaseFirestore.getInstance();

        final String uid = FirebaseAuth.getInstance().getUid();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateFlight = flight.getText().toString();
                String updateEta = newEta.getText().toString();
                updateDocument(updateFlight, updateEta);
            }

            private void updateDocument(String flight, String eta) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();



                DocumentReference documentReference = mDatabase.collection("Flight Entry").document("B8UUt0Xn1cmSOtjL7cwV");
                documentReference.update("FlightNo", flight);
                documentReference.update("mETA",eta)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(updateFlight.this,"Flight Updated", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(updateFlight.this,"Error",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });*/
    }
}
