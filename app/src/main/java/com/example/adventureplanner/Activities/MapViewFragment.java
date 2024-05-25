package com.example.adventureplanner.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.adventureplanner.Classes.place;
import com.example.adventureplanner.Classes.rootdetail;
import com.example.adventureplanner.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapViewFragment extends Fragment implements GoogleMap.OnMapClickListener {
    ArrayList<route> arrayList = new ArrayList<>();
    ArrayList<LatLng> markerPositions = new ArrayList<>();
    private Marker selectedMarker;
    private ImageView imageView;
    private TextView tvSelectImage;
    private Uri selectedImageUri;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
   private List<LatLng> polylinePoints = new ArrayList<>(); // List to store clicked points
    Button savebtn,clearbtn;
    private RadioGroup radioGroup;
    TextView tripnametxt;
    private List<rootdetail> Rootdetail ;
    private List<place> places;
    //private List<Marker> markerList = new ArrayList<>();  // List to store markers


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);

        savebtn = view.findViewById(R.id.savebtn);
        clearbtn = view.findViewById(R.id.mapclearbtn);
        radioGroup = view.findViewById(R.id.radiogroup);
        tripnametxt = view.findViewById(R.id.tripnametxt);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this::onMapReady);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult)
            {
                if (locationResult == null)
                {
                    return;
                }
                for (Location location : locationResult.getLocations())
                {
                    updateLocationOnMap(location);
                }
            }
        };

        savebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        return view;
    }

    @SuppressLint("SuspiciousIndentation")
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        mMap.setOnMapClickListener(this);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        mMap.setMyLocationEnabled(true);
        startLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    mMap.setMyLocationEnabled(true);
                    startLocationUpdates();
                }
            }
            else
            {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void updateLocationOnMap(Location location)
    {
        if (mMap != null)
        {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng)
    {
        if (radioGroup.getCheckedRadioButtonId() == R.id.addplaceRB)
        {
            // Add marker on map click for option 1
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker =mMap.addMarker(markerOptions);
            selectedMarker = marker;
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        } else
        {
            // Add point to polyline for option 2
            polylinePoints.add(latLng);
            drawPolyline();
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker)
            {


                // Return the custom info window view
                return null;
            }

            @Override
            public View getInfoWindow(Marker marker)
            {
                showDialog(getContext(), marker.getPosition());
                return null;
            }
        });
    }
    private void drawPolyline()
    {
        if (polylinePoints.isEmpty())
        {
            return; // Don't draw if there are no points
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(polylinePoints);
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);

        mMap.addPolyline(polylineOptions);
    }
    public class route
    {
        float latitude, longitude;
        public route(float latitude, float longitude)
        {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        public double getLatitude()
        {
            return latitude;
        }

        public double getLongitude()
        {
            return longitude;
        }
    }
    public void showDialog(final Context context, final LatLng latLng)
    {
        // Check if latLng is not null
        if (latLng != null)
        {
            // Create a dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // Get the layout inflater
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View dialogView = inflater.inflate(R.layout.info_window_layout, null);

            // Find views in the custom layout
            TextView textView = dialogView.findViewById(R.id.abc);
            tvSelectImage = dialogView.findViewById(R.id.tvSelectImage);
            imageView = dialogView.findViewById(R.id.imageview);
            Button addButton = dialogView.findViewById(R.id.addbutton);

            // Customize views as needed
            textView.setText(latLng.toString());
            tvSelectImage.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 101);
                }
            });


            // Create the dialog
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();

            // Set click listener for the button
            addButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    dialog.dismiss();
                    if (selectedImageUri != null)
                    {
                        LatLng markerPosition = latLng;

                        uploadImageToGoogleMaps(selectedImageUri, markerPosition);
                    } else
                    {
                        tvSelectImage.setVisibility(View.GONE);
                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Show the dialog
            dialog.show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK)
        {
            // Get selected image URI
           selectedImageUri = data.getData();

            // Get marker position
            LatLng markerPosition = selectedMarker.getPosition();

            // Display selected image in the imageView
            imageView.setImageURI(selectedImageUri);

        }
    }
    private void uploadImageToGoogleMaps(Uri imageUri, LatLng markerPosition)
    {
        // Add a marker to the map at the specified position
        MarkerOptions markerOptions = new MarkerOptions().position(markerPosition);
        mMap.addMarker(markerOptions);

        // Load the image from the URI
        try
        {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

            // Resize the bitmap if needed
            int maxSize = 100;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scale = (float) maxSize / Math.max(width, height);
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

            // Create a BitmapDescriptor from the resized bitmap
            BitmapDescriptor imageDescriptor = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

            // Add the marker with the image descriptor to the map
            mMap.addMarker(new MarkerOptions().position(markerPosition).icon(imageDescriptor));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // Handle error loading image
        }
    }


}