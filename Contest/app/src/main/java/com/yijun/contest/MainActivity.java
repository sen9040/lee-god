package com.yijun.contest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.fragment.FragmentHome;
import com.yijun.contest.fragment.FragmentSearch;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView_Hotplace;
RecyclerView recyclerView_sports;
Button btnHome;
Button btnRanking;
Button btnFavorite;
Button btnSearch;
Button btnList;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch(MainActivity.this);
    private FragmentHome fragmentHome = new FragmentHome();
    private FragmentFavorite fragmentFavorite = new FragmentFavorite();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        // 버튼 선택
        bottomNavigationView.setSelectedItemId(R.id.itemHome);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @SuppressLint("ResourceType")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.itemSearch:
                    transaction.replace(R.id.frameLayout, fragmentSearch).commitAllowingStateLoss();
                    break;
                case R.id.itemHome:
                    transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.itemFavorite:
                    transaction.replace(R.id.frameLayout, fragmentFavorite).commitAllowingStateLoss();
                    break;
            }
            return true;
        }

//        btnHome = findViewById(R.id.btnHome);
//        btnRanking = findViewById(R.id.btnRanking);
//        btnFavorite = findViewById(R.id.btnFavorite);
//        btnSearch = findViewById(R.id.btnSearch);
//        btnList = findViewById(R.id.btnList);
//
//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRanking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, RankingActivity.class);
//                startActivity(i);
//            }
//        });
//        btnFavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Intent i = new Intent(MainActivity.this, FavoriteActivity.class);
//            startActivity(i);
//            }
//        });
//
//        // 검색 화면
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, SearchActivity.class);
//                startActivity(i);
//            }
//        });
//        btnList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, ListActivity.class);
//                startActivity(i);
//
//            }
//        });


  }

}