package com.student.admin.easycalls.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedpreff {

     Context context;

    public sharedpreff(Context context) {
        this.context = context;
    }

    public  void saveLoginDetails(String email, String password,String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("name", name);
        editor.commit();
    }

    public  void Token(String token ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", token);

        editor.commit();
    }

    public  void saveLoginDetails2(String g ) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("screen", g);
        editor.commit();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String  login123() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }

    public String  getname() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");
    }

    public String  gettoken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Token", "");
    }

    public String getEmail1() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");

    }

    public boolean isUserLogedOut() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;

    }

    public boolean isUserLogedOut1() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("pp", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("screen", "").isEmpty();
        return isEmailEmpty;

    }

    public void logout1() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("p", Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().commit();

    }

}