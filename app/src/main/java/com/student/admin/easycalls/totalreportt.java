package com.student.admin.easycalls;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.student.admin.easycalls.gettersetter.totalreport;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class totalreportt extends AppCompatActivity {

    LinearLayout in, out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Report");

        final TextView btc=findViewById(R.id.btc);
        final TextView tobecollected=findViewById(R.id.tobecollected);
        final TextView collected=findViewById(R.id.collected);
        final TextView act=findViewById(R.id.act);
        final TextView miss=findViewById(R.id.miss);
        final TextView missam=findViewById(R.id.missam);

        final api mApiService = network.getRetrofit().create(api.class);
        String userid = new sharedpreff(getApplicationContext()).login123();
        Call<totalreport> call = mApiService.totalreport12(userid);
        call.enqueue(new Callback<totalreport>() {
            @Override
            public void onResponse(Call<totalreport> call, Response<totalreport> response) {

                System.out.println(call.request().url());
//                Toast.makeText(attence.this, response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                btc.setText(response.body().getTotalReport()[0].getBTC());
                tobecollected.setText(response.body().getTotalReport()[0].getTo_be_collected());
                collected.setText(response.body().getTotalReport()[0].getCollected());
                act.setText(response.body().getTotalReport()[0].getActual());
                miss.setText(response.body().getTotalReport()[0].getMissed());
                missam.setText(response.body().getTotalReport()[0].getMissed_amount());

//                enddate.setText(response.body()Fs.getEnd());
//                follow.setText(response.body().getToday());

            }
            @Override
            public void onFailure(Call<totalreport> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}