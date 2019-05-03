package com.student.admin.easycalls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class Register  extends AppCompatActivity {

    Button singup1;
    EditText ed1,ed2,ed3,ed4,Email;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        singup1 = (Button) findViewById(R.id.signup);
        Email=(EditText) findViewById(R.id.Email);
        ed1 = (EditText) findViewById(R.id.phone);
        ed2 = (EditText) findViewById(R.id.password);
        ed3= (EditText) findViewById(R.id.repassword);
        ed4= (EditText) findViewById(R.id.name);

        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
//        awesomeValidation.addValidation(this, R.id.Email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
//        awesomeValidation.addValidation( this,R.id.phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this,R.id.password, regexPassword, R.string.invalid_password);



        singup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = ed1.getText().toString();
                String password = ed2.getText().toString();
                String repassword = ed3.getText().toString();
                String name = ed4.getText().toString();

                name = name.replace(" ", "");
                String Email11=Email.getText().toString();
                if(awesomeValidation.validate()&&repassword.equals(password)&&phone.length()>9){

                    internetconnection internetconnection=new  internetconnection();

                    if(internetconnection.isOnline(getApplicationContext())) {
                        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        api service = network.getRetrofit().create(api.class);

                        Call<login> call = service.register(name, phone, password, Email11,"Exec","2");

                        call.enqueue(new Callback<login>() {
                            @Override
                            public void onResponse(Call<login> call, Response<login> response) {
                                progressDialog.dismiss();
                                Log.d("response", "code = " + response.code());
                                Log.d("mvvvv", "StudentId  :  " + response.body().toString());
                                System.out.println("log in id :" + response.toString());
                                login resul = response.body();

                                if (resul.getResponse() != null) {

                                    Toast.makeText(Register.this, resul.getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                                }
                            }

                            @Override
                            public void onFailure(Call<login> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "network  error", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "mobile number and mismatch password", Toast.LENGTH_SHORT).show();

//           Toast.makeText(getApplicationContext(), "mobile number and mis match password", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        return true;
    }
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


}
