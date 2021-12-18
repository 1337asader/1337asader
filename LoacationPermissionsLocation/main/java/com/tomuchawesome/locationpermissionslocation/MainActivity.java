package com.tomuchawesome.locationpermissionslocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    /*
    this is skeletal structure of getting gps permission, getting location and converting that location to an address

    next step is to scale this up so it will display a website if the plus code is held in a database
        if have a blue print database made and understand the basics of that
            so i need to build a test app that i can search the data base

        then we can make a new app that will merge this app and the data base app
            after that i belive i'll need to rebuild/build the/a front end
            then build a test data base with 30 different restaurants in 3 different zip codes
     */

    private static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 99;

    private Button nextActivityBtn;

    private boolean hasPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nextActivityBtn = findViewById(R.id.nextActivityBtn);

        String permission = Manifest.permission.ACCESS_FINE_LOCATION;

       // hasPermission = ContextCompat.checkSelfPermission(MainActivity.this,permission ) == PackageManager.PERMISSION_GRANTED;




        if (ContextCompat.checkSelfPermission(MainActivity.this,permission ) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{permission}, FINE_LOCATION_PERMISSION_REQUEST_CODE);

        } else {

            Toast.makeText(this, "Location Permission Already Granted", Toast.LENGTH_SHORT).show();

        }

        nextActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Intent intent = new Intent(MainActivity.this, NextActivity.class);
                    startActivity(intent);
                }

            }
        });

    }




}