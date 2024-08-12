package com.appkida.vehservicing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;
import com.appkida.vehservicing.model.Facility;
import com.appkida.vehservicing.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private List<Service> servicesList;
    private Context context;

    public ServiceAdapter(List<Service> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = servicesList.get(position);

        // Set data to views
        holder.serviceNameTextView.setText(service.getName());
        int imageResourceId = service.getImageResourceId();

        // Fetch the Drawable using the image resource ID
        Drawable drawable = ContextCompat.getDrawable(context, imageResourceId);

        // Set the Drawable to the ImageView
        if (drawable != null) {
            holder.serviceImageView.setImageDrawable(drawable);
        }
        // You may load images here using an image loading library like Picasso or Glide

        // Set background color based on selection state
        holder.itemView.setBackgroundColor(service.isSelected() ? Color.GRAY : Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    // Add these methods in your adapters
    public void clearSelections() {
        for (Service service : servicesList) {
            service.setSelected(false);
        }
        notifyDataSetChanged();
    }



    public List<Service> getSelectedServices() {
        List<Service> selectedServices = new ArrayList<>();

        for (Service service : servicesList) {
            if (service.isSelected()) {
                selectedServices.add(service);
            }
        }

        return selectedServices;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;
        ImageView serviceImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            serviceImageView = itemView.findViewById(R.id.serviceImageView);

            // Handle item click
            itemView.setOnClickListener(v -> {
                toggleSelectionState(getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            });
        }

        private void toggleSelectionState(int position) {
            Service service = servicesList.get(position);
            service.setSelected(!service.isSelected());
        }
    }
}
