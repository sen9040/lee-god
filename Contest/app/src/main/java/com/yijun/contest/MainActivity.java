package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yijun.contest.favorite.FavoriteActivity;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.ranking.RankingActivity;
import com.yijun.contest.search.SearchActivity;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView_Hotplace;
RecyclerView recyclerView_sports;
Button btnHome;
Button btnRanking;
Button btnFavorite;
Button btnSearch;
Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.btnHome);
        btnRanking = findViewById(R.id.btnRanking);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnSearch = findViewById(R.id.btnSearch);
        btnList = findViewById(R.id.btnList);

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
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(i);
            }
        });

        // 검색 화면
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);

            }
        });
    }

}