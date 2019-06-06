package com.student.admin.easycalls;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.student.admin.easycalls.gettersetter.customernotpaidlist;
import com.student.admin.easycalls.gettersetter.exelist;
import com.student.admin.easycalls.gettersetter.list;
import com.student.admin.easycalls.gettersetter.paidlist;
import com.student.admin.easycalls.gettersetter.visit;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;
import java.util.ArrayList;
import java.util.Arrays;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class executelist extends AppCompatActivity {

    LinearLayout ff;
    Button login1, singup;
    EditText ed1, ed2;
    ImageView bb;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    DataAdapter mAdapter1;
    DataAdapter1 mAdapter2;
    DataAdapter2 mAdapter3;
    ArrayList<visit.List> data;
    ArrayList<customernotpaidlist.List> data1;
    ArrayList<paidlist.List> data2;
    ProgressBar bar;
    LinearLayout open, toggle;
    LinearLayout progress, close;
    TextView open1, open2, close1, close2, progress1, progress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.executivelist);
        String userid1 = new sharedpreff(getApplicationContext()).login123();
        String getname = new sharedpreff(getApplicationContext()).getname();
        Crashlytics.setUserIdentifier(userid1);
        Crashlytics.setUserName(getname);
        bb = findViewById(R.id.nodata);
        bar = findViewById(R.id.bar);
        recyclerView1 = findViewById(R.id.recycleview);
        recyclerView2 = findViewById(R.id.recycleview1);
        recyclerView3 = findViewById(R.id.recycleview2);


        final EditText editText = findViewById(R.id.search);
        final CheckBox checkbox = findViewById(R.id.checkbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        open = findViewById(R.id.open);
        progress = findViewById(R.id.progress);
        close = findViewById(R.id.close);
        open1 = findViewById(R.id.open1);
        open2 = findViewById(R.id.open12);
        close1 = findViewById(R.id.close1);
        close2 = findViewById(R.id.close2);
        progress1 = findViewById(R.id.progress1);
        progress2 = findViewById(R.id.progress2);
        toggle = findViewById(R.id.toggle);
        open.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        open1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        open2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        getlist();

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gg("1");

                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);

                if (data1 == null) {

                    getlist();
                }

            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gg("2");
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView3.setVisibility(View.GONE);

                if (data1 == null) {

                    bar.setVisibility(View.VISIBLE);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(executelist.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView2.setLayoutManager(horizontalLayoutManager);
                    final api mApiService = network.getRetrofit().create(api.class);
                    String userid = new sharedpreff(getApplicationContext()).login123();
                    Call<customernotpaidlist> call = mApiService.nopay("64");
                    call.enqueue(new Callback<customernotpaidlist>() {
                        @Override
                        public void onResponse(Call<customernotpaidlist> call, Response<customernotpaidlist> response) {
                            System.out.println(response.body());
                            System.out.println(call.request().url());
                            data1 = new ArrayList<>(Arrays.asList(response.body().getList()));
                            if (data1.size() != 0) {

                            } else {
//                                bb.setVisibility(View.VISIBLE);
                                Toast.makeText(executelist.this, "No data", Toast.LENGTH_SHORT).show();
                            }
                            mAdapter2 = new DataAdapter1(data1, executelist.this);
                            recyclerView2.setAdapter(mAdapter2);

                            bar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<customernotpaidlist> call, Throwable t) {


                            bar.setVisibility(View.GONE);
                            Log.d("Error", t.getMessage());


                        }
                    });
                }

//        Toast.makeText(executelist.this, "2", Toast.LENGTH_SHORT).show();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.VISIBLE);
                gg("3");

                if (data2 == null) {

                    bar.setVisibility(View.VISIBLE);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(executelist.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView3.setLayoutManager(horizontalLayoutManager);
                    final api mApiService = network.getRetrofit().create(api.class);

                    String userid = new sharedpreff(getApplicationContext()).login123();
                    Call<paidlist> call = mApiService.paylist("64");
                    call.enqueue(new Callback<paidlist>() {
                        @Override
                        public void onResponse(Call<paidlist> call, Response<paidlist> response) {
                            System.out.println(response.body());
                            System.out.println(call.request().url());
                            data2 = new ArrayList<>(Arrays.asList(response.body().getList()));

                            if (data2.size() != 0) {

                            } else {
//                            bb.setVisibility(View.VISIBLE);
                                Toast.makeText(executelist.this, "No data", Toast.LENGTH_SHORT).show();
                            }
                            mAdapter3 = new DataAdapter2(data2, executelist.this);


                            recyclerView3.setAdapter(mAdapter3);

                            bar.setVisibility(View.GONE);


                        }

                        @Override
                        public void onFailure(Call<paidlist> call, Throwable t) {


                            bar.setVisibility(View.GONE);
                            Log.d("Error", t.getMessage());


                        }
                    });
                }
//           Toast.makeText(executelist.this, "3", Toast.LENGTH_SHORT).show();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setEnabled(true);
            }
        });

        final Button button = findViewById(R.id.remove);

//      checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkbox.isChecked()) {
//                    for exelist.ExecutiveLocationList: data) {
//                        model.setSelected(true);
//                    }
//                } else {
//                    for (list.Employees model : data) {
//                        model.setSelected(false);
//                    }
//                }
//                mAdapter1.notifyDataSetChanged();
//            }
//      });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    if (data != null) {

