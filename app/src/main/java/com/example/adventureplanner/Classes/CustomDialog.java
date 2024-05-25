package com.example.adventureplanner.Classes;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adventureplanner.R;
import com.google.android.gms.maps.model.LatLng;


public class CustomDialog {
    public static void showDialog(final Context context, final LatLng latLng) {
        // Check if latLng is not null
        if (latLng != null) {
            // Create a dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Get the layout inflater
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View dialogView = inflater.inflate(R.layout.info_window_layout, null);

            // Find views in the custom layout
            TextView textView = dialogView.findViewById(R.id.abc);
            ImageView imageView = dialogView.findViewById(R.id.imageview);
            Button addButton = dialogView.findViewById(R.id.addbutton);

            // Customize views as needed
            textView.setText(latLng.toString());
           // Glide.with(context)
                 //   .load(imageUrl)
                   // .into(imageView);



            // Create the dialog
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();

            // Set click listener for the button
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click here
                    Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show();
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });

            // Show the dialog
            dialog.show();
        }
    }
}