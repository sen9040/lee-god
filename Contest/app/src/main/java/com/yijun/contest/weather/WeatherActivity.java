package com.yijun.contest.weather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.yijun.contest.LodingActivity;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;

import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WeatherDaily;


import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
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

    String baseUrl = "http://api.openweathermap.org/data/2.5/onecall";

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

    ArrayList<WeatherDaily> dailyArrayList = new ArrayList<>();
    LinearLayout linearLayoutH;
    LinearLayout linearLayoutTxtH;


    private RequestQueue requestQueue;
    private String url;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);



        linearLayoutH = findViewById(R.id.linearLayoutH);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
//                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutTxtH = findViewById(R.id.linearLayoutTxtH);





        currentLocationTxt = findViewById(R.id.currentLocationTxt);
        currentWeatherImg = findViewById(R.id.currentWeatherImg);
        currentWeatherTxt = findViewById(R.id.currentWeatherTxt);
//        forecastImg_1 = findViewById(R.id.forecastImg_1);
//        forecastTxt_1 = findViewById(R.id.forecastTxt_1);
//        forecastImg_2 = findViewById(R.id.forecastImg_2);
//        forecastTxt_2 = findViewById(R.id.forecastTxt_2);
//        forecastImg_3 = findViewById(R.id.forecastImg_3);
//        forecastTxt_3 = findViewById(R.id.forecastTxt_3);
//        forecastImg_4 = findViewById(R.id.forecastImg_4);
//        forecastTxt_4 = findViewById(R.id.forecastTxt_4);
//        forecastImg_5 = findViewById(R.id.forecastImg_5);
//        forecastTxt_5 = findViewById(R.id.forecastTxt_5);
//        forecastImg_6 = findViewById(R.id.forecastImg_6);
//        forecastTxt_6 = findViewById(R.id.forecastTxt_6);

        double mainLat = getIntent().getDoubleExtra("lat",0);
        double mainLng = getIntent().getDoubleExtra("lng",0);

//        if (mainLat != 0 || mainLng != 0 ){
//
//        }else {
//            url = "?lat="+mainLat+"&lon="+mainLng+"&exclude=hourly,minutely&appid=6896cf20ec1e12eac4c59197b748fd27&lang=kr&units=metric";
//        }

        url = "?lat=37.5207083&lon=126.8079374&exclude=hourly,minutely&appid=6896cf20ec1e12eac4c59197b748fd27&lang=kr&units=metric";
        getWeather(baseUrl+url);


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


    }

    public void getWeather(String url){
        requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("AAA","weather response : "+response);

                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);


                try {
                    // current weather
                    String timezone = response.getString("timezone");
                    JSONObject current = response.getJSONObject("current");
                    double temp = current.getDouble("temp");
                    double feels_like = current.getDouble("feels_like");
                    int wind_speed = current.getInt("wind_speed");
                    JSONArray weatherArray = current.getJSONArray("weather");
                    String description = weatherArray.getJSONObject(0).getString("description");
                    String icon = weatherArray.getJSONObject(0).getString("icon");
                    Log.i("AAA","current : "+description);

                    series.addPoint(new ValueLinePoint("시작", (float) temp));
                    // forecasts
                    JSONArray dailyArray = response.getJSONArray("daily");
                    for(int i = 1 ; i <7;i++){
                        JSONObject dailyObject = dailyArray.getJSONObject(i);

                        JSONObject dailyTempObject = dailyObject.getJSONObject("temp");
                        JSONArray dailyWeatherArray = dailyObject.getJSONArray("weather");
                        JSONObject dailyWeatherObject = dailyWeatherArray.getJSONObject(0);

                        int dailyDt = dailyObject.getInt("dt");
                        double dailyMin = dailyTempObject.getDouble("min");
                        double dailyMax = dailyTempObject.getDouble("max");
                        String dailyDescription = dailyWeatherObject.getString("description");
                        String dailyIcon = dailyWeatherObject.getString("icon");
                        double dailyPop = dailyObject.getDouble("pop");
                        Log.i("AAA","daily : "+dailyDescription);
                        // 예보 이미지 뷰
                        ImageView iv = new ImageView(WeatherActivity.this);  // 새로 추가할 imageView 생성
                        iv.setLayoutParams(new LinearLayout.LayoutParams(200,200,1));
                        iv.setMaxWidth(50);
                        String iconUrl = "http://openweathermap.org/img/wn/"+dailyIcon+"@2x.png";
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(WeatherActivity.this).load(iconUrl).into(iv);  // imageView에 내용 추가
                        linearLayoutH.addView(iv); // 기존 linearLayout에 imageView 추가
                        // simpleDateFormat
                        Date date = new java.util.Date(dailyDt*1000L);
                        String datePattern = "MM/dd";
                        SimpleDateFormat format = new SimpleDateFormat(datePattern);
                        String dateStr = format.format(date);
                        // 예보 텍스트 뷰
                        TextView tv = new TextView(WeatherActivity.this);
                        tv.setLayoutParams(new LinearLayout.LayoutParams(200,200,1));
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setMaxWidth(50);



                        tv.setText(dateStr+"\n"+dailyMin+"℃\n"+dailyMax+"℃\n"+dailyDescription);
                        linearLayoutTxtH.addView(tv);

                        // 그레프
                        series.addPoint(new ValueLinePoint(dateStr, (float) dailyMax));
                    }
                    // 데이터 입력
                    Glide.with(WeatherActivity.this).load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(currentWeatherImg);
                    currentLocationTxt.setText(timezone);
                    currentWeatherTxt.setText("현재 온도"+temp+"℃\n"+description);

                    ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);

                    series.addPoint(new ValueLinePoint("끝", (float) temp));
                    mCubicValueLineChart.addSeries(series);
                    mCubicValueLineChart.startAnimation();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AAA","weather error : "+error);
            }
        });
        requestQueue.add(request);
    }



}