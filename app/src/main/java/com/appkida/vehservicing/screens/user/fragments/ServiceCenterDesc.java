package com.appkida.vehservicing.screens.user.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.ServicingCenterAdapter;
import com.appkida.vehservicing.model.Service;
import com.appkida.vehservicing.model.ServicingCenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceCenterDesc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceCenterDesc extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference specificServiceCenterRef;
    private DatabaseReference databaseReference;


    public ServiceCenterDesc() {
        // Required empty public constructor
    }

    public static ServiceCenterDesc newInstance(String itemName) {
        ServiceCenterDesc fragment = new ServiceCenterDesc();
        Bundle args = new Bundle();
        args.putString("itemName", itemName);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ServiceCenterDesc.
     */
    // TODO: Rename and change types and number of parameters


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
        View view = inflater.inflate(R.layout.fragment_service_center_desc, container, false);

        // Access the argument
        String itemName = getArguments().getString("itemName");
        databaseReference = FirebaseDatabase.getInstance().getReference();

        specificServiceCenterRef = databaseReference.child("service_centers");
        TextView adde=view.findViewById(R.id.add);
        TextView name=view.findViewById(R.id.name);
        ImageView v=view.findViewById(R.id.servimg);

        RecyclerView recyclerView = view.findViewById(R.id.servicesRecyclerView);
        List<ServicingCenter> servicingCentersList = new ArrayList<>(); // Populate this list with your data
        ServicingCenterAdapter adapter = new ServicingCenterAdapter(servicingCentersList,getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);


        specificServiceCenterRef.orderByChild("address").startAt(itemName).endAt(itemName + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.d("exist","true");
                            for (DataSnapshot data : snapshot.getChildren()) {
                                ServicingCenter servicingCenter = data.getValue(ServicingCenter.class);
                                if (servicingCenter != null) {
                                    name.setText(servicingCenter.getServiceName());
                                    adde.setText(servicingCenter.getAddress());
                                    Picasso.get().load(servicingCenter.getImage_url()).into(v);
                                    servicingCentersList.add(servicingCenter);
                                 }
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.d("exist","false");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseData", "Error: " + error.getMessage());
                    }
                });
        // Use itemName as needed in your fragment

        return view;
    }
}