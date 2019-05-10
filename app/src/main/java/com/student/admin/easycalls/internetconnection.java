package com.student.admin.easycalls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class internetconnection {

    protected static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else {
            return false;
        }
    }
}
