package com.example.adventureplanner.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adventureplanner.Classes.items;
import com.example.adventureplanner.R;

import java.util.ArrayList;
import java.util.List;

public class PopularTripsAdapter extends RecyclerView.Adapter<PopularTripsAdapter.itemViewHolder>
{
    private List<items> itemList = new ArrayList<>();

    public void setItems(List<items> items) {
        itemList.clear();
        itemList.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new itemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularTripsAdapter.itemViewHolder holder, int position) {
        items item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView emailTextView;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.nameTextView);
        }
        public void bind(items item) {
            nameTextView.setText(item.getName());
            emailTextView.setText(item.getEmail());

            // Bind other fields as needed
        }
    }
}
