package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class updateFlight extends AppCompatActivity {

    private EditText flight;
    private EditText eta;
    private Button update;
    private FirebaseFirestore mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flight);

        flight = (EditText)findViewById(R.id.etUflight);
        eta = (EditText)findViewById(R.id.etUeta);
        update = (Button)findViewById(R.id.btnUpdate);
        mDatabase = FirebaseFirestore.getInstance();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateFlight = flight.getText().toString();
                String updateEta = eta.getText().toString();
                updateDocument(updateFlight, updateEta);
            }

            private void updateDocument(String flight, String eta) {

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
        });
    }
}
