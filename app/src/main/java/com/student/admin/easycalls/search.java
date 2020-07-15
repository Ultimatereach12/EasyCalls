package com.student.admin.easycalls;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.student.admin.easycalls.gettersetter.enddate;
import com.student.admin.easycalls.gettersetter.searchem;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class search  extends AppCompatActivity {
    LinearLayout in, out;
    RecyclerView recyclerView1,recyclerView;
    ArrayList<searchem.Data> data;
    private static int resultCode = 40;
    ArrayList<enddate.End> data1;
    DataAdapter  mAdapter;
    int count12 =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        LinearLayout l1=findViewById(R.id.l1);
        LinearLayout l2=findViewById(R.id.l2);

        final String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycleview);
        final String userid= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Search");
        EditText search=findViewById(R.id.search);

        final EditText search1=findViewById(R.id.search1);

        if(sessionId.equals("1")){

            l2.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);

        }else if(sessionId.equals("3")){

            l2.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);

        }

        else{

            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);

        }


        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


//          Toast.makeText(search.this, s, Toast.LENGTH_SHORT).show();

                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(search.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManager);
                final api mApiService = network.getRetrofit().create(api.class);
                String o= String.valueOf(s);
                Call<searchem> call = mApiService.searchem(o,userid);
                call.enqueue(new Callback<searchem>() {
                    @Override
                    public void onResponse(Call<searchem> call, Response<searchem> response) {
//                        System.out.println(response.body());
                        System.out.println(call.request().url());
                        data = new ArrayList<>(Arrays.asList((response.body()).getData()));
                        if (data.size() != 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            count12=1;
                            mAdapter = new  DataAdapter(data,data1,getApplicationContext(),sessionId);
                            recyclerView.setAdapter(mAdapter);

                        } else{

//                          bb.setVisibility(View.VISIBLE);

                            Toast.makeText(search.this, "No data", Toast.LENGTH_SHORT).show();
                            data.clear();
                            recyclerView.setVisibility(View.GONE);
                            if (count12 == 1) {

                                mAdapter.notifyDataSetChanged();

                            }else{
                                System.out.println("test1");
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<searchem> call, Throwable t) {

//                    bar.setVisibility(View.GONE);
                        Log.d("Error", t.getMessage());


                    }
                });

            }
        });




        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(search.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(search.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(horizontalLayoutManager);
                                final api mApiService = network.getRetrofit().create(api.class);


                                ;


                                String o= String.valueOf( String.format("%02d",dayOfMonth) + "-" +String.format("%02d",monthOfYear+1)+ "-" + year);
                                search1.setText(o);

                                Call<enddate> call = mApiService.datesearch(o,userid);
                                call.enqueue(new Callback<enddate>() {
                                    @Override
                                    public void onResponse(Call<enddate> call, Response<enddate> response) {
//                                        System.out.println(response.body());
//                                        System.out.println(call.request().url());
                                        data1 = new ArrayList<>(Arrays.asList((response.body()).getEnd()));

                                        System.out.println(data1.size());

                                        if (data1.size()!=0) {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            count12=1;
                                            mAdapter=new DataAdapter(data,data1, getApplicationContext(),sessionId);
                                            recyclerView.setAdapter(mAdapter);

                                        }else {
                                            //                          bb.setVisibility(View.VISIBLE);
                                            Toast.makeText(search.this, "No data", Toast.LENGTH_SHORT).show();
                                            data1.clear();
                                            recyclerView.setVisibility(View.GONE);
                                            if (count12 == 1) {
                                                mAdapter.notifyDataSetChanged();
                                            }

                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<enddate> call, Throwable t) {

//                                       bar.setVisibility(View.GONE);
                                        Log.d("Error", t.getMessage());


                                    }
                                });





                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();






            }
        });


