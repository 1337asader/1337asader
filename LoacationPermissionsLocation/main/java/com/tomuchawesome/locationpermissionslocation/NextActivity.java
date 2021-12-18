package com.tomuchawesome.locationpermissionslocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NextActivity extends AppCompatActivity {

    TextView longitude, latitude, address;
    Button updateLocBtn, convertAddressBtn;
    double longitudeNum;
    double latitudeNum;


    Geocoder geocoder;
    List<Address> addresses;


    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        longitude = findViewById(R.id.longitudeTextView);
        latitude = findViewById(R.id.latitudeTextView);
        address = findViewById(R.id.addressTextView);
        updateLocBtn = findViewById( R.id.updateLocBtn);
        convertAddressBtn = findViewById(R.id.convertAddressBtn);

        geocoder = new Geocoder(this, Locale.getDefault());


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(NextActivity.this , Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Location Permission Granted in Next Activity" , Toast.LENGTH_SHORT).show();


            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location == null){
                        latitude.setText("location is not on: ");
                        longitude.setText("location is not on");
                        requestLocationData();
                    } else {
                        latitude.setText("latitude: "+location.getLatitude());
                        longitude.setText("longitude: "+location.getLongitude());
                        latitudeNum = location.getLatitude();
                        longitudeNum = location.getLongitude();
                    }
                }
            });
        } else {
            longitude.setText("location permission ");
            latitude.setText("was not granted");
        }


        updateLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //hmmm for some reason i can only use this btn once

                requestLocationData();
            }
        });

        convertAddressBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    addresses = geocoder.getFromLocation( latitudeNum, longitudeNum, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String streetAddress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL


                address.setText(streetAddress +"\n"+city +", "+state+ ", " + country + "\n"+postalCode);





            }
        }));




    }

    @SuppressLint("MissingPermission")
    private void requestLocationData(){
        Toast.makeText(this, "running: requestLocalData()" , Toast.LENGTH_LONG).show();


        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // dont know why or even "if" i need these
            // readiung thing like setInterval makes me think this is for updating the gps as location is changeing


        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(5);

        // so this guy seems to bee the one impeeding the update location btn
            // if i can get that btn working it'll save battery usage with the app
            //if im only requesting location on occation insteed of all the fliping time
        //locationRequest.setNumUpdates(11);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



    }

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult( LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitude.setText("Longitude: " + mLastLocation.getLongitude() + "");
            latitudeNum = mLastLocation.getLatitude();
            longitudeNum = mLastLocation.getLongitude();
        }
    };



}