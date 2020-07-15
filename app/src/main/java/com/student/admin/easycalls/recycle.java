package com.student.admin.easycalls;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.student.admin.easycalls.gettersetter.endlist;
import com.student.admin.easycalls.gettersetter.followlist;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;
import java.util.ArrayList;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recycle   extends AppCompatActivity {

    LinearLayout in,out;
    RecyclerView  recyclerView1,recyclerView;
    ArrayList<followlist.End> data;
    ArrayList<endlist.End> data1;
    DataAdapter mAdapter1;
    DataAdapter1 mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recye);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        String userid= new sharedpreff(getApplicationContext()).login123();
        String getname= new sharedpreff(getApplicationContext()).getname();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        recyclerView = findViewById(R.id.recycleview);
        recyclerView1= findViewById(R.id.recycleview1);

        TextView toolbar_title=findViewById(R.id.toolbar_title);

        if(sessionId.equals("1")){

            toolbar_title.setText("Today Follow Up");
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(recycle.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            final api mApiService = network.getRetrofit().create(api.class);

            Call<followlist> call = mApiService.followlist(userid);
            call.enqueue(new Callback<followlist>() {
                @Override
                public void onResponse(Call<followlist> call, Response<followlist> response) {
//                    System.out.println(response.body());
                    System.out.println(call.request().url());
                    data = new ArrayList<>(Arrays.asList(response.body().getEnd()));
                    if (data.size() != 0) {

                    } else {
//                                bb.setVisibility(View.VISIBLE);

                      Toast.makeText(recycle.this, "No data", Toast.LENGTH_SHORT).show();

                    }
                    mAdapter1 = new  DataAdapter(data, getApplicationContext());
                    recyclerView.setAdapter(mAdapter1);
                }

                @Override
                public void onFailure(Call<followlist> call, Throwable t) {

//                    bar.setVisibility(View.GONE);
                    Log.d("Error", t.getMessage());


                }
            });
        }
        else if(sessionId.equals("2"))
        {
            toolbar_title.setText("Today End Date");
            recyclerView1.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(recycle.this, LinearLayoutManager.VERTICAL, false);
            recyclerView1.setLayoutManager(horizontalLayoutManager);
            final api mApiService = network.getRetrofit().create(api.class);

            Call<endlist> call = mApiService.endlist(userid);
            call.enqueue(new Callback<endlist>() {
                @Override
                public void onResponse(Call<endlist> call, Response<endlist> response) {
//                    System.out.println(response.body());
                    System.out.println(call.request().url());
                    data1 = new ArrayList<>(Arrays.asList(response.body().getEnd()));
                    if (data1.size() != 0) {

                    } else {
//                                bb.setVisibility(View.VISIBLE);
                        Toast.makeText(recycle.this, "No data", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter2 = new  DataAdapter1(data1, getApplicationContext());
                    recyclerView1.setAdapter(mAdapter2);
                }
                @Override
                public void onFailure(Call<endlist> call, Throwable t) {

//                    bar.setVisibility(View.GONE);
                    Log.d("Error", t.getMessage());


                }
            });
        }
    }

    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        private ArrayList<followlist.End> android, mFilteredList;

        private ArrayList<followlist.End> item_list;

        RecyclerView recy;

        Context gg;

        private boolean isLoading;


        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;


        public DataAdapter( ArrayList<followlist.End> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;


}

        @Override
        public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart1, viewGroup, false);
            return new DataAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getDispo_code());
            viewHolder.tv_version.setText(android.get(i).getExecutive_other());
            viewHolder.btc.setText(android.get(i).getBTC());
            viewHolder.end.setText(android.get(i).getAccount_number());

//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

            if(android.get(i).getDispo_code().equals("PTP")){
          viewHolder.itemView.setBackgroundColor(Color.parseColor("#e3181e"));
            }


              viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {

//                    Toast.makeText(gg, android.get(i).getBTC(), Toast.LENGTH_SHORT).show();
                       String h;
                     if(android.get(i).getBTC()==null||android.get(i).getBTC().equals("")){
                        h="0";
                    }else{

                        h=android.get(i).getBTC();
                    }

                    String h1;
                    if(android.get(i).getTo_be_collect()==null||android.get(i).getTo_be_collect().equals("")){
                        h1="0";

                    }else{

                        h1=android.get(i).getTo_be_collect();
                    }

                    Intent ii=new Intent(gg,MapsActivity.class);
                    ii.putExtra("name",h);
                    ii.putExtra("address",android.get(i).getExecutive_location_lat());
                    ii.putExtra("end",android.get(i).getExecutive_other());
                    ii.putExtra("name1",h1);
                    ii.putExtra("id", android.get(i).getTeam_leader_id());
                    startActivity(ii);

                }
            });
//            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    CheckBox cb = (CheckBox) v;
//                    list.Employees model = (list.Employees) cb.getTag();
//                    model.setSelected(cb.isChecked());
////                  android.get(i).setSelected(cb.isChecked());
//
//                }
//            });


        }

        @Override
        public int getItemCount() {

            return android.size();

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
//                        ArrayList<exelist.ExecutiveLocat  ionList> filteredList = new ArrayList<>();
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
//
//                    android = (ArrayList<exelist.ExecutiveLocationList>) filterResults.values;
//                    notifyDataSetChanged();
//
//                }
//            };
//        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name, tv_version, btc, end;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
                btc = view.findViewById(R.id.btc);
                end = view.findViewById(R.id.end);
            }
        }
    }



    private class DataAdapter1 extends RecyclerView.Adapter<DataAdapter1.ViewHolder> {

        private ArrayList<endlist.End> android, mFilteredList;

        private ArrayList<endlist.End> item_list;

        RecyclerView recy;

        Context gg;

        private boolean isLoading;


        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;


        public DataAdapter1( ArrayList<endlist.End> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;




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
        public DataAdapter1.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart1, viewGroup, false);
            return new DataAdapter1.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataAdapter1.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getCustomer_name());
            viewHolder.tv_version.setText(android.get(i).getCustomer_address());
            viewHolder.btc.setText(android.get(i).getBTC());
            viewHolder.end.setText(android.get(i).getCustomer_accno());
//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii=new Intent(gg,MapsActivity.class);
                    ii.putExtra("name",android.get(i).getBTC());
                    ii.putExtra("address",android.get(i).getCustomer_address());
                    ii.putExtra("end",android.get(i).getEnd_date());
                    ii.putExtra("name1",android.get(i).getTo_be_collected());
                    ii.putExtra("id", android.get(i).getEmployee_id());
                    startActivity(ii);
                }
            });
//            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    CheckBox cb = (CheckBox) v;
//                    list.Employees model = (list.Employees) cb.getTag();
//                    model.setSelected(cb.isChecked());
////                  android.get(i).setSelected(cb.isChecked());
//                }
//            });
        }
        @Override
        public int getItemCount() {
            return android.size();
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
//                  FilterResults filterResults = new FilterResults();
//                  filterResults.values = android;
//                  return filterResults;
//                }
//                @Override
//                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                    android = (ArrayList<exelist.ExecutiveLocationList>) filterResults.values;
//                    notifyDataSetChanged();
//                }
//            };
//        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name, tv_version, btc, end;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
                btc = view.findViewById(R.id.btc);
                end = view.findViewById(R.id.end);
            }
        }
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
