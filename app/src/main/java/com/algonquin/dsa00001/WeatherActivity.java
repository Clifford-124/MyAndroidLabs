package com.algonquin.dsa00001;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WeatherActivity extends AppCompatActivity {

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");
    ProgressBar progressBar;
    //ActivityMainBinding binding;
    EditText etCity;
    TextView tvResult, tvWeatherCondition;
    Dialog dialog;
    private Toolbar myToolbar;
    private float oldSize = 14;

    private double temp;
    private double temp_max;
    private double temp_min;
    private int humidity;
    private String description;
    private Button buttonGetForecast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.popout_menu);
        navigationView.setNavigationItemSelectedListener((item) -> {

            onOptionsItemSelected(item);

            switch (item.getItemId()) {
                case R.id.hide_views:
                    etCity.setText("");
                    break;
                case R.id.id_decrease:
                    oldSize = Float.max(oldSize - 1, 5);
                    etCity.setTextSize(oldSize);
                    break;
                case R.id.id_increase:
                    oldSize++;
                    etCity.setTextSize(oldSize);
                    break;
                case 5:
                    String cityName = item.getTitle().toString();
                    runForecast(cityName);
                    break;
            }
            drawer.closeDrawer(GravityCompat.START);
                return false;
            });

        etCity = findViewById(R.id.etCity);
        tvResult = findViewById(R.id.tvResult);
        tvWeatherCondition = findViewById(R.id.tv_weather_condition);


        buttonGetForecast = findViewById(R.id.btnGet);
        buttonGetForecast.setOnClickListener(click -> {
            getWeatherDetails();
        });

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        dialog = new Dialog(WeatherActivity.this, R.style.CustomAlertDialog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hide_views:
 /*               currentTemp.setVisibility(View.INVISIBLE);
                maxTemp.setVisibility(View.INVISIBLE);
                minTemp.setVisibility(View.INVISIBLE);
                humidity.setVisibility(View.INVISIBLE);
                description.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);*/
                etCity.setText("");
                break;
            case R.id.id_decrease:
                oldSize = Float.max(oldSize - 1, 5);
/*                currentTemp.setTextSize(oldSize);
                maxTemp.setTextSize(oldSize);
                minTemp.setTextSize(oldSize);
                humidity.setTextSize(oldSize);
                description.setTextSize(oldSize);*/
                etCity.setTextSize(oldSize);
                break;
            case R.id.id_increase:
                oldSize++;
/*                currentTemp.setTextSize(oldSize);
                maxTemp.setTextSize(oldSize);
                minTemp.setTextSize(oldSize);
                humidity.setTextSize(oldSize);
                description.setTextSize(oldSize);*/
                etCity.setTextSize(oldSize);
                break;
            case 5:
                String cityName = item.getTitle().toString();
                runForecast(cityName);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void runForecast(String city) {
        getWeatherDetails(city);
    }

    public void getWeatherDetails() {
        String cityName = etCity.getText().toString();
        myToolbar.getMenu().add( 1, 5, 10, cityName).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        Executor newThread = Executors.newSingleThreadExecutor();
        if (NetworkAvailable.isNetworkAvailable(WeatherActivity.this))
            getWeather();
    }

    public void getWeatherDetails(String cityName) {
        etCity.setText(cityName);
        if(etCity.getText().length() != 0)
            if (NetworkAvailable.isNetworkAvailable(WeatherActivity.this))
                getWeather();
    }

    private void getWeather() {
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
                        description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        temp = jsonObjectMain.getDouble("temp") - 273.15;
                        temp_min = jsonObjectMain.getDouble("temp_min") - 273.15;
                        temp_max = jsonObjectMain.getDouble("temp_max") - 273.15;
                        humidity = jsonObjectMain.getInt("humidity");

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
        tvTitle.setText("We\'re calling people in " + city + " to look outside their windows and tell us what\'s the weather like over there.");

        dialog.show();
        dialog.getWindow().setLayout(((int) WeatherActivity.this.getResources().getDimension(R.dimen._250sdp)), LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        btnOk.setOnClickListener(v -> dialog.dismiss());
    }
}