package com.example.weatherapplicationq4_e2145389;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String City = "London";
    String key = "2315437a225a1268ac2135ee4f11fa2d";

    public class DownloadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            String result = "";

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1) {
                    result += (char) data;
                    data = inputStreamReader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    TextView txtCity, txtTime, txtValueFeelLike, txtValueHumidity, txtVision, txtTemp,txtLat,txtLon,txtGeocoded;
    EditText edt;
    Button btn;
    RelativeLayout rlWeather, rlRoot;

    public void Loading(View view) {
        City = edt.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("WeatherAppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastSearchedCity",City);
        editor.apply();


        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + City + "&units=metric&appid=" + key;

        System.out.println("url"+url);

        edt.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        rlWeather.setVisibility(View.VISIBLE);
        rlRoot.setBackgroundColor(Color.parseColor("#E6E6E6"));
        System.out.println("106");

        DownloadJSON downloadJSON = new DownloadJSON();
        DownloadJSON downloadGeoCodedJson = new DownloadJSON();

        System.out.println("110");

        try {
            System.out.println("113");
            String result = downloadJSON.execute(url).get();
            System.out.println("result"+ result);
            JSONObject jsonObject = new JSONObject(result);

            String temp = jsonObject.getJSONObject("main").getString("temp");
            String humidity = jsonObject.getJSONObject("main").getString("humidity");
            String feel_Like = jsonObject.getJSONObject("main").getString("feels_like");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            String latitude = jsonObject.getJSONObject("coord").getString("lat");
            String longitude= jsonObject.getJSONObject("coord").getString("lon");


            String reverseGeoCoderUrl = "http://api.openweathermap.org/geo/1.0/reverse?lat="+latitude+"&lon="+longitude+"&limit=5"+"&appid=" +key;
            String reverseGeoCoderResult = downloadGeoCodedJson.execute(reverseGeoCoderUrl).get();
            JSONArray jsonObjectGeoCoderResult = new JSONArray(reverseGeoCoderResult);
            String name= jsonObjectGeoCoderResult.getJSONObject(0).getString("name");
            String country = jsonObjectGeoCoderResult.getJSONObject(0).getString("country");
            String state = jsonObjectGeoCoderResult.getJSONObject(0).getString("state");


            System.out.println("reverseGeoCoderResult"+ reverseGeoCoderResult);
            System.out.println(temp);
            System.out.println(humidity);
            System.out.println(feel_Like);
            System.out.println(description);

            System.out.println(name);
            System.out.println(country);
            System.out.println(state);

            Long time = jsonObject.getLong("dt");

            String sTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH).format(new Date(time * 1000));

            txtTime.setText(sTime);
            txtCity.setText(City);
            txtVision.setText(description);
            txtValueFeelLike.setText(feel_Like);
            txtValueHumidity.setText(humidity);
            txtTemp.setText(temp + "°");
            txtGeocoded.setText("Geo-Coded address:- Country: " + country +"," + " Province: " + state+ "," + " City:" + name);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtCity = findViewById(R.id.txtCity);
        txtTime = findViewById(R.id.txtTime);
        txtValueFeelLike = findViewById(R.id.txtValueFeelLike);
        txtValueHumidity = findViewById(R.id.txtValueHumidity);
        txtVision = findViewById(R.id.txtValueDescription);
        txtTemp = findViewById(R.id.txtValue);
        btn = findViewById(R.id.btn);
        edt = findViewById(R.id.edt);
        rlWeather = findViewById(R.id.rlWeather);
        rlRoot = findViewById(R.id.rlRoot);
        txtGeocoded=findViewById(R.id.txtGeocoded);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading(v);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("WeatherAppPrefs", MODE_PRIVATE);
        String lastSearchedCity = sharedPreferences.getString("lastSearchedCity", null);

        if (lastSearchedCity != null) {
            City = lastSearchedCity;
            edt.setText(City);
            System.out.println("I got last searched city");
        }
    }
}