//        search1.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//
//
////                Toast.makeText(search.this, s, Toast.LENGTH_SHORT).show();
//
//                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(search.this, LinearLayoutManager.VERTICAL, false);
//                recyclerView.setLayoutManager(horizontalLayoutManager);
//                final api mApiService = network.getRetrofit().create(api.class);
//                String o= String.valueOf(s);
//                Call<enddate> call = mApiService.datesearch(o,userid);
//                call.enqueue(new Callback<enddate>() {
//                    @Override
//                    public void onResponse(Call<enddate> call, Response<enddate> response) {
//                        System.out.println(response.body());
//                        System.out.println(call.request().url());
//                        data1 = new ArrayList<>(Arrays.asList((response.body()).getEnd()));
//                        if (data1.size() != 0) {
//
//                            mAdapter = new  DataAdapter(data,data1, getApplicationContext());
//                            recyclerView.setAdapter(mAdapter);
//
//                        } else {
//    //                          bb.setVisibility(View.VISIBLE);
//                            Toast.makeText(search.this, "No data", Toast.LENGTH_SHORT).show();
//
//                           if(data1!=null){
//                               data1.clear();
//
//
//                           }
//
//
//
//
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<enddate> call, Throwable t) {
//
////                      bar.setVisibility(View.GONE);
//                        Log.d("Error", t.getMessage());
//
//
//                    }
//                });
//
//            }
//        });



    }
    private class DataAdapter extends RecyclerView.Adapter <DataAdapter.ViewHolder> {

        private ArrayList<searchem.Data> android, mFilteredList;
        private    ArrayList<enddate.End> android1;
        private ArrayList<searchem.Data> item_list;
        RecyclerView recy;
        String s;
        Context gg;
        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;


        public DataAdapter(ArrayList<searchem.Data> android,ArrayList<enddate.End> android1, Context g,String s) {

            if(android1!=null){

                this.android1 = android1;
                this.mFilteredList = android;
                this.gg = g;
                this.s=s;



            }else{

                this.android = android;
                this.mFilteredList = android;
                this.gg = g;
                this.s=s;

            }


//            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recy.getLayoutManager();
//            recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    totalItemCount = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
////                        if (onLoadMoreListener != null) {
////                            onLoadMoreListener.onLoadMore();
////                        }
////                        Toast.makeText(gg, "retre", Toast.LENGTH_SHORT).show();
//
//                        isLoading = true;
//                    }
//                }
//            });

        }

        @Override
        public  DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listofaccount, viewGroup, false);
            return new  DataAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final  DataAdapter.ViewHolder viewHolder, final int i) {

            if(android!=null){

                viewHolder.tv_name.setText(android.get(i).getCustomer_accno());
                viewHolder.tv_version.setText(android.get(i).getEnd_date());
                viewHolder.btc.setText(android.get(i).getBTC());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(android!=null) {
                            if(s.equals("3")){


                                Intent data = new Intent();
                                data.putExtra("PhoneNumber",android.get(i).getCustomer_accno());
                                setResult(resultCode, data);
                                finish();



                            }else{
                                Intent ii=new Intent(gg,accountdetails.class);
//                                ii.putExtra("name","");
//                                ii.putExtra("id", android.get(i).getEmployee_id());

                                ii.putExtra("id", android.get(i).getEmployee_id());
                                ii.putExtra("address",android.get(i).getCustomer_address());
                                ii.putExtra("name",android.get(i).getCustomer_name());
                                ii.putExtra("phone",android.get(i).getCustomer_phone());
                                ii.putExtra("acc",android.get(i).getCustomer_accno());
                                ii.putExtra("end",android.get(i).getEnd_date());
                                ii.putExtra("amount",android.get(i).getBTC());
                                ii.putExtra("amount1",android.get(i).getTo_be_collected());

                                startActivity(ii);
                            }


                        }else{

                            Intent ii=new Intent(gg,accountdetails.class);

                            ii.putExtra("id", android1.get(i).getEmployee_id());
                            ii.putExtra("address",android1.get(i).getCustomer_address());
                            ii.putExtra("name",android1.get(i).getCustomer_name());
                            ii.putExtra("phone",android1.get(i).getCustomer_phone());
                            ii.putExtra("acc",android1.get(i).getCustomer_accno());
                            ii.putExtra("amount",android1.get(i).getBTC());
                            ii.putExtra("end",android1.get(i).getEnd_date());
                            ii.putExtra("amount1",android1.get(i).getTo_be_collected());
                            startActivity(ii);
                        }
                    }
                });

            }else{

                viewHolder.tv_name.setText(android1.get(i).getCustomer_accno());
                viewHolder.tv_version.setText(android1.get(i).getEnd_date());
                viewHolder.btc.setText(android1.get(i).getTo_Be_Paid());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(android!=null) {
                            Intent ii=new Intent(gg,accountdetails.class);
//                            ii.putExtra("name","");
//                            ii.putExtra("id", android.get(i).getEmployee_id());
                            ii.putExtra("id", android.get(i).getEmployee_id());
                            ii.putExtra("address",android.get(i).getCustomer_address());
                            ii.putExtra("name",android.get(i).getCustomer_name());
                            ii.putExtra("phone",android.get(i).getCustomer_phone());
                            ii.putExtra("acc",android.get(i).getCustomer_accno());
                            ii.putExtra("amount",android.get(i).getBTC());
                            ii.putExtra("end",android.get(i).getEnd_date());
                            ii.putExtra("amount1",android.get(i).getTo_be_collected());
                            startActivity(ii);

                        }else{
                              Intent ii=new Intent(gg,accountdetails.class);
//                            ii.putExtra("name","");
//                            ii.putExtra("id", android1.get(i).getEmployee_id());

                            ii.putExtra("id", android1.get(i).getEmployee_id());
                            ii.putExtra("address",android1.get(i).getCustomer_address());
                            ii.putExtra("name",android1.get(i).getCustomer_name());
                            ii.putExtra("acc",android1.get(i).getCustomer_accno());
                            ii.putExtra("phone",android1.get(i).getCustomer_phone());
                            ii.putExtra("amount",android1.get(i).getBTC());
                            ii.putExtra("end",android1.get(i).getEnd_date());
                            ii.putExtra("amount1",android1.get(i).getTo_be_collected());
                               startActivity(ii);
                        }
                    }
                });
            }




//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));
//            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    CheckBox cb = (CheckBox) v;
//                    list.Employees model = (list.Employees) cb.getTag();
//                    model.setSelected(cb.isChecked());
//                    android.get(i).setSelected(cb.isChecked());
//
//                }
//            });

        }


        @Override
        public int getItemCount() {
            int o=0;
            if(android1!=null){
               o= android1.size();

            }else{
               o= android.size();
            }


            return o;
        }



//        Filter getFilter() {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence charSequence) {
//                    String charString = charSequence.toString();
//                    if (charString.isEmpty()) {
//                        android=mFilteredList;
//                    } else {
//                        System.out.println(charString);
//                        ArrayList<exelist.ExecutiveLocationList> filteredList = new ArrayList<>();
//                        fort (exelist.ExecutiveLocationList androidVersion : android) {
//                            if (androidVersion.getCustomer_name().toLowerCase().contains(charString)) {
//                                filteredList.add(androidVersion);
//                            }
//                        }
//                          android = filteredList;
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = android;
//                    return filterResults;
//                }
//
//                @Override
//                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                    android = (ArrayList<exelist.ExecutiveLocationList>) filterResults.values;
//                    notifyDataSetChanged();
//
//                }
//            };
//        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name, tv_version,btc,tv_name1;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
                btc = view.findViewById(R.id.btc);
            }
        }
 }



    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}