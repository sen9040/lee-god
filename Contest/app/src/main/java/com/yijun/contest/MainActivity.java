package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yijun.contest.Favorite.FavoriteActivity;
import com.yijun.contest.Weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView_Hotplace;
    RecyclerView recyclerView_sports;
    Button btnSearch;
    Button btnHome;
    Button btnFavorite;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        btnHome = findViewById(R.id.btnHome);
        btnFavorite = findViewById(R.id.btnFavorite);
        fab = findViewById(R.id.fab);

        // 검색화면
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        // 홈화면
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 즐겨찾기화면
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(i);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}