package com.yijun.contest.weather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;

import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.model.Forecasts;
import com.yijun.contest.weather.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class WeatherActivity extends AppCompatActivity {

    TextView currentLocationTxt;
    ImageView currentWeatherImg;
    TextView currentWeatherTxt;
    TextView forecastTxt_1;
    ImageView forecastImg_1;
    TextView forecastTxt_2;
    ImageView forecastImg_2;
    TextView forecastTxt_3;
    ImageView forecastImg_3;
    TextView forecastTxt_4;
    ImageView forecastImg_4;
    TextView forecastTxt_5;
    ImageView forecastImg_5;
    TextView forecastTxt_6;
    ImageView forecastImg_6;

    LocationManager locationManager;
    LocationListener locationListener;

    ArrayList<Forecasts> forecastsArrayList = new ArrayList<>();
    // 30 이 코드 값임
    String weatherUrl = "https://weather-ydn-yql.media.yahoo.com/forecastrss?location=seoul&format=json&u=c";
    String iconUrl = "http://l.yimg.com/a/i/us/we/52/30.gif";
    String appId = "LHyI4Ire";
    String clientId = "dj0yJmk9ZXN2TEN5S0FiNUI3JmQ9YWk9TEh5STRJcmUmcGo9MCZzPWNvbnN1bWVyc2VjcmV0JnN2PTAmeD0xMQ--";
    String clientSecret = "88cac39cf0cd7950fd5b4b177aa9ceffc98ebb0d";
    private RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        currentLocationTxt = findViewById(R.id.currentLocationTxt);
        currentWeatherImg = findViewById(R.id.currentWeatherImg);
        currentWeatherTxt = findViewById(R.id.currentLocationTxt);
        forecastImg_1 = findViewById(R.id.forecastImg_1);
        forecastTxt_1 = findViewById(R.id.forecastTxt_1);
        forecastImg_2 = findViewById(R.id.forecastImg_2);
        forecastTxt_2 = findViewById(R.id.forecastTxt_2);
        forecastImg_3 = findViewById(R.id.forecastImg_3);
        forecastTxt_3 = findViewById(R.id.forecastTxt_3);
        forecastImg_4 = findViewById(R.id.forecastImg_4);
        forecastTxt_4 = findViewById(R.id.forecastTxt_4);
        forecastImg_5 = findViewById(R.id.forecastImg_5);
        forecastTxt_5 = findViewById(R.id.forecastTxt_5);
        forecastImg_6 = findViewById(R.id.forecastImg_6);
        forecastTxt_6 = findViewById(R.id.forecastTxt_6);

       
        currentWeatherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    WeatherYdnJava.main(WeatherActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("AAA","error 12: "+e);
                }

                Toast.makeText(WeatherActivity.this, "hi", Toast.LENGTH_SHORT).show();
            }
        });

        // todo volley 사용 해서 날씨 API 가져오기 (후보 1순위 야후 날씨 날씨 이미지도 가져 올수 있어 보임 )

        // 가져온 값으로 Activity setting
        Weather weather = new Weather("서울시",28,123,
                8.2f,70,10,23,
                "17시-20분","05시-20분","비",32,29);
        // todo volley  forecasts[]  ArrayList 에 입력
        Forecasts forecasts = new Forecasts("월",1546992000,24,29,"rain",12);
        forecastsArrayList.add(forecasts);

        // simpleDateFormat
        int date = forecastsArrayList.get(0).getDate();
        String datePattern = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        String dateStr = format.format(date);
        Log.i("AAA",dateStr);

//        for(int i = 0 ; i < forecastsArrayList.size();i++){
//            if(i == 0){
//                forecastTxt_1.setText(forecastsArrayList.get(0).getDay()+"\n"+forecastsArrayList.get(0).getDate());
//            }
//        }
//
//        currentLocationTxt.setText(weather.getLocation_city());
//        currentWeatherTxt.setText(weather.getCondition_temperature()+"℃, "+weather.getCondition_text());

        locationManager = (LocationManager) WeatherActivity.this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                Log.i("AAA","lat : "+lat);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(WeatherActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WeatherActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION},0);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000*60, 100, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0){
            if (ActivityCompat.checkSelfPermission(WeatherActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(WeatherActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(WeatherActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},0);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000*60,
                    100,
                    locationListener);
        }
        getSearch(weatherUrl);

    }

    public void getSearch(String url){
        requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("AAA","weather response : "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AAA","weather error : "+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>headers=new HashMap<String,String>();
                headers.put("X-Yahoo-App-Id","LHyI4Ire");
                headers.put("cache-control","no-cache");
                // 나의 시간도 필요함
                headers.put("Authorization","OAuth " +
                        "oauth_consumer_key="+clientId+",oauth_signature_method=HMAC-SHA1,oauth_timestamp=1597738963,oauth_nonce=ZlZkNpc0Ipl,oauth_version=1.0,oauth_signature=oHxUBJQYvSKvQIQFdZwbWQBgN2g=" +
                        "cache-control: no-cache");
                return headers;
            }
        };
        requestQueue.add(request);
    }
}