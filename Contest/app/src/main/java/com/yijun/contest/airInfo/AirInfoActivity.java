package com.yijun.contest.airInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.model.AirInfo;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.WeatherActivity;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AirInfoActivity extends AppCompatActivity {

    // 날짜를 넣어야 하는데 YYYYMMDD 형식으로 넣어야함
    String url = "http://openAPI.seoul.go.kr:8088/"+ Utils.AUTH_KEY+"/json/DailyAverageAirQuality/1/5/";
    private RequestQueue requestQueue;
    ArrayList<AirInfo> airInfoArrayList = new ArrayList<>();
    TextView txtNo2;
    TextView txtO3;
    TextView txtCo;
    TextView txtSo2;
    TextView txtPm10;
    TextView txtPm25;
    ImageView imgNo2;
    ImageView imgO3;
    ImageView imgCo;
    ImageView imgSo2;
    ImageView imgPm10;
    ImageView imgPm25;
    ImageView imgCurrent;
    TextView txt;

    private String date ;
    private String msrste_nm;
    private double no2;
    private double o3 ;
    private double co ;
    private double so2 ;
    private double pm10 ;
    private double pm25 ;

    BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_info);

        txtNo2 = findViewById(R.id.txtNo2);
        txtO3 = findViewById(R.id.txtO3);
        txtCo = findViewById(R.id.txtCo);
        txtSo2 = findViewById(R.id.txtSo2);
        txtPm10 = findViewById(R.id.txtPm10);
        txtPm25 = findViewById(R.id.txtPm25);
        imgNo2 = findViewById(R.id.imgNo2);
        imgO3= findViewById(R.id.imgO3);
        imgCo = findViewById(R.id.imgCo);
        imgSo2 = findViewById(R.id.imgSo2);
        imgPm10 = findViewById(R.id.imgPm10);
        imgPm25 = findViewById(R.id.imgPm25);
        imgCurrent = findViewById(R.id.imgCurrent);
        txt = findViewById(R.id.txt);

        BoomMenuButton bmb = (BoomMenuButton)findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(AirInfoActivity.this,bmb);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        String getDate = sdf.format(date);

        Log.i("AAA","air date : "+getDate);

        url = url+getDate+"/강남구";


        getAirInfo(url);



    }
    public void getAirInfo(String url){
        requestQueue = Volley.newRequestQueue(AirInfoActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("AAA","air response : "+response);
                try {
                    JSONObject dailyObject = response.getJSONObject("DailyAverageAirQuality");
                    JSONArray rowArray = dailyObject.getJSONArray("row");
                    for(int i = 0;i<rowArray.length();i++){
                        JSONObject rowObject = rowArray.getJSONObject(i);
                         date = rowObject.getString("MSRDT_DE");
                         msrste_nm = rowObject.getString("MSRSTE_NM");
                         no2 = rowObject.getDouble("NO2");
                         o3 = rowObject.getDouble("O3");
                         co = rowObject.getDouble("CO");
                         so2 = rowObject.getDouble("SO2");
                         pm10 = rowObject.getDouble("PM10");
                         pm25 = rowObject.getDouble("PM25");

                        Log.i("AAA","air row : "+date+", "+msrste_nm+", "+no2+", "+o3+", "+co+", "+so2+", "+pm10+", "+pm25);
                    }

                    mBarChart = (BarChart) findViewById(R.id.barchart);

                    float no2f = Float.parseFloat(String.valueOf(no2));
                    float o3f = Float.parseFloat(String.valueOf(o3));
                    float cof = Float.parseFloat(String.valueOf(co));
                    float so2f = Float.parseFloat(String.valueOf(so2));
                    float pm10f = Float.parseFloat(String.valueOf(pm10));
                    float pm25f = Float.parseFloat(String.valueOf(pm25));
                    Log.i("AAA","pm : "+pm10f);

                    mBarChart.addBar(new BarModel("이산화질소",no2f*1000, 0xFF123456));
                    mBarChart.addBar(new BarModel("오존",o3f*1000,  0xFF343456));
                    mBarChart.addBar(new BarModel("일산화탄소",cof*10, 0xFF563456));
                    mBarChart.addBar(new BarModel("이황산가스",so2f*1000, 0xFF873F56));
                    mBarChart.addBar(new BarModel("초미세먼지",pm25f,  0xFF343456));
                    mBarChart.addBar(new BarModel("미세먼지",pm10f, 0xFF56B7F1));

                    mBarChart.startAnimation();

                    txtNo2.setText(+no2+" ppm");
                    txtO3.setText(o3+" ppm");
                    txtCo.setText(+co+" ppm");
                    txtSo2.setText(+so2+" ppm");
                    txtPm10.setText(+pm10+" ㎍/㎥");
                    txtPm25.setText(+pm25+" ㎍/㎥");

                    int avg = 0;
                    if(no2 >0.06){
                        // 나쁨
                        avg = avg +1;
                        imgNo2.setImageResource(R.drawable.air_bad);
                    }else {
                        imgNo2.setImageResource(R.drawable.air_good);
                    }
                    if(o3 >0.06){
                        // 나쁨
                        avg = avg +1;
                        imgO3.setImageResource(R.drawable.air_bad);
                    }else{
                        imgO3.setImageResource(R.drawable.air_good);
                    }
                    if(co >9){
                        // 나쁨
                        avg = avg +1;
                        imgCo.setImageResource(R.drawable.air_bad);
                    }else {
                        imgCo.setImageResource(R.drawable.air_good);
                    }
                    if(so2 >0.05){
                        // 나쁨
                        avg = avg +1;
                        imgSo2.setImageResource(R.drawable.air_bad);
                    }else {
                        imgSo2.setImageResource(R.drawable.air_good);
                    }
                    if(pm10 >50){
                        // 나쁨
                        avg = avg +1;
                        imgPm10.setImageResource(R.drawable.air_bad);
                    }else {
                        imgPm10.setImageResource(R.drawable.air_good);
                    }
                    if(pm25 >35){
                        // 나쁨
                        avg = avg +1;
                        imgPm25.setImageResource(R.drawable.air_bad);
                    }else {
                        imgPm25.setImageResource(R.drawable.air_good);
                    }

                    if (avg >= 3){
                        imgCurrent.setImageResource(R.drawable.air_bad);
                        txt.setText("나쁨, 오늘은 활동하기 좋지않은거같아요..");
                    }else {
                        imgCurrent.setImageResource(R.drawable.air_good);
                        txt.setText("좋음, 오늘은 야외활동하기 좋아요!");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AAA","air error : "+error);
            }
        })
        ;
        requestQueue.add(request);
    }
}