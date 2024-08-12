package com.appkida.vehservicing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.ServicingCenter;
import com.appkida.vehservicing.screens.user.fragments.BookingPage;
import com.appkida.vehservicing.screens.user.fragments.ServiceCenterDesc;

import java.util.List;

public class ServicingCenterAdapter extends RecyclerView.Adapter<ServicingCenterAdapter.ViewHolder> {

    private List<ServicingCenter> servicingCentersList;
    private FragmentManager fragmentManager;  // Add this field

    Context context;

    public ServicingCenterAdapter(List<ServicingCenter> servicingCentersList, FragmentManager fragmentManager) {
        this.servicingCentersList = servicingCentersList;
        this.context = context;
        this.fragmentManager = fragmentManager;  // Initialize the field

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicess, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ServicingCenter servicingCenter = servicingCentersList.get(position);

        holder.serviceNameTextView.setText(servicingCenter.getServices().get(position));
        holder.serviceAddressTextView.setText(servicingCenter.getServiceName());

        if(servicingCenter.getServices().get(position).equals("Oil Change")){


            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.oil_change;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);
            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        } else if (servicingCenter.getServices().get(position).equals("Brake Inspection")) {
            int imageResourceId = 1;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.brake;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        }else if(servicingCenter.getServices().get(position).equals("Tire Rotation")){
            int imageResourceId = 2;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.tire_rotation;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        }else if(servicingCenter.getServices().get(position).equals("Engine Diagnostics")){
            int imageResourceId = 3;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.engine;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        }else if(servicingCenter.getServices().get(position).equals("Transmission Service")){
            int imageResourceId = 4;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.tire_rotation;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        }else{
            int imageResourceId = 5;

            // Fetch the Drawable using the image resource ID
            int resourceId = R.drawable.battery;
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            // Set the Drawable to the ImageView
            if (drawable != null) {
                holder.mg.setImageDrawable(drawable);
            }
        }
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the corresponding ServicingCenter object
                ServicingCenter clickedItem = servicingCentersList.get(position);

                // Check if the clickedItem is not null and retrieve the name
                if (clickedItem != null) {
                    String itemName = clickedItem.getServices().get(position);
                    String centerName=clickedItem.getServiceName();
                    String address=clickedItem.getAddress();
                    String email=clickedItem.getEmail();
                    openNewFragment(itemName, centerName, address,email);

                }
            }
        });

    }

    private void openNewFragment(String itemName, String centerName, String address,String email) {
        // Create a new instance of your fragment with arguments
        BookingPage yourFragment = BookingPage.newInstance(itemName, centerName, address,email);

        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, yourFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public int getItemCount() {
        return servicingCentersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;
        TextView serviceAddressTextView;
        Button add;
        ImageView mg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            serviceAddressTextView = itemView.findViewById(R.id.serviceAddressTextView);
            mg=itemView.findViewById(R.id.imgg);
            add=itemView.findViewById(R.id.book);
            // Initialize other views here
        }
    }
}

