package com.student.admin.easycalls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register  extends AppCompatActivity {

    Button singup1;
    EditText ed1,ed2,ed3,ed4,Email;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Register");
        singup1 = (Button) findViewById(R.id.signup);

        ed1 = (EditText) findViewById(R.id.phone);
        ed2 = (EditText) findViewById(R.id.password);
        ed3= (EditText) findViewById(R.id.repassword);
        ed4= (EditText) findViewById(R.id.name);
//        awesomeValidation.addValidation(this, R.id.Email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
//        awesomeValidation.addValidation( this,R.id.phone, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        String regexPassword = ".{8,}";

        singup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = ed1.getText().toString();
                String password = ed2.getText().toString();
                String repassword = ed3.getText().toString();
                String name = ed4.getText().toString();

                name = name.replace(" ", "");

                if(repassword.equals(password)&&phone.length()>9){

                    internetconnection internetconnection=new  internetconnection();

                    if(internetconnection.isOnline(getApplicationContext())) {
                        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        api service = network.getRetrofit().create(api.class);
                        String teaml_id= new sharedpreff(getApplicationContext()).login123();
                        Call<login> call = service.register(name, phone, password,"Exec","2",teaml_id);
                        call.enqueue(new Callback<login>() {
                            @Override
                            public void onResponse(Call<login> call, Response<login> response) {
                                progressDialog.dismiss();
//                                Log.d("response", "code = " + response.code());
//                                Log.d("mvvvv", "StudentId  :  " + response.body().toString());
//                                System.out.println("log in id :" + response.toString());
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
//                  Toast.makeText(getApplicationContext(), "mobile number and mis match password", Toast.LENGTH_LONG).show();
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
