package com.student.admin.easycalls;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.student.admin.easycalls.R;
import com.student.admin.easycalls.gettersetter.discode;
import com.student.admin.easycalls.gettersetter.exelist;
import com.student.admin.easycalls.map.JSONParserTask;
import com.student.admin.easycalls.map.TrackerService;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.module.DirectionFinder;
import com.student.admin.easycalls.shared.sharedpreff;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng p1,p2;
    private Button btnFindPath;
    private EditText accno,summary,etOrigin,bank,amout,transid,cheque,date,DispoCode;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    GoogleApiClient mGoogleApiClient;
    private String mLastUpdateTime;
    private Boolean mRequestingLocationUpdates;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    LinearLayout l1,l2;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    Button start;

    ArrayList<LatLng> MarkerPoints;

    TextView count,dis;
    ArrayList<discode.DispoCodeList> data;
    String[] dispo;
    MaterialSpinner spinner,spinner1;
    String value,id1;
    String time ;
    TextView accnotext;
    long startTime = 0;
    final Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int hours   = (int) ((millis / (1000*60*60)) % 24);
            count.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
            time=  count.getText().toString();
            timerHandler.postDelayed(this, 500);

        }
           };



String t[]={"Cash" , "Cheque" , "Online Transaction"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        value= getIntent().getExtras().getString("name");
        id1= getIntent().getExtras().getString("id");
        count=findViewById(R.id.count);
        spinner=findViewById(R.id.spinner);
        spinner1=findViewById(R.id.spinner1);
        l1=findViewById(R.id.timer);
        l2=findViewById(R.id.clientfeed);
        DispoCode=findViewById(R.id.DispoCode);
        accno=findViewById(R.id.accno);
        bank=findViewById(R.id.bank);
        amout=findViewById(R.id.amount);
        spinner1.setItems(t);
        transid=findViewById(R.id.transid);
        cheque=findViewById(R.id.cheque);
        date=findViewById(R.id.date);
//      accnotext=findViewById(R.id.accnotext);
        summary=findViewById(R.id.summary);
        dis=findViewById(R.id.dis);
        ImageView image=findViewById(R.id.load);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ggg();

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        DispoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DispoCode.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        spinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

        @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//      Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(item.equals("Cheque")){

                    cheque.setVisibility(View.VISIBLE);
                    bank.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    transid.setVisibility(View.GONE);

                }else if(item.equals("Cash")){

                    transid.setVisibility(View.VISIBLE);
                    cheque.setVisibility(View.GONE);
                    bank.setVisibility(View.GONE);
                    date.setVisibility(View.GONE);


                }else if(item.equals("Online Transaction")){



                    cheque.setVisibility(View.GONE);
                    bank.setVisibility(View.GONE);
                    date.setVisibility(View.GONE);
                    transid.setVisibility(View.VISIBLE);



                }
            }
        });

             ggg();

     //runs without a timer by reposting this handler at the end of the runnable
        init();
       start=(Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String o= start.getText().toString();

                 if(o.equals("start")){

                     start.setText("end");
                     Dexter.withActivity(MapsActivity.this)
                             .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                             .withListener(new PermissionListener() {
                                 @Override
                                 public void onPermissionGranted(PermissionGrantedResponse response) {
                                     mRequestingLocationUpdates = true;
                                     startLocationUpdates();
                                     startLocationService();
                                 }

                                 @Override
                                 public void onPermissionDenied(PermissionDeniedResponse response) {
                                     if (response.isPermanentlyDenied()) {
                                         // open device settings when the permission is
                                         // denied permanently
                                         openSettings();
                                     }
                                 }

                                 @Override
                                 public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                                 }
                                 public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                     token.continuePermissionRequest();
                                 }
                             }).check();

                 }else if(o.equals("end")){

                     start.setText("timer start");
                     mRequestingLocationUpdates = false;
                     stopLocationUpdates();
                     start.setText("stop timer");
                     startTime = System.currentTimeMillis();
                     timerHandler.postDelayed(timerRunnable, 0);
                 }

                 else if(o.equals("stop timer")) {

                     start.setText("upload data");
                     timerHandler.removeCallbacks(timerRunnable);
                     l1.setVisibility(View.GONE);
                     l2.setVisibility(View.VISIBLE);

                 }

                 else if(o.equals("upload data")) {
//
                     final api mApiService = network.getRetrofit().create(api.class);
                     String userid= new sharedpreff(getApplicationContext()).login123();
                     String g =null;String gg=null;
                       String dfg=summary.getText().toString();
                      int type= spinner.getSelectedIndex();
                      int type1= spinner1.getSelectedIndex();
                            System.out.println(mCurrentLocation);
//                     Toast.makeText(MapsActivity.this, t[type1], Toast.LENGTH_SHORT).show();
                     if (mCurrentLocation!=null) {

                        g= String.valueOf(mCurrentLocation.getLatitude());
                       double i=  mCurrentLocation.getLongitude();
                       gg= String.valueOf(i);
                     }else{
                         g="";
                         gg="";
                     }
                   String hi=  accno.getText().toString();
                if(dfg.length()>3){
                    String e=amout.getText().toString();
                    String ee=bank.getText().toString();
                     String p= transid.getText().toString();
                    String ttt=cheque.getText().toString();
                    String tt= date.getText().toString();



     Call<exelist> call = mApiService.addlatlong1
             (userid, dispo[type],dfg,hi,g,gg,t[type1],id1,time,e,ee,p,ttt,tt);
     call.enqueue(new Callback<exelist>(){
         @Override
         public void onResponse(Call<exelist> call, Response<exelist> response) {
//                    System.out.println(response.body());
//                    progressDialog.dismiss();
//             System.out.println(call.request().url());
//             System.out.println(response.body().getResponse().getResponse_message());
             Toast.makeText(MapsActivity.this, response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
//             Intent i = new Intent(getApplicationContext(),executelist.class);
//             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//             startActivity(i);
//             overridePendingTransition(R.anim.out, R.anim.in);
             if( response.body().getResponse().getResponse_code().equals("1")){
                 finish();
             }



         }
         @Override
         public void onFailure(Call<exelist> call, Throwable t) {
//                    progressDialog.dismiss();
             Log.d("Error", t.getMessage());

         }
     });

 }else{
     Toast.makeText(MapsActivity.this, "Enter Summary More Text ", Toast.LENGTH_SHORT).show();

 }


                 }
                 }

        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


    private void ggg() {
        final api mApiService = network.getRetrofit().create(api.class);
        Call<discode> call = mApiService.discodw1();
        call.enqueue(new Callback<discode>() {
            @Override
            public void onResponse(Call<discode> call, Response<discode> response) {

                data = new ArrayList<>(Arrays.asList(response.body().getDispoCodeList()));

                dispo=   new String[data.size()];
                for(int i=0;i<data.size();i++) {
                    dispo[i]=data.get(i).getDis_code();
                }

                spinner.setItems(dispo);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
     //          Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(item.equals("PTP") ||item.equals("FLW") ||item.equals("ALC") ){
                            DispoCode.setVisibility(View.VISIBLE);

                        }else {
                                DispoCode.setVisibility(View.GONE);
                        }

                    }
                });

                System.out.println(response.body().getResponse().getResponse_message());
            }
            @Override
            public void onFailure(Call<discode> call, Throwable t) {
//                    progressDialog.dismiss();
                Log.d("Error", t.getMessage());

            }
        });

    }


    private void sendRequest() {

        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new DirectionFinder(getApplicationContext(),origin,destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationFromAddress(getApplicationContext(),value);


      if(p1!=null) {

    mMap.addMarker(new MarkerOptions().position(p1).title("Destination"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p1, 12));

       }else{

    Toast.makeText(this, "" +
            "No Match in Location", Toast.LENGTH_SHORT).show();

        }
    }
    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;


        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

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
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
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

    private void restoreValuesFromBundle(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }
            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }
            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

    updateLocationUI();

    }

    private void updateLocationUI() {
        if (mCurrentLocation!=null) {

//            addlatlong
            final api mApiService = network.getRetrofit().create(api.class);

            String userid= new sharedpreff(getApplicationContext()).login123();
            Call<exelist> call = mApiService.addlatlong(userid,String.valueOf(mCurrentLocation.getLatitude()),String.valueOf(mCurrentLocation.getLongitude()),id1);
            call.enqueue(new Callback<exelist>(){
                @Override
                public void onResponse(Call<exelist> call, Response<exelist> response) {
//                    System.out.println(response.body());
//                    progressDialog.dismiss();

                    System.out.println(response.body().getResponse().getResponse_message());
                }
                @Override
                public void onFailure(Call<exelist> call, Throwable t) {
                //  progressDialog.dismiss();
                    Log.d("Error", t.getMessage());

                }
            });
//            System.out.println(mCurrentLocation);
            p2=new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()) ;


            String str_origin = "origin=" + p1.latitude + "," + p1.longitude;
            String str_dest = "destination=" + mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude();
            String sensor = "sensor=false";
            String parameters = str_origin + "&" + str_dest ;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+"&key=AIzaSyC-lD2GZRCFeoU2boj_lIiF_Zc_20TFKrw";
            //https://maps.googleapis.com/maps/api/directions/json?origin=Time+Square&destination=Chelsea+Market&key=YOUR_API_KEY
             Log.d("onMapClick", url.toString());
//            FetchUrl FetchUrl = new FetchUrl();
//            FetchUrl.execute(url);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p2));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

            Location delhi_location = new Location("Delhi");
            delhi_location.setLatitude(p1.latitude);
            delhi_location.setLongitude(p1.longitude);
            Location chandigarh_location = new Location("Chandigarh");
            chandigarh_location.setLatitude(mCurrentLocation.getLatitude());
            chandigarh_location.setLongitude(mCurrentLocation.getLongitude());
            double distance = (delhi_location.distanceTo(chandigarh_location))/1000;
            String j=  new DecimalFormat("##.##").format(distance);
             dis.setText("Distance  : "+j +" KM");
//            AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
//            alertDialog.setTitle("Info");
//            alertDialog.setMessage("Distance between these two location is : "+distance +" miles");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                         dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

            mMap.addMarker(new MarkerOptions().position(p2).title("current"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p2));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p2, 12));
