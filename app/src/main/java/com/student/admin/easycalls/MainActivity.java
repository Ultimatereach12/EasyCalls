package com.student.admin.easycalls;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, TextWatcher {


    LinearLayout ff;
    Button login1,singup;
    EditText ed1,ed2;
    static final Integer LOCATION = 0x1;
    int  REQUEST_PHONE_CALL=123;
    int  REQUEST_PHONE_CALL1=1234;

    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences12;
    SharedPreferences.Editor editor;
    String deviceid;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        final TextView forget=findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),passwordreset.class);
                startActivity(intent);


            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }

        sharedPreferences12 = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences12.edit();

        rem_userpass = (CheckBox)findViewById(R.id.checkBox);

        if(sharedPreferences12.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);
            rem_userpass.setOnCheckedChangeListener(this);

//        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
//        askForPermission(Manifest.permission.CALL_PHONE,REQUEST_PHONE_CALL);
//        askForPermission1(Manifest.permission.FOREGROUND_SERVICE,REQUEST_PHONE_CALL1);

        String userid= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();

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

        ed1.setText(sharedPreferences12.getString(KEY_USERNAME,""));
        ed2.setText(sharedPreferences12.getString(KEY_PASS,""));

        ed1.addTextChangedListener(this);
        ed2.addTextChangedListener(this);



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
                        Call<login> call = service.Login(Phone, pass, tokrn,deviceid);
                        call.enqueue(new Callback<login>() {
                             @Override
                            public void onResponse(Call<login> call, Response<login> response) {
                                progressDialog.dismiss();

                                 System.out.println("log in id :" + call.request().url());
//                                 Log.d("response", "code = " + response.code());
//                                 Log.d("mvvvv", "StudentId  : " + response.body().toString());
//                                 System.out.println("log in id :" + response.toString());

                                login resul = response.body();

                                if (resul.getLoginDetails() != null) {


                                    if (resul.getLoginDetails()[0].getEmployee_type().equals("2")) {
//                                        ed1.setText("");
//                                        ed2.setText("");

                                        new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getLoginDetails()[0].getEmployee_type(),resul.getLoginDetails()[0].getId(),resul.getLoginDetails()[0].getEmployee_name());
                                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        overridePendingTransition(R.anim.in, R.anim.out);
                                        startActivity(i);

                                    }
                                    else if (resul.getLoginDetails()[0].getEmployee_type().equals("3")) {
//
//                                        ed1.setText("");
//                                        ed2.setText("");

                                        new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getLoginDetails()[0].getEmployee_type(),resul.getLoginDetails()[0].getId(),resul.getLoginDetails()[0].getEmployee_name());
                                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        overridePendingTransition(R.anim.in, R.anim.out);
                                        startActivity(i);

                                    }

                                    else {

                                        Snackbar snackbar = Snackbar.make(ff, "Invalid login details", Snackbar.LENGTH_LONG);
                                        snackbar.show();
             //                            new sharedpreff(getApplicationContext()).saveLoginDetails(resul.getUser_info().getlogin_type(), resul.getUser_info().getId(), resul.getUser_info().getDoctor_name());
             //                            Intent i = new Intent(getApplicationContext(), newdashboard.class);
             //                            startActivity(i);
             //                            overridePendingTransition(R.anim.in, R.anim.out);

                                    }

                                } else {
                                    Snackbar snackbar = Snackbar.make(ff, response.body().getResponse().getResponse_message(), Snackbar.LENGTH_LONG);
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
                        Toast.makeText(getApplicationContext(),"Net not connect", Toast.LENGTH_SHORT).show();
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

        }
        else {
            System.out.println("test12345");
//            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission1(String permission, Integer requestCode ) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
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


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    private void managePrefs() {
        if(rem_userpass.isChecked()){

            editor.putString(KEY_USERNAME, ed1.getText().toString().trim());
            editor.putString(KEY_PASS, ed2.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();

        }else{

            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();


        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

     managePrefs();

    }
    @Override
    public void afterTextChanged(Editable s) {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)   {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=  PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                     deviceid=Build.DEVICE;
//                       Toast.makeText(this, g, Toast.LENGTH_SHORT).show();
            }
            else  {
                //not granted
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {

        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;

        }

//        textView.setText(Build.DEVICE);

        deviceid=Build.DEVICE;
/*   Toast.makeText(this, g, Toast.LENGTH_SHORT).show();*/

    }



}
