package com.example.cagairsidemonitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;

public class flightsAdapter extends FirestoreRecyclerAdapter <Flights, flightsAdapter.flightMyHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public flightsAdapter(@NonNull FirestoreRecyclerOptions<Flights> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull flightMyHolder holder, int position, @NonNull Flights model) {
        holder.flight.setText(model.getFlightNo());
        holder.eta.setText("ETA: " + model.getmETA());
        holder.bay.setText("Bay: " + model.getBay());
        holder.acType.setText("A/C Type: " +model.getmType());

        String documentId = getSnapshots().getSnapshot(position).getId();


    }

    @NonNull
    @Override
    public flightMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);


        return new flightMyHolder(v);

    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();

    }


    class flightMyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView flight, bay, eta, acType;

        public flightMyHolder(@NonNull View itemView) {
            super(itemView);

            flight = itemView.findViewById(R.id.tvFlight);
            eta = itemView.findViewById(R.id.tvEta);
            bay = itemView.findViewById(R.id.tvBay);
            acType = itemView.findViewById(R.id.tvType);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }



}
