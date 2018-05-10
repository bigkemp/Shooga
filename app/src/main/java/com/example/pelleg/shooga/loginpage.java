package com.example.pelleg.shooga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

Button btn = (Button) findViewById(R.id.yay);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(loginpage.this, AlarmBeGone.class);
        startActivity(intent);
    }
});

        Button login = (Button) findViewById(R.id.btnLOGIN);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("user", "tester"); // Storing string
                editor.putString("pass", "p1p1p1"); // Storing string


                editor.commit(); // commit changes

                Intent intent = new Intent(loginpage.this,MainActivity.class);
               startActivity(intent);
            }
        });
    }
}
