package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.yijun.contest.Favorite.FavoriteActivity;
import com.yijun.contest.Weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView_Hotplace;
RecyclerView recyclerView_sports;
Button btnHome;
Button btnLank;
Button btnfavorite;
Button btnWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHome = findViewById(R.id.btnHome);
        btnLank = findViewById(R.id.btnLank);
        btnfavorite = findViewById(R.id.btnfavorite);
        btnWeather = findViewById(R.id.btnWeather);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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