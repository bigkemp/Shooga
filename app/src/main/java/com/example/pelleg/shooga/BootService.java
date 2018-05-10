package com.example.pelleg.shooga;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class BootService extends IntentService{


    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context=this;
    ArrayList<Alarm> arrayList;
    LocalSQL myDb;
    PendingIntent pendingIntent;



    public BootService() {
        super("BootService");
    }




    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        myDb = new LocalSQL(context);
        arrayList = new ArrayList<Alarm>();
        arrayList = getData2Array();
        Log.e("myTag","BOOT: in DB found: "+arrayList.size());
        Calendar calendar;
        Intent intent2;
        for (Alarm alarm : arrayList) {
            Log.e("myTag","BOOT: alarm code:"+alarm.getCode()+" and name:"+alarm.getName());

            intent2 = new Intent(this.context, Alarm_Receiver.class);
            calendar = Calendar.getInstance();
            int hour = converterHOUR2int(alarm.getTime().toString(),"hour");
            int minute = converterHOUR2int(alarm.getTime().toString(),"minute");
            Log.e("myTag","BOOT: settings times, hour:"+hour+"and minute:"+minute);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            Log.e("myTag","BOOT: calendar is set");
            int  num = Integer.parseInt(alarm.getCode());
            Log.e("myTag","BOOT: code is set:"+num);
            intent2.putExtra("active",true);
            pendingIntent = PendingIntent.getBroadcast(context,num,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.e("myTag","BOOT:set alarm is done");
        }

    }

    private ArrayList<Alarm> getData2Array() {

        Cursor res;
        try {
            res = myDb.getAllData();
        }catch (Exception e){
            Log.e("Error", "Nothing found");
            return null;
        }

//        if (res.getCount() == 0) {
//            // show message
//
//        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            // buffer.append("Id :"+ res.getString(0)+"\n");
            Boolean tempREPEAT =converter2bool(res.getString(3));
            Boolean tempSTATUE =converter2bool(res.getString(4));
            Alarm info = new Alarm(res.getString(0),res.getString(1),res.getString(2),tempREPEAT,tempSTATUE);
            arrayList.add(info);
        }

//        // Show all data
        //  showMessage("All Data was loaded","Now Showing Data...");

        return arrayList;
    }


    private int converterHOUR2int(String value,String hourORminute){
        if (hourORminute.matches("hour")){
            String[] splitted = value.split(":");
        int answer = Integer.parseInt(splitted[0]);

            return answer ;
        }

        else if (hourORminute.matches("minute")){
            String[] splitted = value.split(":");
            int answer =Integer.parseInt(splitted[1]);

            return answer ;
        }
return 0;
    }


    private boolean converter2bool(String value){
        if (value.matches("true"))
            return true;
        else
            return false;
    }
}
