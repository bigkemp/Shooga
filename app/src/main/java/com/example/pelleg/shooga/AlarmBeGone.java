package com.example.pelleg.shooga;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AlarmBeGone extends AppCompatActivity {
    String[] cradentials;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmdismissal);
         context=this;

        final EditText something = (EditText) findViewById(R.id.etSOMETHING);
        Button btn = (Button) findViewById(R.id.btnSOMETHING);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(something.getText().toString().matches(""))){
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    cradentials = new String[] {pref.getString("user", null),pref.getString("pass", null),something.getText().toString()};

                bgTask bgTask = new bgTask(cradentials,context);
                bgTask.execute();
            }}
        });

    }


public class bgTask extends AsyncTask<String[],Void,Void>{

    Context ctx;
    ProgressDialog mProgress;
    String json_string = "http://pilgiya.site/Shooga_Email2.php";
    boolean something=false;

    public bgTask(String[] cradentials, Context context  ) {
        json_string = json_string + "?user="+cradentials[0] + "&pass="+cradentials[1]+ "&something="+cradentials[2];
        ctx = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress = new ProgressDialog(ctx);
        mProgress.setMessage("Please wait a minute...");
        mProgress.show();
    }


    @Override
    protected Void doInBackground(String[]... strings) {
        try {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            URL url = new URL(json_string);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while   ((line = bufferedReader.readLine()) != null){stringBuilder.append(line+"\n");}

            httpURLConnection.disconnect();
            String json_string = stringBuilder.toString().trim();
            JSONObject jsonObject = new JSONObject(json_string);
            String success = jsonObject.getString("ID");
            if (success != null){
                something = true;
            }else{
                something = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmBeGone.this);
                builder.setMessage("Failed").setNegativeButton("something wrong, are you connected to the internet?",null).create().show();
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("TAG",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG",e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("TAG",e.toString());
        }




        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        mProgress.dismiss();

        final Intent intent = new Intent(this.ctx, Alarm_Receiver.class);
        intent.putExtra("active",false);
        ctx.sendBroadcast(intent);
        finish();


        super.onPostExecute(aVoid);
    }
}
}
