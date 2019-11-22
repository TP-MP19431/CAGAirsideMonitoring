package com.example.cagairsidemonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Start extends AppCompatActivity {

    private Button geo;

    private EditText etSearchFlight;

    private TextView tvBayInfo;

    private String bus;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flightRef = db.collection("FlightEntry");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button finalSearch = findViewById(R.id.btnsSearch);
        //final Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

         etSearchFlight = (EditText)findViewById(R.id.etFlightNo);
         tvBayInfo = (TextView)findViewById(R.id.tvFinalBay);


        finalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchFlight = etSearchFlight.getText().toString();

                flightRef.whereEqualTo("FlightNo", searchFlight)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Flights flights = documentSnapshot.toObject(Flights.class);

                                    final String bay = flights.getBay();
                                    final String flight = flights.getFlightNo();
                                    final String documentId = documentSnapshot.getId();
                                    final String role = "bus";

                                   // System.out.println(bay);
                                    System.out.println(role);
                                    tvBayInfo.setText("Flight " + flight + "\nis at bay " + bay);

                                    //passes variable to another activity
                                    Intent intent = new Intent(Start.this, GeoFencing.class);
                                    intent.putExtra("docId", documentId);
                                    intent.putExtra("bay", bay);
                                    intent.putExtra("role", role);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        });


    }
}