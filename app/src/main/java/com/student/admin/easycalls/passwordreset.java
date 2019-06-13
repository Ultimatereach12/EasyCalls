package com.student.admin.easycalls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.gettersetter.password;
import com.student.admin.easycalls.gettersetter.tllist;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class passwordreset extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.passwordreset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final EditText email=findViewById(R.id.email);
        Button login=findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().length()==10){

//                    Toast.makeText(passwordreset.this, "test", Toast.LENGTH_SHORT).show();
                    final ProgressDialog progressDialog = new ProgressDialog(passwordreset.this);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    api service = network.getRetrofit().create(api.class);
                    Call<password> call = service.password(email.getText().toString());
                    call.enqueue(new Callback<password>() {
                        @Override
                        public void onResponse(Call<password> call, Response<password> response) {
                            progressDialog.dismiss();

                            if(response.body().getResponse().getResponse_code().equals("1")){
                                 System.out.println(call.request().url());
                                Toast.makeText(passwordreset.this,"Please Contact Ranjith", Toast.LENGTH_SHORT).show();
                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<password> call, Throwable t) {
                            progressDialog.dismiss();
                            System.out.println(call.request().url());
                            System.out.println(t.getMessage());
                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                        }
                    });



                }else{

                    Toast.makeText(passwordreset.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();

                }

            }
        });




    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}