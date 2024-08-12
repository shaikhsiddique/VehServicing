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

import java.util.ArrayList;
import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {

    private List<Facility> facilitiesList;
    private Context context;

    public FacilitiesAdapter(List<Facility> facilitiesList) {
        this.facilitiesList = facilitiesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Facility facility = facilitiesList.get(position);

        // Set data to views
        holder.facilityNameTextView.setText(facility.getName());

        int imageResourceId = facility.getImageResourceId();

        // Fetch the Drawable using the image resource ID
        Drawable drawable = ContextCompat.getDrawable(context, imageResourceId);

        // Set the Drawable to the ImageView
        if (drawable != null) {
            holder.facilityImageView.setImageDrawable(drawable);
        }
        holder.itemView.setBackgroundColor(facility.isSelected() ? Color.GRAY : Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return facilitiesList.size();
    }

    // Add these methods in your adapters
    public void clearSelections() {
        for (Facility facility : facilitiesList) {
            facility.setSelected(false);
        }
        notifyDataSetChanged();
    }



    public List<Facility> getSelectedFacilities() {
        List<Facility> selectedFacilities = new ArrayList<>();

        for (Facility facility : facilitiesList) {
            if (facility.isSelected()) {
                selectedFacilities.add(facility);
            }
        }

        return selectedFacilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView facilityNameTextView;
        ImageView facilityImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            facilityNameTextView = itemView.findViewById(R.id.facilityNameTextView);
            facilityImageView = itemView.findViewById(R.id.facilityImageView);

            // Handle item click
            itemView.setOnClickListener(v -> {
                toggleSelectionState(getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            });
        }

        private void toggleSelectionState(int position) {
            Facility facility = facilitiesList.get(position);
            facility.setSelected(!facility.isSelected());
        }
    }
}
