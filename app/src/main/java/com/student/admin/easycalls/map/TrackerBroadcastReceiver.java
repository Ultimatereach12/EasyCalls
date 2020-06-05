

package com.student.admin.easycalls.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.student.admin.easycalls.MapsActivity;


public class TrackerBroadcastReceiver extends BroadcastReceiver {


    public TrackerBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent start = new Intent(context, MapsActivity.class);
            start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(start);
        }
    }
}
