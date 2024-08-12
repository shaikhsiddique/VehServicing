package com.appkida.vehservicing.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.ServicingCenter;
import com.appkida.vehservicing.screens.user.fragments.ServiceCenterDesc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiceCenterAdapter extends RecyclerView.Adapter<ServiceCenterAdapter.ServiceCenterViewHolder> {

    private List<ServicingCenter> fullList;
    private List<ServicingCenter> filteredList;
    private FragmentManager fragmentManager;  // Add this field

    // Constructor
    // Constructor
    public ServiceCenterAdapter(List<ServicingCenter> list, FragmentManager fragmentManager) {
        this.fullList = list;
        this.filteredList = new ArrayList<>(list);
        this.fragmentManager = fragmentManager;  // Initialize the field
    }
    @NonNull
    @Override
    public ServiceCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_center, parent, false);
        return new ServiceCenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCenterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ServicingCenter servicingCenter = filteredList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the corresponding ServicingCenter object
                ServicingCenter clickedItem = filteredList.get(position);

                // Check if the clickedItem is not null and retrieve the name
                if (clickedItem != null) {
                    String itemName = clickedItem.getAddress();
                    openNewFragment(itemName);

                }
             }
        });
        // Bind data to views in the ViewHolder
        holder.nameTextView.setText(servicingCenter.getServiceName());
        holder.addressTextView.setText(servicingCenter.getAddress());
        // Set image if the URL is not null
        // Use FirebaseUI to load the image from the URL
        if (servicingCenter.getImage_url() != null) {
            String imageUrl = servicingCenter.getImage_url();

            // Load image into ImageView using FirebaseUI
            Picasso.get().load(imageUrl).into(holder.img);
        }       // Update other views as needed


    }

    private void openNewFragment(String itemName) {
        // Create a new instance of your fragment
        // Create a new instance of your fragment with arguments
        ServiceCenterDesc yourFragment = ServiceCenterDesc.newInstance(itemName);

        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, yourFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterList(List<ServicingCenter> filteredList) {
        this.filteredList = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }

    public List<ServicingCenter> getFullList() {
        return fullList;
    }

    public void updateData(List<ServicingCenter> newData) {
        fullList.clear();
        fullList.addAll(newData);
        filterList(fullList);
    }

    // ViewHolder class
    public static class ServiceCenterViewHolder extends RecyclerView.ViewHolder {
        // Define your ViewHolder views
        public TextView nameTextView;
        public TextView addressTextView;
        public ImageView img;

        public ServiceCenterViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your ViewHolder views
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            img=itemView.findViewById(R.id.imgof);
            // Initialize other views as needed
        }
    }
}
