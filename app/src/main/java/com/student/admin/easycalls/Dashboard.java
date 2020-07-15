package com.student.admin.easycalls;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.gettersetter.password;
import com.student.admin.easycalls.map.TrackerService;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class Dashboard  extends AppCompatActivity {

      AlertDialog alertDialog;
    LinearLayout ff,clientlocaion,employee,register,add,exec,exec1,detail,detail1,first,second,Attendance,Attendance1,del;
    Button login1, singup;
    EditText ed1, ed2;
    static final Integer LOCATION = 0x1;
   int  REQUEST_PHONE_CALL=123;
    private static final int PERMISSIONS_REQUEST = 0x1;
     public EditText accno12;
    private static String[] PERMISSIONS_REQUIRED = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTrackingStatus(intent.getIntExtra(getString(R.string.status), 0));
        }
    };


    private BroadcastReceiver mLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            double lat = intent.getDoubleExtra("lat", 0.0);
            double lng = intent.getDoubleExtra("long", 0.0);
            setTrackingLocation(lat, lng);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
        askForPermission(Manifest.permission.CALL_PHONE,REQUEST_PHONE_CALL);
        String userid1= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();
        TextView  name= findViewById(R.id.name);
        String y=new sharedpreff(getApplicationContext()).getname();
        name.setText(y);
//        Toast.makeText(this, y, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
//            startActivity(intent);
        }

        LinearLayout button = findViewById(R.id.set);
        LinearLayout client = findViewById(R.id.client);
        Attendance=findViewById(R.id.Attendance1);
        Attendance1=findViewById(R.id.Attendance2);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        employee=findViewById(R.id.clientlocation);
        del=findViewById(R.id.del1);
        clientlocaion=findViewById(R.id.emplocation);
        exec1=findViewById(R.id.auto1);
        LinearLayout exec1 = findViewById(R.id.auto1);
        exec=findViewById(R.id.auto);
        add=findViewById(R.id.add);
        register=findViewById(R.id.emplocation1);
        detail=findViewById(R.id.details);
        detail1=findViewById(R.id.details1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        TextView toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Dashboard");
        checkLocationPermission();
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//          Intent   intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent1);
        }

            // startService(new Intent( Dashboard.this, TrackerService.class));
        String userid= new sharedpreff(getApplicationContext()). getEmail1();
      //  Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();
        if(userid.equals("3")){
            clientlocaion.setVisibility(View.VISIBLE);
            employee.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
            Attendance.setVisibility(View.VISIBLE);
            first.setVisibility(View.VISIBLE);
            second.setVisibility(View.GONE);

            exec.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            detail.setVisibility(View.VISIBLE);
        }else if(userid.equals("2")){

            second.setVisibility(View.VISIBLE);
            first.setVisibility(View.GONE);
            Attendance.setVisibility(View.GONE);
            del.setVisibility(View.VISIBLE);
            employee.setVisibility(View.VISIBLE);
            clientlocaion.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
            exec1.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);

        }
            add.setOnClickListener(new View.OnClickListener() {
             @Override
       public void onClick(View v) {

                 Intent intent = new Intent(getApplicationContext(), attence.class);
                 startActivity(intent);


//           Toast.makeText(Dashboard.this, "under construction ", Toast.LENGTH_SHORT).show();

             }
      });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), totalreportt.class);
                startActivity(intent);

            }
        });

        Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), attence.class);
                startActivity(intent);


//        Toast.makeText(Dashboard.this, "under construction ", Toast.LENGTH_SHORT).show();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), employeelist.class);
                startActivity(intent);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), detail.class);
                startActivity(intent);

