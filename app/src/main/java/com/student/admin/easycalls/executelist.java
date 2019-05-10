package com.student.admin.easycalls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.student.admin.easycalls.gettersetter.exelist;
import com.student.admin.easycalls.gettersetter.list;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import com.student.admin.easycalls.shared.sharedpreff;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class executelist  extends AppCompatActivity {


    LinearLayout ff;
    Button login1, singup;
    EditText ed1, ed2;
    ImageView bb;
    RecyclerView recyclerView1;
        DataAdapter mAdapter1;
    ArrayList<exelist.ExecutiveLocationList> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.executivelist);
        bb=findViewById(R.id.nodata);
        recyclerView1 = findViewById(R.id.recycleview);
        final EditText editText = findViewById(R.id.search);
        final CheckBox checkbox = findViewById(R.id.checkbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
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

//        checkbox.setOnClickListener(new View.OnClickListener() {
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
//        });

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
                     if(data!=null){

                             mAdapter1.getFilter().filter("");


                     }


                } else {
                    mAdapter1.getFilter().filter(s.toString());

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading  please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(horizontalLayoutManager);
        final api mApiService = network.getRetrofit().create(api.class);

       String userid= new sharedpreff(getApplicationContext()).login123();
        Call<exelist> call = mApiService.list1(userid);
        call.enqueue(new Callback<exelist>() {
            @Override
            public void onResponse(Call<exelist> call, Response<exelist> response) {

                System.out.println(response.body());
                data = new ArrayList<>(Arrays.asList(response.body().getExecutiveLocationList()));

                if(data.size()!=0){

                }else{
                    bb.setVisibility(View.VISIBLE);
                }
                mAdapter1 = new  DataAdapter(data, executelist.this);
                recyclerView1.setAdapter(mAdapter1);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<exelist> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.getMessage());

            }
        });


    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class DataAdapter
            extends RecyclerView.Adapter< DataAdapter.ViewHolder> {
        private ArrayList<exelist.ExecutiveLocationList> android, mFilteredList;

        private ArrayList<exelist.ExecutiveLocationList> item_list;
        Context gg;

        public DataAdapter(ArrayList<exelist.ExecutiveLocationList> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;
        }

        @Override
        public  DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart1, viewGroup, false);
            return new  DataAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getCustomer_name());
           viewHolder.tv_version.setText(android.get(i).getCustomer_address());
//            viewHolder.checkBox.setChecked(android.get(i).isSelected());
//            viewHolder.checkBox.setTag(android.get(i));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(android.get(i).getStatus().equals("1")){

                        Intent ii=new Intent(gg,MapsActivity.class);
                        ii.putExtra("name",android.get(i).getCustomer_address());
                        ii.putExtra("id",android.get(i).getId());
                        startActivity(ii);
                    }
                    else{

                        Toast.makeText(gg, "Already Finish", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;
                    list.Employees model = (list.Employees) cb.getTag();
                    model.setSelected(cb.isChecked());
//                    android.get(i).setSelected(cb.isChecked());

                }
            });

        }
        @Override
        public int getItemCount() {
            return android.size();
        }
        Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        android = mFilteredList;
                    } else {
                        System.out.println(charString);
                        ArrayList<exelist.ExecutiveLocationList> filteredList = new ArrayList<>();
                        for (exelist.ExecutiveLocationList androidVersion : android) {
                            if (androidVersion.getCustomer_name().toLowerCase().contains(charString)) {
                                filteredList.add(androidVersion);
                            }
                        }
                          android = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = android;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                    android = (ArrayList<exelist.ExecutiveLocationList>) filterResults.values;
                    notifyDataSetChanged();

                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name, tv_version, tv_api_level, tv_name1;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox = view.findViewById(R.id.checkbox);
                tv_version=view.findViewById(R.id.tv1);
            }
        }
    }
}