package com.appkida.vehservicing.screens.user.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.adapter.ServiceCenterAdapter;
import com.appkida.vehservicing.adapter.SliderAdapter;
import com.appkida.vehservicing.model.ServicingCenter;
import com.appkida.vehservicing.model.SliderData;
import com.appkida.vehservicing.screens.user.ProfilePage;
import com.appkida.vehservicing.screens.user.UserMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuhart.stepview.StepView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigation;

    TextView noDataTextView;
    private ServiceCenterAdapter adapter;
    DatabaseReference specificServiceCenterRef;
    // Urls of our images.
    String url1 = "https://firebasestorage.googleapis.com/v0/b/vehservicing.appspot.com/o/Offers%2Fadd_1.png?alt=media&token=af0187e9-64dd-4d43-a68b-5cdaee038e20";
    String url2 = "https://firebasestorage.googleapis.com/v0/b/vehservicing.appspot.com/o/Offers%2Fadd_2.png?alt=media&token=0b1d368b-379b-4c38-87e5-6c118e04fb2a";
    String url3 = "https://firebasestorage.googleapis.com/v0/b/vehservicing.appspot.com/o/Offers%2Fadd_3.jpg?alt=media&token=47d77aec-650e-425b-8adf-2503e9820c0b";
    List<ServicingCenter> servicingCenters;
    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_home, container, false);

        com.shuhart.stepview.StepView stepView=view.findViewById(R.id.step_view);
        List<String> steps = Arrays.asList("First step", "Second step", "Third step");
        stepView.setSteps(steps);

        stepView.setStepsNumber(4);
        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.bottom_nav_icon_color))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.bottom_nav_icon_color))
                .steps(steps)
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapters = new SliderAdapter(getContext(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapters);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();


        noDataTextView = view.findViewById(R.id.nodatatext);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        specificServiceCenterRef = databaseReference.child("service_centers");

        recyclerView = view.findViewById(R.id.recyclerView);
        // Set up GridLayoutManager with span count 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                // Return 2 for the first two items, 1 for the rest
//                return (position == 0 || position == 1) ? 2 : 1;
//            }
//        });
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ServiceCenterAdapter(new ArrayList<>(), getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);


        // SearchView setup
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResults(newText);
                return true;
            }
        });

        specificServiceCenterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    noDataTextView.setVisibility(View.GONE);
                    populateRecyclerView(snapshot);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Error: " + error.getMessage());
            }
        });
        return view;
    }

    private void populateRecyclerView(DataSnapshot snapshot) {
        List<ServicingCenter> servicingCenters = new ArrayList<>();

        for (DataSnapshot data : snapshot.getChildren()) {
            ServicingCenter servicingCenter = data.getValue(ServicingCenter.class);
            if (servicingCenter != null) {
                servicingCenters.add(servicingCenter);
            }
        }

        adapter.updateData(servicingCenters);
        recyclerView.setVisibility(View.VISIBLE);
    }


    private void filterResults(String query) {
        if (TextUtils.isEmpty(query)) {
            // If the query is empty, show all results
            specificServiceCenterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        noDataTextView.setVisibility(View.GONE);
                        populateRecyclerView(snapshot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseData", "Error: " + error.getMessage());
                }
            });
        } else {
            // If there's a query, filter the results
            specificServiceCenterRef.orderByChild("address").startAt(query).endAt(query + "\uf8ff")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                noDataTextView.setVisibility(View.GONE);
                                populateRecyclerView(snapshot);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                noDataTextView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseData", "Error: " + error.getMessage());
                        }
                    });
        }
    }


}