//                Toast.makeText(Dashboard.this, "", Toast.LENGTH_SHORT).show();

            }
        });


        exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String t[]= {"Landline" , "Mobile" };
                LayoutInflater li = LayoutInflater.from(Dashboard.this);
                final View promptsView = li.inflate(R.layout.prompts, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Dashboard.this);
                alertDialogBuilder.setCancelable(false);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText)promptsView
                        .findViewById(R.id.name);
                final EditText phone = (EditText)promptsView
                        .findViewById(R.id.phone);
                final EditText address = (EditText)promptsView
                        .findViewById(R.id.Address);
                 accno12 = (EditText)promptsView
                        .findViewById(R.id.accno);
                final Button cencel = (Button)promptsView
                        .findViewById(R.id.cencel);

                final MaterialSpinner test = (MaterialSpinner)promptsView
                        .findViewById(R.id.spinner1);

                final Button submit = (Button) promptsView
                        .findViewById(R.id.submit);

                test.setItems(t);

                accno12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Intent ii=new Intent(Dashboard.this,search.class);
////                        ii.putExtra("name",address1);
////                        ii.putExtra("id", response.body().getResponse().getResponse_code());
//                        ii.putExtra("EXTRA_SESSION_ID", "3");
//
//                        startActivity(ii);

