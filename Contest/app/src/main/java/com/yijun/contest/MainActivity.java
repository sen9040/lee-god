package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

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

    }

}