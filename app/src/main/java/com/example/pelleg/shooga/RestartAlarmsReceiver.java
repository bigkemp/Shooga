package com.example.pelleg.shooga;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartAlarmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){

            Intent i = new Intent(context,BootService.class);
            ComponentName service = context.startService(i);

            if (null == service){

                Log.e("myTag","Could not start service ");
            }else{
                Log.e("myTag","Successful");
            }
        }else  {

            Log.e("myTag","Received unexpected intent "+intent.toString());
        }


    }
}