//                        Intent intent = new Intent();
//                        intent.setClass(getApplicationContext(), search.class);
//                        intent.putExtra("EXTRA_SESSION_ID", "3");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivityForResult(intent, 6969);
//                        setResult(60);


                    }
                });
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int mYear, mMonth, mDay;
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(Dashboard.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        phone.setText(String.format("%02d",dayOfMonth) + "-" + String.format("%02d",monthOfYear + 1)+ "-" + year);




                                    }

                                    }, mYear, mMonth, mDay);
                        datePickerDialog.show();


                    }
                });


                 alertDialog = alertDialogBuilder.create();

                cencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        alertDialog.dismiss();


                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        internetconnection internetconnection = new internetconnection();


                        if (internetconnection.isOnline(getApplicationContext())) {


                            String user= userInput.getText().toString();
                            final String phone1= phone.getText().toString();
                            final String address1= address.getText().toString();
                            String accno1=   accno12.getText().toString();
                            int type1= test.getSelectedIndex();


                            if(accno1.length()>1) {
                                if (user.length() > 2) {


                                    
                                    if( phone1.length() > 4){

                                        if(address1.length() > 3){

                                            String r = userInput.getText().toString();
                                            final ProgressDialog progressDialog = new ProgressDialog(Dashboard.this);
                                            progressDialog.setMessage("Loading ... ");
                                            progressDialog.setCancelable(false);
                                            progressDialog.show();
                                            api mApiService = network.getRetrofit().create(api.class);
                                            String userid = new sharedpreff(Dashboard.this).login123();
                                            Call<login> call = mApiService.exee(userid, user, address1, accno1, phone1, t[type1]);
                                            alertDialog.dismiss();
                                            call.enqueue(new Callback<login>() {
                                                @Override
                                                public void onResponse(Call<login> call, Response<login> response) {
                                            System.out.println(call.request().url());
                                                    System.out.println(response.body());

//                                              Extra("name1", "");
//                                                    ii.putExtra("address", address1);
//                                      value= getIntent().getExtras().getString("name");

                                             Toast.makeText(Dashboard.this, response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();

                                                  if (response.body().getResponse().getResponse_message().equals("ExecutiveLocation Inserted")) {

                                                    Intent ii = new Intent(Dashboard.this, MapsActivity.class);
                                                    ii.putExtra("name", address1);
                                                    ii.putExtra("id", response.body().getResponse().getResponse_code());
                                                    ii.putExtra("end", phone1);
                                                    ii.putExtra("name", "");
                                                    ii.putExtra("name1", "");
                                                    startActivity(ii);

                                                                   }
                                                }

                                                @Override
                                                public void onFailure(Call<login> call, Throwable t) {
                                                    Log.d("Error", t.getMessage());
                                                    Toast.makeText(Dashboard.this,"network error",Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    alertDialog.dismiss();
                                                }
                                            });




                                        }else{
                                            Toast.makeText(Dashboard.this, "Check Address length  ", Toast.LENGTH_SHORT).show();
                                        }

                                      }else{
                                        Toast.makeText(Dashboard.this, "Select date", Toast.LENGTH_SHORT).show();
                                    }
                                     } else {

                                    Toast.makeText(getApplicationContext(), "Check name length", Toast.LENGTH_SHORT).show();
                                }

                            }else{

                                Toast.makeText(getApplicationContext(), "Account number  length not valid ", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



//            create alert dialog
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(42,184,204)));
//            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);


//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                //  show it
                alertDialog.show();



            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),middle.class);
                startActivity(intent);

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(getApplicationContext(),Register.class);
                startActivity(i);
                overridePendingTransition(R.anim.in,R.anim.out);

            }
        });
    }





    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            // We request storage perms as well as location perms, but don't care
            // about the storage perms - it's just fort debugging.
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                    } else {

                        checkGpsEnabled();
                    }
                }
            }
        }
    }
    private void checkLocationPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int storagePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (locationPermission != PackageManager.PERMISSION_GRANTED
                || storagePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST);
        } else {
            checkGpsEnabled();
        }
    }
    private void checkGpsEnabled() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            reportGpsError();
            startLocationService();
        } else {
            startLocationService();
        }
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
        startService(new Intent(this, TrackerService.class));
    }

    private void stopLocationService() {
        stopService(new Intent(this, TrackerService.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
   //Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_user){

//      new sharedpreff(getApplicationContext()).logout1();
//      Intent i=new Intent(this,DashActivity.class);
//      startActivity(i);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Dashboard.this);
            builder1.setMessage("Sure Logout  ");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            stopLocationService();
                            String userid= new sharedpreff(getApplicationContext()).login123();
                            api service = network.getRetrofit().create(api.class);
                            Call<password> call = service.logout(userid );
                            call.enqueue(new Callback<password>() {
                                @Override
                                public void onResponse(Call<password> call,Response<password> response) {

//                                    System.out.println(call.request().url());
                                    new sharedpreff(getApplicationContext()).logout1();
                                    Intent i = new Intent(Dashboard.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                                }
                                @Override
                                public void onFailure(Call<password> call, Throwable t) {
                                    System.out.println(call.request().url());
                                    Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                                }
                            });



                    }
                    }
                    );

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    } 
                           );

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(Dashboard.this).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage("Sure exit  app" );
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void setTrackingStatus(int status) {
        boolean tracking = status == R.string.tracking;
//        mTransportIdEditText.setEnabled(!tracking);
//        mStartButton.setVisibility(tracking ? View.INVISIBLE : View.VISIBLE);
//        if (mSwitch != null) {
//            // Initial broadcast may come before menu has been initialized.
//            mSwitch.setChecked(tracking);
//        }
//        ((TextView) findViewById(R.id.title)).setText(getString(status));
    }
    private void setTrackingLocation(double lat, double lng) {
//        latText.setText(Double.toString(lat));
//        lngText.setText(Double.toString(lng));
        String h= String.valueOf(lat);
        String hh= String.valueOf(lng);
//        System.out.println(h  +"sdffffffffffffffffff"+ hh );
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("tretretertefgdfgdgdgfd");

            if (data != null) {

                String  user_mobile = data.getStringExtra("PhoneNumber");
                System.out.println(user_mobile );

                if(alertDialog!=null){
                    accno12.setText(user_mobile);
                }else{
                     exec.callOnClick();
                     accno12.setText(user_mobile);
                }
//
            }
    }

    @Override
    public void onResume() {

        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(TrackerService.STATUS_INTENT));
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocationReceiver,
                new IntentFilter(TrackerService.LOCATION_INTENT));

    }
    @Override
    protected void onPause() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocationReceiver);
        super.onPause();

    }


    private void askForPermission(String permission, Integer requestCode ) {

        if (ContextCompat.checkSelfPermission(Dashboard.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(Dashboard.this, new String[]{permission}, requestCode);

            }else{

                ActivityCompat.requestPermissions(Dashboard.this, new String[]{permission}, requestCode);
            }

        } else {
//             Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}