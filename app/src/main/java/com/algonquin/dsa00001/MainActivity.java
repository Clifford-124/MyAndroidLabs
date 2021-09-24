package com.algonquin.dsa00001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mytext = findViewById(R.id.textview);
        Button btn =findViewById(R.id.mybutton);
        EditText myedit = findViewById(R.id.myedittext);
        CheckBox chbox = findViewById(R.id.mychbutton);
        RadioButton rabox = findViewById(R.id.myrabutton);
        Switch sh = findViewById(R.id.myshbutton);
        final LinearLayout rootLayout =findViewById(R.id.rootLayout);

        chbox.setOnClickListener(v -> Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show());

        chbox.setOnCheckedChangeListener((buttonView, isChecked) ->
        {


                Snackbar snackbar = Snackbar
                        .make(rootLayout, getResources().getString(R.string.checkbox_text) + " " +
                                (isChecked ? getResources().getString(R.string.checkbox_on) : getResources().getString(R.string.checkbox_off)), Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> chbox.setChecked(!isChecked));

                snackbar.show();
            });


            sh.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                Snackbar snackbar = Snackbar
                        .make(rootLayout, getResources().getString(R.string.switch_text) + " " +
                                (isChecked ? getResources().getString(R.string.switch_on) : getResources().getString(R.string.switch_off)), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sh.setChecked(!isChecked);
                            }
                        });

                snackbar.show();
            });

        }}




