package com.student.admin.easycalls;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.map.TrackerService;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission_group.LOCATION;

public class MainActivity extends AppCompatActivity {


    LinearLayout ff;
    Button login1,singup;
    EditText ed1,ed2;
    static final Integer LOCATION = 0x1;
    int  REQUEST_PHONE_CALL=123;
    int  REQUEST_PHONE_CALL1=1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.login);
//        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
//        askForPermission(Manifest.permission.CALL_PHONE,REQUEST_PHONE_CALL);
//        askForPermission1(Manifest.permission.FOREGROUND_SERVICE,REQUEST_PHONE_CALL1);
        String userid= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();
        Crashlytics.setUserIdentifier(userid);
        Crashlytics.setUserName(getname);

        if (new sharedpreff(getApplicationContext()).isUserLogedOut()) {
                // System.out.println("dsffffffffffffffffffffffffffff");
        }else{
            String y=new sharedpreff(getApplicationContext()).getEmail();
            System.out.println(y);
            if (y.equals("3")) {
                Intent i = new Intent(MainActivity.this, Dashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.in, R.anim.out);
            }else if(y.equals("2")){
                Intent i = new Intent(MainActivity.this,Dashboard.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
            else if(y.equals("3"))
            {
//            Intent i = new Intent(MainActivity.this,receptionist.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.in, R.anim.out);
            }
            else{
//                Intent ii = new Intent(getApplicationContext(),DashActivity.class);
//                startActivity(ii);
//                overridePendingTransition(R.anim.in, R.anim.out);
            }
        }

        ff=(LinearLayout)findViewById(R.id.q) ;
        login1=(Button)findViewById(R.id.login);
        singup=(Button)findViewById(R.id.signup);
        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.password);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              singup.setClickable(false);
                Intent i=new Intent(getApplicationContext(),Register.class);
                startActivity(i);
                overridePendingTransition(R.anim.in, R.anim.out);
//                ActivityOptions options = ActivityOptions
//               .makeSceneTransitionAnimation(this, androidRobotView, "robot");

            }
        });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phone=ed1.getText().toString();
                String  pass=ed2.getText().toString().trim();
                if(Phone != null && !Phone.isEmpty() && !Phone.equals("null")&&pass != null && !pass.isEmpty()&&!pass.equals("null")) {
                    internetconnection internetconnection = new internetconnection();

                    if (internetconnection.isOnline(getApplicationContext())) {

                        String tokrn = new sharedpreff(getApplicationContext()).gettoken();
                     // Toast.makeText(DashActivity.this,  tokrn, Toast.LENGTH_SHORT).show();
                        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        api service = network.getRetrofit().create(api.class);
                        Call<login> call = service.Login(Phone, pass, tokrn);
                        call.enqueue(new Callback<login>() {
                             @Override
                            public void onResponse(Call<login> call, Response<login> response) {
                                progressDialog.dismiss();
                                Log.d("response", "code = " + response.code());

                                Log.d("mvvvv", "StudentId  : " + response.body().toString());
                                System.out.println("log in id :" + response.toString());
                                login resul = response.body();

                                if (resul.getLoginDetails() != null) {

                                    if (resul.getLoginDetails()[0].getEmployee_type().equals("2")) {
                                        ed1.setText("");
                                        ed2.setText("");

                                        new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getLoginDetails()[0].getEmployee_type(),resul.getLoginDetails()[0].getId(),resul.getLoginDetails()[0].getEmployee_name());
                                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        overridePendingTransition(R.anim.in, R.anim.out);
                                        startActivity(i);

                                    }
                                    else if (resul.getLoginDetails()[0].getEmployee_type().equals("3")) {

                                        ed1.setText("");
                                        ed2.setText("");

                                        new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getLoginDetails()[0].getEmployee_type(),resul.getLoginDetails()[0].getId(),resul.getLoginDetails()[0].getEmployee_name());
                                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        overridePendingTransition(R.anim.in, R.anim.out);
                                        startActivity(i);

                                    }

                                    else {

                                        Snackbar snackbar = Snackbar.make(ff, "Invalid login details", Snackbar.LENGTH_LONG);
                                        snackbar.show();
             //        new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getUser_info().getlogin_type(), resul.getUser_info().getId(), resul.getUser_info().getDoctor_name());
             //                            Intent i = new Intent(getApplicationContext(), newdashboard.class);
             //                            startActivity(i);
             //                            overridePendingTransition(R.anim.in, R.anim.out);

                                    }

                                } else {
                                    Snackbar snackbar = Snackbar.make(ff, "Invalid login details", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<login> call, Throwable t) {
                                progressDialog.dismiss();
                                System.out.println(call.request().url());
                                System.out.println(t.getMessage());
                              Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(),"No Network", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Snackbar snackbar = Snackbar.make(ff, "Enter phone number and password", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

//                   api  mApiService = network.getRetrofit().create(api.class);
//                   Call<login> call = mApiService.studentLogin(Phone,pass,"dfssd");
//                   call.enqueue(new Callback<login>() {
//                   public void onResponse(Call<login> call, Response<login> response) {
//                        progressDialog.dismiss();
//                        login login=response.body();
//                        System.out.println(response.body());
//                        System.out.println(response.code());
//                        System.out.println(response.errorBody());
//                        System.out.println(response.isSuccessful());
//                        Toast.makeText(DashActivity.this,login.getUser_info().getDoctor_name(), Toast.LENGTH_SHORT).show();
//                        JSONResponse jsonResponse = response.body();
//                        data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
//                        adapter = new DataAdapter(data);
//                        recyclerView.setAdapter(adapter);
//                 }
//                    @Override
//                    public void onFailure(Call<login> call, Throwable t) {
//                        progressDialog.dismiss();
//                        Toast.makeText(DashActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
//                        System.out.println(t.getCause());
//                    }
//                });
//                network.getRetrofit().create(api.class).studentLogin(Phone,pass,token);
//                Intent i = new Intent(getApplicationContext(),MainActivity1.class);
//                startActivity(i);

            }
        });
    }

    private void askForPermission(String permission, Integer requestCode ) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

                System.out.println("test");

            } else {
                System.out.println("test123");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }

        } else {
            System.out.println("test12345");
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission1(String permission, Integer requestCode ) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

                System.out.println("test");

            } else {
                System.out.println("test123");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }

        } else {
            System.out.println("test12345");
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


}
