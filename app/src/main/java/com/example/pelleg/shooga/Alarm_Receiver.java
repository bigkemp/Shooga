package com.example.pelleg.shooga;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Alarm_Receiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Boolean active = intent.getExtras().getBoolean("active");
Intent service_intent = new Intent(context,RingtonePlayService.class);
service_intent.putExtra("active",active);
context.startService(service_intent);





    }
}
