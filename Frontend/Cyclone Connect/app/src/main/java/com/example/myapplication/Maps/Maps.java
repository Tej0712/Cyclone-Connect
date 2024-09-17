package com.example.myapplication.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SearchView searchView;
    private Button satelliteViewButton;
    private Button trafficViewButton;
    private Button threeDMapsButton;
    private Toolbar toolbar;

    // ISU campus bounds
    private static final LatLngBounds ISU_CAMPUS_BOUNDS = new LatLngBounds(
            new LatLng(42.0075, -93.6650), // Southwest corner
            new LatLng(42.0290, -93.6250)  // Northeast corner
    );

    // ISU landmarks
    private static final LatLng CAMPANILE = new LatLng(42.0255, -93.6465);
    private static final LatLng MEMORIAL_UNION = new LatLng(42.0230, -93.6460);
    private static final LatLng PARKS_LIBRARY = new LatLng(42.0275, -93.6500);
    private static final LatLng JACK_TRICE_STADIUM = new LatLng(42.0140, -93.6340);
    private static final LatLng HILTON_COLISEUM = new LatLng(42.0250, -93.6340);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(Maps.this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                } else {
                    Toast.makeText(Maps.this, "Location not found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        satelliteViewButton = findViewById(R.id.satellite_view_button);
        satelliteViewButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    satelliteViewButton.setText("Normal View");
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    satelliteViewButton.setText("Satellite View");
                }
            }
        });

        trafficViewButton = findViewById(R.id.traffic_view_button);
        trafficViewButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mMap.isTrafficEnabled()) {
                    mMap.setTrafficEnabled(false);
                    trafficViewButton.setText("Show Traffic");
                } else {
                    mMap.setTrafficEnabled(true);
                    trafficViewButton.setText("Hide Traffic");
                }
            }
        });

        threeDMapsButton = findViewById(R.id.three_d_maps_button);
        threeDMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.isBuildingsEnabled()) {
                    mMap.setBuildingsEnabled(false);
                    threeDMapsButton.setText("Enable 3D Maps");
                } else {
                    mMap.setBuildingsEnabled(true);
                    threeDMapsButton.setText("Disable 3D Maps");
                }
            }
        });

        FloatingActionButton myLocationToDestinationButton = findViewById(R.id.my_location_to_destination_button);
        myLocationToDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDirectionsDialog();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showDirectionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_directions, null);
        builder.setView(view);

        final EditText etFrom = view.findViewById(R.id.et_from);
        final EditText etTo = view.findViewById(R.id.et_to);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String fromLocation = etFrom.getText().toString().trim();
            String toLocation = etTo.getText().toString().trim();

            if (!fromLocation.isEmpty() && !toLocation.isEmpty()) {
                getDirections(fromLocation, toLocation);
            } else {
                Toast.makeText(Maps.this, "Please enter both locations", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getDirections(String fromLocation, String toLocation) {
        Geocoder geocoder = new Geocoder(Maps.this);
        List<Address> fromAddressList = null;
        List<Address> toAddressList = null;

        try {
            fromAddressList = geocoder.getFromLocationName(fromLocation, 1);
            toAddressList = geocoder.getFromLocationName(toLocation, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fromAddressList != null && !fromAddressList.isEmpty()
                && toAddressList != null && !toAddressList.isEmpty()) {
            Address fromAddress = fromAddressList.get(0);
            Address toAddress = toAddressList.get(0);

            LatLng fromLatLng = new LatLng(fromAddress.getLatitude(), fromAddress.getLongitude());
            LatLng toLatLng = new LatLng(toAddress.getLatitude(), toAddress.getLongitude());

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(fromLatLng).title(fromLocation));
            mMap.addMarker(new MarkerOptions().position(toLatLng).title(toLocation));

            // Draw a polyline between the locations
            List<LatLng> path = new ArrayList<>();
            path.add(fromLatLng);
            path.add(toLatLng);
            mMap.addPolyline(new PolylineOptions().addAll(path).color(Color.RED).width(5));

            // Adjust camera bounds to include both markers
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(fromLatLng);
            builder.include(toLatLng);
            LatLngBounds bounds = builder.build();
            int padding = 100; // Padding in pixels
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } else {
            Toast.makeText(Maps.this, "One or both locations not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Check permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        // Set the initial camera position to ISU campus bounds
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ISU_CAMPUS_BOUNDS.getCenter(), 15));

        // Add markers for ISU landmarks
        mMap.addMarker(new MarkerOptions().position(CAMPANILE).title("Campanile"));
        mMap.addMarker(new MarkerOptions().position(MEMORIAL_UNION).title("Memorial Union"));
        mMap.addMarker(new MarkerOptions().position(PARKS_LIBRARY).title("Parks Library"));
        mMap.addMarker(new MarkerOptions().position(JACK_TRICE_STADIUM).title("Jack Trice Stadium"));
        mMap.addMarker(new MarkerOptions().position(HILTON_COLISEUM).title("Hilton Coliseum"));

        // Set up a custom info window adapter
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(@NonNull com.google.android.gms.maps.model.Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(@NonNull com.google.android.gms.maps.model.Marker marker) {
                @SuppressLint("InflateParams") View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null, false);
                TextView title = infoWindow.findViewById(R.id.info_window_title);
                title.setText(marker.getTitle());
                return infoWindow;
            }
        });

        // Set up a marker click listener to show the info window
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull com.google.android.gms.maps.model.Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}