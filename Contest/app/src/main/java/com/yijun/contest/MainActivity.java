package com.yijun.contest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.fragment.FragmentHome;
import com.yijun.contest.fragment.FragmentSearch;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentSearch fragmentSearch = new FragmentSearch(MainActivity.this);
    private FragmentHome fragmentHome = new FragmentHome(MainActivity.this);
    private FragmentFavorite fragmentFavorite = new FragmentFavorite();

    LocationManager locationManager;
    LocationListener locationListener;
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }



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




  }

}