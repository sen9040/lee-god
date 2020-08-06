package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yijun.contest.Favorite.FavoriteActivity;
import com.yijun.contest.Ranking.RankingActivity;
import com.yijun.contest.Weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView_Hotplace;
RecyclerView recyclerView_sports;
Button btnHome;
Button btnRanking;
Button btnfavorite;
Button btnWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHome = findViewById(R.id.btnHome);
        btnRanking = findViewById(R.id.btnRanking);
        btnfavorite = findViewById(R.id.btnfavorite);
        btnWeather = findViewById(R.id.btnWeather);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(i);
            }
        });
        btnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(i);
            }
        });

        // 날씨 화면
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }

}