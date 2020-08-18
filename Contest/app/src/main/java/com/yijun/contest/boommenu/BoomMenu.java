package com.yijun.contest.boommenu;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;


public class BoomMenu {



    public void getBoomMenu(final Context context, BoomMenuButton bmb){


        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            if (i == 0){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.butterfly)
                        .normalTextRes(R.string.weather)
                        .subNormalTextRes(R.string.weather_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(context, WeatherActivity.class);
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                    }
                });
                bmb.addBuilder(builder);
            }else if(i == 1){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.bat)
                        .normalTextRes(R.string.test1)
                        .subNormalTextRes(R.string.test1_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
                    }
                });
                bmb.addBuilder(builder);
            }else if(i == 2){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.bear)
                        .normalTextRes(R.string.test2)
                        .subNormalTextRes(R.string.test2_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
                    }
                });
                bmb.addBuilder(builder);
            }else if(i == 3){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.bee)
                        .normalTextRes(R.string.test3)
                        .subNormalTextRes(R.string.test3_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
                    }
                });
                bmb.addBuilder(builder);
            }

        }


    }


}
