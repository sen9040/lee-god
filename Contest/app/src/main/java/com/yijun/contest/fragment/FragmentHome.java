package com.yijun.contest.fragment;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.LodingActivity;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.weather.WeatherActivity;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    Context context;
    RecyclerView recyclerView;
    ImageButton btnSoccer;
    ImageButton btn_baseball;
    ImageButton btn_foot;
    ImageButton btn_tennis;
    ImageButton btn_futsal;
    ImageButton btn_pingpong;
    ImageButton btn_multi;
    ImageButton btn_golf;
    ImageButton btn_badminton;
    ImageButton btn_ground;
    ImageButton btn_gym;
    ImageButton btn_dulle;
    ImageButton btn_park;
    ImageButton btn_mountain;

    public FragmentHome(){
    }

    public FragmentHome(Context context){
      this.context = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(context,bmb);

        recyclerView = view.findViewById(R.id.recyclerView);
        btnSoccer  =  view.findViewById(R.id.btnSoccer);
        btn_baseball = view.findViewById(R.id.btn_baseball);
        btn_foot = view.findViewById(R.id.btn_foot);
        btn_tennis = view.findViewById(R.id.btn_tennis);
        btn_futsal = view.findViewById(R.id.btn_futsal);
        btn_pingpong = view.findViewById(R.id.btn_pingpong);
        btn_multi = view.findViewById(R.id.btn_multi);
        btn_golf = view.findViewById(R.id.btn_golf);
        btn_badminton = view.findViewById(R.id.btn_badminton);
        btn_ground = view.findViewById(R.id.btn_ground);
        btn_gym = view.findViewById(R.id.btn_gym);
        btn_dulle = view.findViewById(R.id.btn_dulle);
        btn_park = view.findViewById(R.id.btn_park);
        btn_mountain = view.findViewById(R.id.btn_mountain);
//
        btnSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, com.yijun.contest.list.ListActivity.class);
                i.putExtra("sports","축구");
                i.putExtra("key",1);
               startActivity(i);


            }
        });
        btn_baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","야구");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","족구");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","테니스");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","풋살");
               a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_pingpong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","탁구");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","다목적");
               a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","골프");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","배드민턴");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","운동장");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","체육관");
                a.putExtra("key",1);
                startActivity(a);

            }
        });
        btn_dulle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","둘레길");
                a.putExtra("key",2);
                startActivity(a);

            }
        });
        btn_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","공원");
                a.putExtra("key",3);
                startActivity(a);

            }
        });
        btn_mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","산");
                a.putExtra("key",4);
                startActivity(a);

            }
        });
        return view;
    }
}
