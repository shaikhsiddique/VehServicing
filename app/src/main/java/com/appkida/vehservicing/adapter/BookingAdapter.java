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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

// Import statements

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<BookingClass> bookings;
    private List<String> statusOptions;

    public BookingAdapter(List<BookingClass> bookings) {
        this.bookings = bookings;
        this.statusOptions = Arrays.asList("Pending", "Confirmed", "Completed");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingClass booking = bookings.get(position);

        holder.textViewSelectedDate.setText("Selected Date: " + booking.getDate());
        holder.textViewSelectedTime.setText("Selected Time: " + booking.getTime());
        holder.custname.setText("Customer Name: " + booking.getCustomername());
        holder.type.setText("Service Type: " + booking.getServicetype());

        // Set up the status spinner
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.statusSpinner.setAdapter(statusAdapter);

        // Set the selected status
        int statusIndex = statusOptions.indexOf(booking.getStatus());
        holder.statusSpinner.setSelection(statusIndex);

        // Set the item selected listener for the status spinner
        holder.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedStatus = statusOptions.get(position);
                // Update the status in your BookingClass
                booking.setStatus(selectedStatus);
                // Show the Update button
                holder.updateButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Set up the click listener for the Update button
        // Inside ViewHolder's updateButton click listener
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the status in the real-time database
                updateStatusForBooking(booking.getBookingId(), booking.getStatus());

                // You may want to hide the Update button after updating
                holder.updateButton.setVisibility(View.GONE);
            }
        });

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
        public Spinner statusSpinner;
        public Button updateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSelectedDate = itemView.findViewById(R.id.textViewSelectedDate);
            textViewSelectedTime = itemView.findViewById(R.id.textViewSelectedTime);
            custname = itemView.findViewById(R.id.nameofcust);
            type = itemView.findViewById(R.id.nameofservice);
            statusSpinner = itemView.findViewById(R.id.statusSpinner);
            updateButton = itemView.findViewById(R.id.updateButton);
        }
    }

    private void updateStatusForBooking(String customerName, String newStatus) {
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");

        // Perform a query to find the booking based on the customer name
        bookingsRef.orderByChild("bookingId").equalTo(customerName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the booking key dynamically
                    String bookingKey = snapshot.getKey();

                    if (bookingKey != null) {
                        // Update the status directly using the dynamically obtained booking key
                        updateStatusInDatabase(bookingKey, newStatus);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("err",databaseError.getMessage());
                // Handle errors
            }
        });
    }

    private void updateStatusInDatabase(String bookingKey, String newStatus) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookings").child(bookingKey);

        // Update the status directly
        databaseReference.child("status").setValue(newStatus);
    }

}