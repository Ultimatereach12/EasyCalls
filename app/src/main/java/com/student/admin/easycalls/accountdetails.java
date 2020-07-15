package com.student.admin.easycalls;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class accountdetails   extends AppCompatActivity {


    LinearLayout in, out;
    String h="";
    String h1="";
    String  address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailaccount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        ii.putExtra("id", android.get(i).getEmployee_id());
//        ii.putExtra("address",android.get(i).getCustomer_address());
//        ii.putExtra("name",android.get(i).getCustomer_name());
//        ii.putExtra("phone",android.get(i).getCustomer_phone());
//        ii.putExtra("amount",android.get(i).getBillzip_zip());

         EditText name1=findViewById(R.id.name);
         EditText  accountnumber1=findViewById(R.id.accountnumber);
         EditText  phone1=findViewById(R.id.phone);
         final EditText    address1=findViewById(R.id.address);
         final EditText   amount1=findViewById(R.id.amount);
         final Button g=findViewById(R.id.map);
         final EditText ggg=findViewById(R.id.collected);
         String    value= getIntent().getExtras().getString("name");
         final String  id1= getIntent().getExtras().getString("id");
         address= getIntent().getExtras().getString("address");
         String  name= getIntent().getExtras().getString("phone");
         final String  amount= getIntent().getExtras().getString("amount");
         final String amount11 = getIntent().getExtras().getString("amount1");
         String  acc= getIntent().getExtras().getString("acc");
         final String  end= getIntent().getExtras().getString("end");

          if(amount==null||amount.equals(""))
          {
                 h="0";
          }else{

                 h=amount;

          }

        if(amount11==null||amount11.equals("")){
            h1="0";
        }else{
            h1=amount11;
        }

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address=address1.getText().toString();
                g.setClickable(false);
                Intent ii=new Intent(getApplicationContext(),MapsActivity.class);
                ii.putExtra("address",address);
                ii.putExtra("name",h);
                ii.putExtra("end",end);
                ii.putExtra("name1",h1);
                ii.putExtra("id",id1);
                g.setClickable(true);
                startActivity(ii);

            }
        });

         name1.setText(value);
         accountnumber1.setText(acc);
         phone1.setText(name);
         address1.setText(address);
         amount1.setText(amount);
         ggg.setText(amount11);
         System.out.println(value);
         System.out.println(id1);
         TextView toolbar_title = findViewById(R.id.toolbar_title);
         toolbar_title.setText("Account details");

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
