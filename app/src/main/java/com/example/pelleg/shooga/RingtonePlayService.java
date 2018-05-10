package com.example.pelleg.shooga;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.Provider;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RingtonePlayService extends Service{
    int start_id;
MediaPlayer mediaPlayer;
 boolean isRunning;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), AlarmBeGone.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Time for your mother freaking check up" + "!")
                .setContentText("Click me now or i'll tell your mom youre alittle lying bitch")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        Log.e("in the service","startcommand");
        Boolean state = intent.getExtras().getBoolean("active");
       assert state!= null;
        if (state)
            start_id=1;
        else
            start_id=0;

try {
    if (mediaPlayer.isPlaying())
        isRunning = true;
    else
        isRunning = false;
}
catch(Exception e ) {
    isRunning = false;



}



        if(!this.isRunning && start_id == 1) {
            Log.e("if there was not sound ", " and you want start");

            mediaPlayer = MediaPlayer.create(this, R.raw.ring);
            mediaPlayer.start();
            mNM.notify(0, mNotify);
            this.isRunning = true;
            this.start_id = 0;

        }
        else if (!this.isRunning && start_id == 0){
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.start_id = 0;

        }

        else if (this.isRunning && start_id == 1){
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.start_id = 0;

        }
        else {
            Log.e("if there is sound ", " and you want end");


mediaPlayer.stop();
mediaPlayer.reset();
            this.isRunning = false;
            this.start_id = 0;
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
