package com.appkida.vehservicing.screens.user.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.BookingAdapter;
import com.appkida.vehservicing.adapter.ServiceHistoryAdapter;
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
 * Use the {@link ServiceHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private ServiceHistoryAdapter bookingAdapter;
    private List<BookingClass> bookingList;
    public ServiceHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceHistoryFragment newInstance(String param1, String param2) {
        ServiceHistoryFragment fragment = new ServiceHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_history, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the list to store bookings
        bookingList = new ArrayList<>();
        bookingAdapter = new ServiceHistoryAdapter(bookingList);
        recyclerView.setAdapter(bookingAdapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("bookings");
        // Initialize Firebase Database
        String id = retrieveId();
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
                    if (booking != null && booking.getUserId() != null && booking.getUserId().equals(id)) {
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

    private String retrieveId() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }
}