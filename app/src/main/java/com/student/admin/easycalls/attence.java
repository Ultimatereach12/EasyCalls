package com.student.admin.easycalls;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.student.admin.easycalls.gettersetter.datelogin;
import com.student.admin.easycalls.gettersetter.visit;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class attence  extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    LinearLayout in,out;
    private SettingsClient mSettingsClient;
    LinearLayout l1,l2;
    private Boolean mRequestingLocationUpdates;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private LocationRequest mLocationRequest;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        init();
        startLocationUpdates();
        TextView toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Attendance");


            in=findViewById(R.id.in);
            out=findViewById(R.id.out);

        in.setEnabled(false);
        out.setEnabled(false);
        startLocationService();

        final api mApiService = network.getRetrofit().create(api.class);
        String userid = new sharedpreff(getApplicationContext()).login123();
        Call<datelogin> call = mApiService.datelogin(userid);
        call.enqueue(new Callback<datelogin>() {
            @Override
            public void onResponse(Call<datelogin> call, Response<datelogin> response) {

System.out.println(call.request().url());
//                Toast.makeText(attence.this, response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                in.setEnabled(true);
                out.setEnabled(true);
               if(response.body().getStatus().equals("1"))
              {

              in.setVisibility(View.VISIBLE);
              out.setVisibility(View.GONE);

               }else{
                   in.setVisibility(View.GONE);
                   out.setVisibility(View.VISIBLE);

               }
            }

            @Override
            public void onFailure(Call<datelogin> call, Throwable t) {


                Log.d("Error", t.getMessage());


            }
        });




        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              g();


            }
        });




        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g();
            }
        });



    }


    private void startLocationService() {
        // Before we start the service, confirm that we have extra power usage privileges.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            Intent intent = new Intent();
            if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

    }


    private void startLocationUpdates(){
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(attence.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        System.out.println("out");

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae . startResolutionForResult(attence.this, REQUEST_CHECK_SETTINGS);


                                }
                                catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case  LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                Toast.makeText(attence.this, "error", Toast.LENGTH_LONG).show();
                        }

//                        updateLocationUI();


                        System.out.println("try");
                    }
                });
    }




    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this,new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();


                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                      address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(mCurrentLocation );
                stopLocationUpdates
                        ();
//                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//
//                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }
    private void g() {

        final api mApiService = network.getRetrofit().create(api.class);
        String userid = new sharedpreff(getApplicationContext()).login123();
        Call<visit> call = mApiService.AttendanceDetails(userid,address);
        call.enqueue(new Callback<visit>() {
            @Override
            public void onResponse(Call<visit> call, Response<visit> response) {

                System.out.println(call.request().url());




                if(response.body().getResponse().getResponse_code().equals("0"))
                {
                    Toast.makeText(attence.this,"Check out success", Toast.LENGTH_SHORT).show();
                    in.setVisibility(View.VISIBLE);
                    out.setVisibility(View.GONE);

                }else if(response.body().getResponse().getResponse_code().equals("2")){

                    Toast.makeText(attence.this,response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                    in.setVisibility(View.VISIBLE);
                    out.setVisibility(View.GONE);

                }
                else{

                    in.setVisibility(View.GONE);
                    out.setVisibility(View.VISIBLE);
                    Toast.makeText(attence.this,"Check in success", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<visit> call, Throwable t) {


                Log.d("Error", t.getMessage());


            }
        });
    }

    public boolean onSupportNavigateUp() {
        if(mRequestingLocationUpdates) {
            //pausing location updates
            stopLocationUpdates();
        }
        onBackPressed();
        return true;
    }
}
