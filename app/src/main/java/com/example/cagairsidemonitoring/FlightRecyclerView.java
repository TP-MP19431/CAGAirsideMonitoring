package com.example.cagairsidemonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.cagairsidemonitoring.App.CHANNEL_1_ID;
import static com.example.cagairsidemonitoring.App.CHANNEL_2_ID;

public class FlightRecyclerView extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference flightRef = db.collection("FlightEntry");
    private flightsAdapter adapter;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_recycler_view);
        notificationManager = NotificationManagerCompat.from(this);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query = flightRef.orderBy("mETA", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<Flights> options = new FirestoreRecyclerOptions.Builder<Flights>()
                .setQuery(query, Flights.class)
                .build();

        adapter = new flightsAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.rvFlights);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    adapter.deleteItem(viewHolder.getAdapterPosition());
                }

                if (direction == ItemTouchHelper.LEFT){

                    String uid = FirebaseAuth.getInstance().getUid();

                    Intent intent = new Intent (FlightRecyclerView.this, updateFlight.class);
                    startActivity(intent);
                }

                }
        }).attachToRecyclerView(recyclerView);





    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        flightRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String flight = documentSnapshot.getString("FlightNo");
                    String bay = documentSnapshot.getString("Bay");
                    String eta = documentSnapshot.getString("mETA");

                    switch(dc.getType()){
                        case ADDED:
                           // Toast.makeText(FlightRecyclerView.this, "New flight has been added", Toast.LENGTH_SHORT).show();

                            Notification notification = new NotificationCompat.Builder(FlightRecyclerView.this, CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.ic_plane)
                                    .setContentTitle("New remote bay flight " + flight )
                                    .setContentText("is arriving at " + eta + " at bay " + bay)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, notification);

                            break;

                        case MODIFIED:
                           // Toast.makeText(FlightRecyclerView.this, "A flight has been updated", Toast.LENGTH_SHORT).show();

                            Notification notificationUpdate = new NotificationCompat.Builder(FlightRecyclerView.this, CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.ic_plane)
                                    .setContentTitle("Updated Flight " + flight)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, notificationUpdate);

                            break;

                        case REMOVED:
                           // Toast.makeText(FlightRecyclerView.this, "A flight has been deleted", Toast.LENGTH_SHORT).show();

                            Notification notificationDelete = new NotificationCompat.Builder(FlightRecyclerView.this, CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.ic_delete)
                                    .setContentTitle("Deleted Flight " + flight)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(1, notificationDelete);

                            break;
                    }

                }
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

}
