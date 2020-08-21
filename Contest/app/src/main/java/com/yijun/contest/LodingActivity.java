package com.yijun.contest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.yijun.contest.model.Loading;

public class LodingActivity extends AppCompatActivity {
    Handler handler; // 헨들러 선언
    Loading loader; // 로더 클래스 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        init(); // 핸들러 시작

        loader = new Loading(handler); // 로더 클래스에 핸들러를 던져줌 ( 로딩 종료후 핸들러에 메세지를 보내면 스플레시 엑티비티를 종료
        new Thread(loader).start(); //로딩 시작

    }

    public void init(){

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                finish();
            }
        };
    }

}