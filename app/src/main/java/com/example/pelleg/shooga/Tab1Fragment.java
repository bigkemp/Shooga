package com.example.pelleg.shooga;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";
    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context;
    ArrayList<Alarm> arrayList;
    LocalSQL myDb;
    ListView listView;
    ListViewAdapter adapter;
    private Button btnTEST;
    PendingIntent pendingIntent;
    boolean alarmup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab1_fragment, container, false);
        context = view.getContext();
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();
        final Intent intent = new Intent(this.context, Alarm_Receiver.class);
        listView = (ListView) view.findViewById(R.id.lvAlarms);
        myDb = new LocalSQL(context);
        arrayList = new ArrayList<Alarm>();
        arrayList = getData2Array();
        if (arrayList != null){

            adapter = new ListViewAdapter(context, arrayList);
            ListView listView = (ListView) view.findViewById(R.id.lvAlarms);
            listView.setAdapter(adapter);
        }
        btnTEST = (Button) view.findViewById(R.id.btnADD);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Choose what to change:");
                dialog.setContentView(R.layout.dialog_timepicker);
                dialog.create();
                final Button btnSET = (Button) dialog.findViewById(R.id.btnSET);
                timePicker = (TimePicker) dialog.findViewById(R.id.timePicker); timePicker.setIs24HourView(true);
                btnSET.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        Random randCODE = new Random();
                        int  num = randCODE.nextInt(5000) + 1;
                        intent.putExtra("active",true);

                        alarmup = (PendingIntent.getBroadcast(context,num,intent,PendingIntent.FLAG_NO_CREATE ) != null);
                        if (alarmup)
                            Log.d("myTag","Alarm is already active "+num);
                        else
                            Log.d("myTag","Alarm is dead");

                        pendingIntent = PendingIntent.getBroadcast(context,num,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY, pendingIntent);
                        alarmup = (PendingIntent.getBroadcast(context,num,intent,PendingIntent.FLAG_NO_CREATE ) != null);
                        if (alarmup)
                            Log.d("myTag","Alarm is already active "+num);
                        else
                            Log.d("myTag","Alarm is dead");
                        myDb.insertData(String.valueOf(num),"ALARM",String.valueOf(timePicker.getHour())+ ":"+ String.valueOf( timePicker.getMinute()),true,true   );
                        arrayList.clear();
                        arrayList = getData2Array();
                        if (arrayList != null){
                             adapter = new ListViewAdapter(context, arrayList);
                             listView.setAdapter(adapter);
                        }
dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Alarm item = (Alarm) adapter.getItem(position);
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Choose what to change:");
        dialog.setContentView(R.layout.dialog_options);
        dialog.create();

//        EditText EDITname = (EditText) dialog.findViewById(R.id.etNAMEEDIT);
//        ToggleButton EDITrepeat = (ToggleButton) dialog.findViewById(R.id.sREPEAT);
//        ToggleButton EDIToffon = (ToggleButton) dialog.findViewById(R.id.sONOFF);
//        ToggleButton EDITampm = (ToggleButton) dialog.findViewById(R.id.sAMPM);
//        Button EDITsave = (Button) dialog.findViewById(R.id.btnSAVEEDIT);
        Button EDITdelete = (Button) dialog.findViewById(R.id.btnDELETE);
        Button EDITcancel = (Button) dialog.findViewById(R.id.btnCANCELEDIT);
//        NumberPicker EDIThour = (NumberPicker) dialog.findViewById(R.id.npHOUR);
//        EDIThour.setMaxValue(23);
//        EDIThour.setMinValue(0);
//        NumberPicker EDITminute = (NumberPicker) dialog.findViewById(R.id.npMINUTE);
//        EDITminute.setMaxValue(59);
//        EDITminute.setMinValue(00);

//fill items details:
//        EDITname.setText(item.getName());
//        if (item.getRepeat().toString().matches("Repeat"))
//            EDITrepeat.setChecked(true);
//        else EDITrepeat.setChecked(false);
//        if (item.getStatue().toString().matches("ON"))
//            EDIToffon.setChecked(true);
//        else EDIToffon.setChecked(false);

        //


EDITcancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});



        EDITdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                myDb.deleteData(item.getCode());
                intent.putExtra("active",false);
                context.sendBroadcast(intent);
                alarmup = (PendingIntent.getBroadcast(context,Integer.parseInt(item.getCode()),intent,PendingIntent.FLAG_NO_CREATE ) != null);
                if (alarmup)
                    Log.d("myTag","Alarm is already active "+ Integer.parseInt(item.getCode()));
                else
                    Log.d("myTag","Alarm is dead");
                pendingIntent = PendingIntent.getBroadcast(context,Integer.parseInt(item.getCode()),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();

                arrayList.clear();
                arrayList = getData2Array();
                if (arrayList != null){
                    adapter = new ListViewAdapter(context, arrayList);
                    listView.setAdapter(adapter);
                }
                alarmup = (PendingIntent.getBroadcast(context,Integer.parseInt(item.getCode()),intent,PendingIntent.FLAG_NO_CREATE ) != null);
                if (alarmup)
                    Log.d("myTag","Alarm is already active "+ Integer.parseInt(item.getCode()));
                else
                    Log.d("myTag","Alarm is dead");

            }
        });

        dialog.show();
    }
});
        return view;
    }




    private ArrayList<Alarm> getData2Array() {

        Cursor res;
        try {
             res = myDb.getAllData();
        }catch (Exception e){
            showMessage("Error", "Nothing found");
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



    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    private boolean converter2bool(String value){
        if (value.matches("true"))
            return true;
        else
            return false;
    }
}