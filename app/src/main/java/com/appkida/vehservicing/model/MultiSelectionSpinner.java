package com.appkida.vehservicing.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appkida.vehservicing.R;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectionSpinner extends LinearLayout {

    private List<String> items;
    private List<Boolean> selected;

    private RecyclerView recyclerView;
    private MultiSelectionAdapter adapter;

    public MultiSelectionSpinner(Context context) {
        super(context);
        init(context);
    }

    public MultiSelectionSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.drawable.multi_selection_spinner, this, true);

        items = new ArrayList<>();
        selected = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MultiSelectionAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void setItems(List<String> items) {
        this.items = items;
        for (int i = 0; i < items.size(); i++) {
            selected.add(false);
        }
        adapter.notifyDataSetChanged();
    }

    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (selected.get(i)) {
                selectedItems.add(items.get(i));
            }
        }
        return selectedItems;
    }

    private class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.drawable.item_multi_selection, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.checkBox.setText(items.get(position));
            holder.checkBox.setChecked(selected.get(position));
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> selected.set(position, isChecked));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;

            public ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkBox);
            }
        }
    }

}
