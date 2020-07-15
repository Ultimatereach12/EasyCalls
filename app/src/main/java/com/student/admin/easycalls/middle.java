package com.student.admin.easycalls;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.student.admin.easycalls.gettersetter.TodayFollowUp;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class middle   extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybase);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Mybase");
        LinearLayout linearLayout=findViewById(R.id.today);
        LinearLayout enddate1=findViewById(R.id.enddate1);
        final TextView follow=findViewById(R.id.follow);
        final TextView enddate=findViewById(R.id.enddate);
        TextView searchaccount=findViewById(R.id.searchaccount);
        TextView searchdate=findViewById(R.id.searchdate);

        searchdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),search.class);
                intent.putExtra("EXTRA_SESSION_ID", "2");
                startActivity(intent);
            }
        }
        );

        searchaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),search.class);
                intent.putExtra("EXTRA_SESSION_ID", "1");
                startActivity(intent);

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//              Intent intent = new Intent(getApplicationContext(),executelist.class);
                Intent intent = new Intent(getBaseContext(), recycle.class);
                intent.putExtra("EXTRA_SESSION_ID", "1");
                startActivity(intent);
//              startActivity(intent);
            }
        });

        enddate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              Intent intent=new Intent(getApplicationContext(),executelist.class);
                Intent intent = new Intent(getBaseContext(), recycle.class);
                intent.putExtra("EXTRA_SESSION_ID", "2");
                startActivity(intent);
//              startActivity(intent);

            }
        });


        final api mApiService = network.getRetrofit().create(api.class);
        String userid = new sharedpreff(getApplicationContext()).login123();
        Call<TodayFollowUp> call = mApiService.TodayFollowUp(userid);
        call.enqueue(new Callback<TodayFollowUp>() {
            @Override
            public void onResponse(Call<TodayFollowUp> call, Response<TodayFollowUp> response) {
//                Toast.makeText(attence.this, response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();

                enddate.setText(response.body().getEnd());
                follow.setText(response.body().getToday());

            }
            @Override
            public void onFailure(Call<TodayFollowUp> call, Throwable t) {

                Log.d("Error", t.getMessage());

            }
        });
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}