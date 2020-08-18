package com.yijun.contest.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijun.contest.R;
import com.yijun.contest.weather.model.Forecasts;
import com.yijun.contest.weather.model.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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



    ArrayList<Forecasts> forecastsArrayList = new ArrayList<>();

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



    }
}