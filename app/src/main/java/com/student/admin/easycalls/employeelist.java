package com.student.admin.easycalls;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.student.admin.easycalls.gettersetter.list;
import com.student.admin.easycalls.gettersetter.login;
import com.student.admin.easycalls.model.api;
import com.student.admin.easycalls.model.network;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static java.security.AccessController.getContext;
public class employeelist  extends AppCompatActivity {
    LinearLayout ff;
    Button login1, singup;
    EditText ed1, ed2;
    RecyclerView recyclerView1;
    DataAdapter mAdapter1;
    ArrayList<list.Employees> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeelist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView1= findViewById(R.id.recycleview);
        final EditText editText= findViewById(R.id.search);
        final CheckBox checkbox= findViewById(R.id.checkbox);
        final Button button= findViewById(R.id.remove);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editText.setEnabled(true);
            }
        });
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkbox.isChecked()) {
                    for (list.Employees model : data) {
                             model.setSelected(true);
                    }

                } else {

                    for (list.Employees  model : data) {
                        model.setSelected(false);
                    }

                }
                mAdapter1.notifyDataSetChanged();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){

                    if(data.size()!=0){
                        mAdapter1.getFilter().filter("");
                    }

                }else {

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
        final api mApiService =  network.getRetrofit().create(api.class);
        Call<list> call = mApiService.list();
        call.enqueue(new Callback<list>() {
            @Override
            public void onResponse(Call<list> call, Response<list> response) {

                System.out.println(call.request().url());
                System.out.println(response.body());
                data = new ArrayList<>(Arrays.asList(response.body().getEmployees()));
                mAdapter1= new DataAdapter(data,employeelist.this);
                recyclerView1.setAdapter(mAdapter1);
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<list> call, Throwable t) {
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
        private ArrayList<list.Employees> android, mFilteredList;

        private ArrayList<list.Employees> item_list;
        Context gg;

        public DataAdapter(ArrayList<list.Employees> android, Context g) {
            this.android = android;
            this.mFilteredList = android;
            this.gg = g;
        }

        @Override
        public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentcart, viewGroup, false);
            return new DataAdapter.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.tv_name.setText(android.get(i).getEmployee_name());
            viewHolder.tv_version.setText(android.get(i).getEmployee_email());
            viewHolder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+android.get(i).getEmployee_phone()));
                    startActivity(intent);

                }
            });


            viewHolder.checkBox.setChecked(android.get(i).isSelected());
            viewHolder.checkBox.setTag(android.get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final String t[]={"Landline" , "Mobile" };
                    LayoutInflater li = LayoutInflater.from(gg);
                    final View promptsView = li.inflate(R.layout.prompts, null);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            gg);
                    alertDialogBuilder.setCancelable(false);
                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);


                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.name);
                    final EditText phone = (EditText) promptsView
                            .findViewById(R.id.phone);

                    final EditText address = (EditText) promptsView
                             .findViewById(R.id.Address);

                    final EditText accno = (EditText) promptsView
                            .findViewById(R.id.accno);

                    final Button cencel = (Button) promptsView
                            .findViewById(R.id.cencel);

                    final MaterialSpinner test = (MaterialSpinner) promptsView
                            .findViewById(R.id.spinner1);

                    final Button submit = (Button) promptsView
                            .findViewById(R.id.submit);

                          test.setItems(t);

                          phone.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  int mYear, mMonth, mDay, mHour, mMinute;
                                  final Calendar c = Calendar.getInstance();
                                  mYear = c.get(Calendar.YEAR);
                                  mMonth = c.get(Calendar.MONTH);
                                  mDay = c.get(Calendar.DAY_OF_MONTH);


                                  DatePickerDialog datePickerDialog = new DatePickerDialog(gg,
                                          new DatePickerDialog.OnDateSetListener() {

                                              @Override
                                              public void onDateSet(DatePicker view, int year,
                                                                    int monthOfYear, int dayOfMonth) {

                                                  phone.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                              }
                                          }, mYear, mMonth, mDay);
                                  datePickerDialog.show();
                              }
                          });


                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    cencel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            internetconnection internetconnection = new internetconnection();


                            if (internetconnection.isOnline(gg)) {


                                String user= userInput.getText().toString();
                                String phone1= phone.getText().toString();
                                String address1= address.getText().toString();
                                String accno1=   accno.getText().toString();
                                int type1= test.getSelectedIndex();
                                if(user.length()>2&&phone1.length()>4&&address1.length()>4&&accno1.length()>4){

                                    String r=userInput.getText().toString();
                                    final ProgressDialog progressDialog = new ProgressDialog( gg);
                                    progressDialog.setMessage("Loading ...");
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();
                                    api mApiService = network.getRetrofit().create(api.class);
                                    Call<login> call = mApiService.exee(android.get(i).getId(),user,address1,accno1,phone1,t[type1]);
                                    call.enqueue(new Callback<login>() {
                                        @Override
                                        public void onResponse(Call<login> call, Response<login> response) {
                                            System.out.println(call.request().url());
                                            Toast.makeText( gg,  response.body().getResponse().getResponse_message(), Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                            progressDialog.dismiss();

                                        }
                                        @Override
                                        public void onFailure(Call<login> call, Throwable t) {
                                            Log.d("Error",t.getMessage());
                                            Toast.makeText( gg , "network error", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            alertDialog.dismiss();
                                        }
                                    });


                                }else{

                                    Toast.makeText(getApplicationContext(), "click ok ", Toast.LENGTH_SHORT).show();
                                }



                            }else{
                                Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



// create alert dialog
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(42,184,204)));
//            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);


//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
          //  show it
                    alertDialog.show();
                }
            });


            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;
                    list.Employees  model=(list.Employees) cb.getTag();
                    model.setSelected(cb.isChecked());
                    android.get(i).setSelected(cb.isChecked());

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
                        android=mFilteredList;
                    } else {
                        System.out.println(charString);
                        ArrayList<list.Employees> filteredList = new ArrayList<>();
                        for(list.Employees androidVersion : android) {
                            if(androidVersion.getEmployee_name().toLowerCase().contains(charString)) {
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

                    android=(ArrayList<list.Employees>) filterResults.values;
                    notifyDataSetChanged();

                }
            };

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_name, tv_version, tv_api_level, tv_name1;
            ImageView phone;
            CheckBox checkBox;

            public ViewHolder(View view) {
                super(view);
                tv_version = (TextView) view.findViewById(R.id.tv1);
                tv_name = (TextView) view.findViewById(R.id.tv);
                checkBox=view.findViewById(R.id.checkbox);
                phone=view.findViewById(R.id.phone);
            }
        }
    }



}

