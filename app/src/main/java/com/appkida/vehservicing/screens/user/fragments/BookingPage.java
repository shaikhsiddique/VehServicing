package com.appkida.vehservicing.screens.user.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.BookingClass;
import com.appkida.vehservicing.model.ServicingCenter;
import com.appkida.vehservicing.screens.user.UserMainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingPage extends Fragment {
    String selectedTime,selectedDate,emailid;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editTextDate;
    private DatePicker datePicker;

    private EditText editTextTime;
    private TimePicker timePicker;
    String itemName,centerName,address;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sharedPreferences;
    private static final String PREF_EMAIL = "user_name";

    public BookingPage() {
        // Required empty public constructor
    }

    public static BookingPage newInstance(String itemName) {
        BookingPage fragment = new BookingPage();
        Bundle args = new Bundle();
        args.putString("itemName", itemName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingPage.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingPage newInstance(String param1, String param2) {
        BookingPage fragment = new BookingPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static BookingPage newInstance(String itemName, String centerName, String address,String email) {
        BookingPage fragment = new BookingPage();
        Bundle args = new Bundle();
        args.putString("itemName", itemName);
        args.putString("centerName", centerName);
        args.putString("address", address);
        args.putString("email", email);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_page, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("bookings");
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Bundle args = getArguments();
        if (args != null) {
            itemName = args.getString("itemName", "");
             centerName = args.getString("centerName", "");
             address = args.getString("address", "");
            emailid = args.getString("email", "");


            // Now you have itemName, centerName, and address to use in your fragment
        }

        editTextDate = view.findViewById(R.id.editTextDate);
        datePicker = view.findViewById(R.id.datePicker);
        editTextTime = view.findViewById(R.id.editTextTime);
        timePicker = view.findViewById(R.id.timePicker);

        Button booking=view.findViewById(R.id.booking);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookapointment();
            }
        });
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        ImageView im = view.findViewById(R.id.image);
        if (itemName.equals("Oil Change")) {


            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.oil_change;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);
            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
        } else if (itemName.equals("Brake Inspection")) {
            int imageResourceId = 1;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.brake;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
        } else if (itemName.equals("Tire Rotation")) {
            int imageResourceId = 2;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.tire_rotation;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
        } else if (itemName.equals("Engine Diagnostics")) {
            int imageResourceId = 3;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.engine;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
        } else if (itemName.equals("Transmission Service")) {
            int imageResourceId = 4;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.tire_rotation;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
        } else {
            int imageResourceId = 5;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.battery;
            Drawable drawable = ContextCompat.getDrawable(getContext(), resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                im.setImageDrawable(drawable);
            }
            return view;

        }
    return view;}

    private void bookapointment() {
        String userEmail = retrieveEmailFromSharedPreferences();
        String bookingId = UUID.randomUUID().toString();
        String userId = retrieveuserId();
        // If no image is selected, proceed without uploading an image
        BookingClass booking = new BookingClass(
                bookingId,
                selectedDate,
                editTextTime.getText().toString(),
                itemName,
                userEmail,
                address,
                centerName,
                emailid,
                userId, // Assuming userId is the correct parameter for customername
                "pending"
        );

        // Save the ServicingCenter object to the Realtime Database
        databaseReference.push().setValue(booking);

        // Start the new activity after submitting data
        Intent intent = new Intent(getActivity(), UserMainActivity.class);
        startActivity(intent);
    }

    private String getUserEmail() {
        // Retrieve the user's email from SharedPreferences
        return sharedPreferences.getString(PREF_EMAIL, "");
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTime = convertTo12HourFormat(hourOfDay, minute);
                        editTextTime.setText(selectedTime);
                    }
                },
                hour, minute, false); // Set the last parameter to false for 24-hour format

        timePickerDialog.show();
    }

    private String convertTo12HourFormat(int hourOfDay, int minute) {
        String am_pm;
        int hour = hourOfDay;

        if (hourOfDay >= 12) {
            am_pm = "PM";
            if (hourOfDay > 12) {
                hour = hourOfDay - 12;
            }
        } else {
            am_pm = "AM";
            if (hourOfDay == 0) {
                hour = 12;
            }
        }

        return String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, am_pm);
    }
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    private String retrieveEmailFromSharedPreferences() {
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userEmail", "");
    }

    private String retrieveuserId() {
        SharedPreferences sharedPreferences =requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }


}