//            txtLocationResult.setText(
//                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
//                            "Lng: " + mCurrentLocation.getLongitude()
//            );
            // giving a blink animation on TextView
//            txtLocationResult.setAlpha(0);
//            txtLocationResult.animate().alpha(1).setDuration(300);

            // location last updated time
//            txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);
        }
        toggleButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
//        btnStartUpdates.setEnabled(false);
//        btnStopUpdates.setEnabled(true);
        } else {
//            btnStartUpdates.setEnabled(true);
//            btnStopUpdates.setEnabled(false);
        }
    }


    private void startLocationUpdates(){
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                             mLocationCallback, Looper.myLooper());

                        updateLocationUI();
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
                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                Toast.makeText(MapsActivity.this, "error", Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });

    }



//    @OnClick(R.id.btn_stop_location_updates)
//    public void stopLocationButtonClick() {
//
//    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this,new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                        toggleButtons();
                    }
                });
    }

//    @OnClick(R.id.btn_get_last_location)
//    public void showLastKnownLocation() {
//        if (mCurrentLocation != null) {
//            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
//                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();


    }

    @Override
    protected void onPause() {

        super.onPause();

        if(mRequestingLocationUpdates) {
            //pausing location updates
            stopLocationUpdates();
        }
    }






    @Override
    public void onBackPressed() {
//        super.onBackPressed();
                    AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Sure exit  page" );
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();



    }

    private boolean checkPermissions() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;

    }


}
