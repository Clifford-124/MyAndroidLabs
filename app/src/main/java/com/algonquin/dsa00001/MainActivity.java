package com.algonquin.dsa00001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private String emailAddressToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "onCreate() - Loading Widgets");

        final EditText emailAddress = (EditText) findViewById(R.id.email_address);
        final SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String value = prefs.getString("loginName", "null");
        Toast.makeText(MainActivity.this, "result 0 is :" + value, Toast.LENGTH_LONG).show();


            emailAddress.setText(value);
            emailAddressToString = emailAddress.getText().toString();
            Toast.makeText(MainActivity.this, "result 2 is :" + emailAddressToString, Toast.LENGTH_LONG).show();

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                //editor.putString("loginName", "cliffiswatching@tv.ca");
                editor.putString("loginName", emailAddressToString);
                editor.apply();

                Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
                nextPage.putExtra("EmailAddress", emailAddressToString);
                startActivity(nextPage);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart() -The application is now visible on screen");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() - The application is now responding to user input");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() - The application is now partially visible to user ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy() - The application is now dismissed and in background ");
    }


}
