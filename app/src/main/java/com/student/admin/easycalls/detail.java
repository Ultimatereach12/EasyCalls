package com.student.admin.easycalls;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.student.admin.easycalls.gettersetter.exelist;
import com.student.admin.easycalls.gettersetter.tllist;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail extends AppCompatActivity {

          DataAdapter mAdapter1;
    ArrayList<tllist.List> data;
    RecyclerView recyclerView;
    String g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        String userid= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();
        Crashlytics.setUserIdentifier(userid);
        Crashlytics.setUserName(getname);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

          recyclerView=findViewById(R.id.recycleview);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        Date date = new Date();    // to get the date
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");    // getting date in this format
        String formattedDate = df.format(date.getTime());
        //   text.setText(formattedDate);
        g=formattedDate;
    }

    private void gg() {
        String userid= new sharedpreff(getApplicationContext()).login123();
        api mApiService = network.getRetrofit().create(api.class);
        Call<tllist> call = mApiService.tllist(userid,g);
        call.enqueue(new Callback<tllist>() {
            @Override
            public void onResponse(Call<tllist> call, Response<tllist> response) {
                System.out.println(call.request().url());
                data = new ArrayList<>(Arrays.asList(response.body().getList()));

                if(data.size()!=0){
                    System.out.println("ghfghfgh");
                    mAdapter1 = new DataAdapter(data, getApplicationContext());
                    recyclerView.setAdapter(mAdapter1);
                }else{
                    mAdapter1 = new DataAdapter(data, getApplicationContext());
                    recyclerView.setAdapter(mAdapter1);
                    Toast.makeText(detail.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<tllist> call, Throwable t) {

                Log.d("Error", t.getMessage());

            }
        });
                    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.date, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_user) {
            int mYear, mMonth, mDay, mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(detail.this,
                    new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                             String ff  =  String.format("%02d", monthOfYear+1 );
//                       Toast.makeText(detail.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                             g=dayOfMonth + "-" + ff + "-" + year;


                             //phone.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            gg();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    protected void onStart() {
        super.onStart();

        gg();

    }
    @Override
    protected void onResume() {
        super.onResume();
                gg();
    }


    private class DataAdapter
            extends RecyclerView.Adapter<  DataAdapter.ViewHolder> {
        private ArrayList<tllist.List> android, mFilteredList;

        private ArrayList<exelist.ExecutiveLocationList> item_list;
        Context gg;

        public DataAdapter(ArrayList<tllist.List> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;
        }

        @Override
        public   DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart2, viewGroup, false);
            return new   DataAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final  DataAdapter.ViewHolder viewHolder , final int i) {

            viewHolder.cname.setText(android.get(i).getCustomer_name());

//            System.out.println(android.get(i).getCustomer_name());
         viewHolder.ename.setText(android.get(i).getEmployee_name());

            viewHolder.su.setText(android.get(i).getExecutive_summary());
            viewHolder.typ.setText(android.get(i).getExecutive_transtype());
            viewHolder.code.setText(android.get(i).getDispo_code());


//          viewHolder.checkBox.setChecked(android.get(i).isSelected());
//          viewHolder.checkBox.setTag(android.get(i));
//         viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {

//                    AlertDialog alertDialog = new AlertDialog.Builder(gg).create();
//                    LayoutInflater inflater = (LayoutInflater)getApplicationContext().
//                    getSystemService(LAYOUT_INFLATER_SERVICE);
//                    final View popupView = inflater.inflate(R.layout.popup_window1, null);
////                    textView.setText(android.get(i).get);
////                    create the popup window
//                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
//                    int height = LinearLayout.LayoutParams.MATCH_PARENT;
//                    boolean focusable = true; // lets taps outside the popup also dismiss it
//                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
////                    WebView button=popupView.findViewById(R.id.web);
//                    TextView et=popupView.findViewById(R.id.et);
//                    TextView eti=popupView.findViewById(R.id.eti);
//                    TextView es=popupView.findViewById(R.id.es);
//                    TextView dc=popupView.findViewById(R.id.dc);
//                    ImageView  g=popupView.findViewById(R.id.ic_close);
//                    WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//                    lp.dimAmount=0.0f;
//                    alertDialog.getWindow().setAttributes(lp);
//                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//                    popupWindow.showAtLocation(v, Gravity.BOTTOM, 100, 100);
//                    g.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            popupWindow.dismiss();
//                        }
//                    });
//
//                    et.setText(android.get(i).getExecutive_transtype());
//                    eti.setText(android.get(i).getExecutive_time());
//                    es.setText(android.get(i).getExecutive_summary());
//                    dc.setText(android.get(i).getDispo_code());
//
//                    popupView.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
////                       popupWindow.dismiss();
//                            return true;
//                        }
//                    });
//
//
//
////         viewHolder.tv_name.setText(android.get(i).getCustomer_name());
////        if(android.get(i).getStatus().equals("1")){
////        Intent ii=new Intent(gg,MapsActivity.class);
////        ii.putExtra("name",android.get(i).getCustomer_address());
////        ii.putExtra("id",android.get(i).getId());
////        startActivity(ii);
////        }
////   else{
////         Toast.makeText(gg, "Already Finish", Toast.LENGTH_SHORT).show();
////                    }
//                }
//            });



        }
        @Override
        public int getItemCount() {

            return android.size();

        }


//   Filter getFilter() {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence charSequence) {
//                    String charString = charSequence.toString();
//                    if (charString.isEmpty()) {
//                        android = mFilteredList;
//                    } else {
//                        System.out.println(charString);
//                        ArrayList<exelist.ExecutiveLocationList> filteredList = new ArrayList<>();
//                        for (exelist.ExecutiveLocationList androidVersion : android) {
//                            if (androidVersion.getCustomer_name().toLowerCase().contains(charString)) {
//                                 filteredList.add(androidVersion);
//                            }
//                        }
//                        android = filteredList;
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = android;
//               return filterResults;
//                }
//                @Override
//                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                android = (ArrayList<exelist.ExecutiveLocationList>) filterResults.values;
//                notifyDataSetChanged();
//                }
//            };
//                      }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView ename, cname,su,code,typ ;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                ename = (TextView) view.findViewById(R.id.ename);
                cname = view.findViewById(R.id.cname);
                su=view.findViewById(R.id.su);
                code=view.findViewById(R.id.dispo);
                typ=view.findViewById(R.id.type);


            }
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
