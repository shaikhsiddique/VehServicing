package com.appkida.vehservicing.screens.admin.fragments;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.BookingAdapter;
import com.appkida.vehservicing.model.BookingClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingsShow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingsShow extends Fragment {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<BookingClass> bookingList;
    public BookingsShow() {
        // Required empty public constructor
    }
    public static BookingsShow newInstance(String param1, String param2) {
        BookingsShow fragment = new BookingsShow();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings_show, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the list to store bookings
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(bookingList);
        recyclerView.setAdapter(bookingAdapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("bookings");
        // Initialize Firebase Database
        String centerName = retrieveServiceNameFromSharedPreferences();
        // Somewhere in your code, you can retrieve the saved email
        String userEmail = retrieveEmailFromSharedPreferences();

// Now you can use the userEmail variable as needed

        if (userEmail.isEmpty()) {
            // Handle the case when the serviceName is not found or is empty
            // You might want to provide a default value or show an error message
            return view;
        }
        // Assuming your BookingClass has a field named centerName
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");

        // Retrieve all bookings from Firebase
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingList.clear(); // Clear existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookingClass booking = snapshot.getValue(BookingClass.class);
                    if (booking != null && booking.getEmail() != null && booking.getEmail().equals(userEmail)) {
                        // Filter bookings based on the email
                        bookingList.add(booking);
                    }
                }

                bookingAdapter.notifyDataSetChanged(); // Notify the adapter of the changes
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });


        return view;
    }
    private String retrieveEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userEmail", "");
    }

    private String retrieveServiceNameFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("serviceName", "");
    }
}