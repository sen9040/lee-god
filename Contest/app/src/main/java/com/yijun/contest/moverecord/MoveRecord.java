package com.yijun.contest.moverecord;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.airInfo.AirInfoActivity;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.moverecord.adapter.RecyclerViewAdapter;
import com.yijun.contest.moverecord.data.DatabaseHandler;

import java.util.ArrayList;

public class MoveRecord extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<com.yijun.contest.moverecord.model.MoveRecord> moveRecordArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_record);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoveRecord.this));

        BoomMenuButton bmb = (BoomMenuButton)findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(MoveRecord.this,bmb);
        bmb.bringToFront();
//        DatabaseHandler db = new DatabaseHandler(MoveRecord.this);
//        moveRecordArrayList = db.getAllRecord();
//
//        Log.i("adapter", "adapter연결");
//
//        recyclerViewAdapter = new RecyclerViewAdapter(MoveRecord.this, moveRecordArrayList);
//        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        moveRecordArrayList.clear();
        DatabaseHandler db = new DatabaseHandler(MoveRecord.this);
        moveRecordArrayList = db.getAllRecord();
        // 어댑터를 연결해야지 화면에 표시가 됨.
        recyclerViewAdapter = new RecyclerViewAdapter(MoveRecord.this, moveRecordArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}