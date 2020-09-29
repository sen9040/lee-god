package com.yijun.contest.boommenu;

import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.airInfo.AirInfoActivity;
import com.yijun.contest.moverecord.MoveRecord;
import com.yijun.contest.network.CheckNetwork;
import com.yijun.contest.ranking.RankingActivity;
import com.yijun.contest.weather.WeatherActivity;
import com.yijun.contest.weather.model.Weather;

import static android.content.Context.LOCATION_SERVICE;

public class BoomMenu {

    Context context;
    double lat;
    double lng;
    int key = 0;

    public BoomMenu(){

    }



    public void getBoomMenu(final Context context, BoomMenuButton bmb){
        if(context == null){
            return;
        }
        this.context = context;

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0){

                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.eagle)
                        .normalTextRes(R.string.weather);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                        if (key == 1){
                            Toast.makeText(context, "동일한 화면 입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(context, WeatherActivity.class);

                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);

                        context.startActivity(intent);
                    }
                });
                bmb.addBuilder(builder);
            }else if(i == 1){

                if (key == 2){
                    Toast.makeText(context, "동일한 화면 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.bee)
                        .normalTextRes(R.string.air_Info);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(context, AirInfoActivity.class);

                        context.startActivity(intent);
                    }
                });
                bmb.addBuilder(builder);
            }else if(i == 2){

                if (key == 3){
                    Toast.makeText(context, "동일한 화면 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.butterfly)
                        .normalTextRes(R.string.moverecord);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(context, MoveRecord.class);

                        context.startActivity(i);
                    }
                });
                bmb.addBuilder(builder);
            }else if (i == 3) {
                if (key == 1) {
                    Toast.makeText(context, "동일한 화면 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CheckNetwork.isNetworkAvailable(context)){
                    Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.bat)
                        .normalTextRes(R.string.ranking);

                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {


                        Intent intent = new Intent(context, RankingActivity.class);

                        intent.putExtra("lat", lat);
                        intent.putExtra("lng", lng);

                        context.startActivity(intent);

                    }
                });
                bmb.addBuilder(builder);

            }
            }

    }


}
