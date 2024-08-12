package com.appkida.vehservicing.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.BookingClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class ServiceHistoryAdapter extends  RecyclerView.Adapter<com.appkida.vehservicing.adapter.ServiceHistoryAdapter.ViewHolder> {

    private List<BookingClass> bookings;
    private List<String> statusOptions;

    public ServiceHistoryAdapter(List<BookingClass> bookings) {
        this.bookings = bookings;
        this.statusOptions = Arrays.asList("Pending", "Confirmed", "Completed");
    }

    @NonNull
    @Override
    public com.appkida.vehservicing.adapter.ServiceHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new com.appkida.vehservicing.adapter.ServiceHistoryAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull com.appkida.vehservicing.adapter.ServiceHistoryAdapter.ViewHolder holder, int position) {
        BookingClass booking = bookings.get(position);

        holder.textViewSelectedDate.setText("Selected Date: " + booking.getDate());
        holder.textViewSelectedTime.setText("Selected Time: " + booking.getTime());
        holder.custname.setText("Customer Name: " + booking.getCustomername());
        holder.type.setText("Service Type: " + booking.getServicetype());
        holder.status.setText(booking.getStatus());


    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSelectedDate;
        public TextView textViewSelectedTime;
        public TextView custname;
        public TextView type;
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSelectedDate = itemView.findViewById(R.id.textViewSelectedDate);
            textViewSelectedTime = itemView.findViewById(R.id.textViewSelectedTime);
            custname = itemView.findViewById(R.id.nameofcust);
            type = itemView.findViewById(R.id.nameofservice);
            status = itemView.findViewById(R.id.statusTextView);

        }
    }

}
