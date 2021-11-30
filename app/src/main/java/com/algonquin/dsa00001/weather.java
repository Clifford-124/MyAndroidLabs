package com.algonquin.dsa00001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.algonquin.dsa00001.databinding.ActivityMainBinding;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class weather extends AppCompatActivity {

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");
    ProgressBar progressBar;
    //ActivityMainBinding binding;
    EditText etCity;
    TextView tvResult, tvWeatherCondition;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etCity = findViewById(R.id.etCity);
        tvResult = findViewById(R.id.tvResult);
        tvWeatherCondition = findViewById(R.id.tv_weather_condition);


        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        dialog = new Dialog(weather.this, R.style.CustomAlertDialog);
    }

    public void getWeatherDetails(View view) {
        if (NetworkAvailable.isNetworkAvailable(weather.this))
            getWeather();
    }
    private void getWeather(){
        String tempUrl = "";
        //String city = binding.etCity.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        //customDialog(city);
        if (city.equals("")) {
            //binding.tvResult.setText("City field can not be empty!");
            tvResult.setText("City field can not be empty!");
        } else {
            tempUrl = url + "?q=" + city + "&appid=" + appid;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double temp_min = jsonObjectMain.getDouble("temp_min") - 273.15;
                        double temp_max = jsonObjectMain.getDouble("temp_max") - 273.15;
                        int humidity = jsonObjectMain.getInt("humidity");

                        output = "The current temperature is " + df.format(temp)
                                + "\nThe max temperature is " + df.format(temp_max)
                                + "\nThe min temperature is " + df.format(temp_min)
                                + "\nThe humidity is " + humidity + "%";
                        tvResult.setText(output);
                        tvWeatherCondition.setText(description + "");
                        //progressBar.setVisibility(View.GONE);
                        //dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }


    public void customDialog(String city) {

        dialog.setContentView(R.layout.forecast_dialog);
        dialog.setCancelable(false);
        TextView tvTitle;
        progressBar = dialog.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);
        tvTitle = dialog.findViewById(R.id.tv_dialog_title);
        tvTitle.setText("We\'re calling people in "+ city+" to look outside their windows and tell us what\'s the weather like over there.");

        dialog.show();
        dialog.getWindow().setLayout(((int) weather.this.getResources().getDimension(R.dimen._250sdp)), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        btnOk.setOnClickListener(v -> dialog.dismiss());
    }
}