//                 mAdapter1.getFilter().filter("");


                    }


                } else {

//                    mAdapter1.getFilter().filter(s.toString());

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    private void getlist() {

       bar.setVisibility(View.VISIBLE);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(executelist.this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(horizontalLayoutManager);
        final api mApiService = network.getRetrofit().create(api.class);

        String userid = new sharedpreff(getApplicationContext()).login123();
        Call<visit> call = mApiService.baselist("64");
        call.enqueue(new Callback<visit>() {
            @Override
            public void onResponse(Call<visit> call, Response<visit> response) {
                System.out.println(response.body());
                System.out.println(call.request().url());

                data = new ArrayList<>(Arrays.asList(response.body().getList()));
                if (data.size() != 0) {

                } else {
                    Toast.makeText(executelist.this, "No data", Toast.LENGTH_SHORT).show();
                }
                mAdapter1 = new DataAdapter(data, executelist.this);
                recyclerView1.setAdapter(mAdapter1);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<visit> call, Throwable t) {

                bar.setVisibility(View.GONE);
                Log.d("Error", t.getMessage());


            }
        });
    }

    @SuppressLint("ResourceType")
    private void gg(String s) {

        if (s.equals("1")) {

            open.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            open1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            open2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));


            close.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            close1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            close2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


            progress.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            progress1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            progress2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


        } else if (s.equals("2")) {

            open.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            open1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            open2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            progress.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            progress1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            progress2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

            close.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            close1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            close2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


        } else if (s.equals("3")) {

            open.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            open1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            open2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

            close.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            close1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            close2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

            progress.setBackgroundResource(R.drawable.control_switch_background_selector_middle);
            progress1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            progress2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));


        }

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        private ArrayList<visit.List> android, mFilteredList;

        private ArrayList<exelist.ExecutiveLocationList> item_list;
        Context gg;

        public DataAdapter(ArrayList<visit.List> android, Context g) {
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

            viewHolder.tv_name.setText(android.get(i).getCustomer_name());
            viewHolder.tv_version.setText(android.get(i).getCustomer_accno());
//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        Intent ii=new Intent(gg,MapsActivity.class);
                        ii.putExtra("name","");
                        ii.putExtra("id", android.get(i).getId());
                        startActivity(ii);

                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;
                    list.Employees model = (list.Employees) cb.getTag();
                    model.setSelected(cb.isChecked());
//                  android.get(i).setSelected(cb.isChecked());

                }
            });

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
//                        for (exelist.ExecutiveLocationList androidVersion : android) {
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
            private TextView tv_name, tv_version, tv_api_level, tv_name1;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
            }
        }
    }


    private class DataAdapter1 extends RecyclerView.Adapter<DataAdapter1.ViewHolder> {

        private ArrayList<customernotpaidlist.List> android, mFilteredList;

        private ArrayList<exelist.ExecutiveLocationList> item_list;
        Context gg;

        public DataAdapter1(ArrayList<customernotpaidlist.List> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;
        }

        @Override
        public DataAdapter1.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart1, viewGroup, false);
            return new DataAdapter1.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataAdapter1.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getCustomer_name());
            viewHolder.tv_version.setText(android.get(i).getDispo_code());
//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        Intent ii=new Intent(gg,MapsActivity.class);
                        ii.putExtra("name","");
                        ii.putExtra("id",android.get(i).getId());
                        startActivity(ii);



                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;
                    list.Employees model = (list.Employees) cb.getTag();
                    model.setSelected(cb.isChecked());
//                   android.get(i).setSelected(cb.isChecked());

                }
            });

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
//                        for (exelist.ExecutiveLocationList androidVersion : android) {
//                            if (androidVersion.getCustomer_name().toLowerCase().contains(charString)) {
//                                filteredList.add(androidVersion);
//                            }
//                        }
//                        android = filteredList;
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
            private TextView tv_name, tv_version, tv_api_level, tv_name1;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
            }
        }
    }


    private class DataAdapter2 extends RecyclerView.Adapter<DataAdapter2.ViewHolder> {

        private ArrayList<paidlist.List> android, mFilteredList;

        private ArrayList<paidlist.List> item_list;
        Context gg;

        public DataAdapter2(ArrayList<paidlist.List> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;
        }

        @Override
        public DataAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart1, viewGroup, false);
            return new DataAdapter2.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataAdapter2.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getCustomer_name());
            viewHolder.tv_version.setText(android.get(i).getExecutive_summary());

//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(android.get(i).getStatus().equals("1")){
//
//                        Intent ii=new Intent(gg,MapsActivity.class);
//                        ii.putExtra("name",android.get(i).getCustomer_address());
//                        ii.putExtra("id",android.get(i).getId());
//                        startActivity(ii);
//                    }
//                    else{
//                        Toast.makeText(gg, "Already Finish", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;
                    list.Employees model = (list.Employees) cb.getTag();
                    model.setSelected(cb.isChecked());
//                  android.get(i).setSelected(cb.isChecked());

                }
            });

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
//                        ArrayList <exelist.ExecutiveLocationList>  filteredList = new ArrayList<>();
//                        for (exelist.ExecutiveLocationList androidVersion : android) {
//                            if (androidVersion.getCustomer_name().toLowerCase().contains(charString)) {
//                                filteredList.add(androidVersion);
//                            }
//                        }
//                        android = filteredList;
//                    }
//                    FilterResults filterResults = new FilterResults();
//                    filterResults.values = android;
//                    return filterResults;
//                }
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
            private TextView tv_name, tv_version, tv_api_level, tv_name1;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version = view.findViewById(R.id.tv1);
            }
        }
    }


}