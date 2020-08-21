package com.yijun.contest.boommenu;

import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.airInfo.AirInfoActivity;
import com.yijun.contest.moverecord.MoveRecord;
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

    public BoomMenu(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public BoomMenu(int key) {
        this.key = key;
    }

    public void getBoomMenu(final Context context, BoomMenuButton bmb){
        this.context = context;

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0){

                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }
                if (key == 1){
                    Toast.makeText(context, "동일한 화면 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.butterfly)
                        .normalTextRes(R.string.weather)
                        .subNormalTextRes(R.string.weather_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(context, WeatherActivity.class);

                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
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
                        .normalImageRes(R.drawable.bat)
                        .normalTextRes(R.string.air_Info)
                        .subNormalTextRes(R.string.air_Info_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent intent = new Intent(context, AirInfoActivity.class);
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
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
                        .normalImageRes(R.drawable.bear)
                        .normalTextRes(R.string.moverecord)
                        .subNormalTextRes(R.string.moverecord_content);
                builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(context, MoveRecord.class);
                        Toast.makeText(context, "click" +index, Toast.LENGTH_SHORT).show();
                        context.startActivity(i);
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

    // GPS Disabled Alert
    private void createGpsDisabledAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS is disabled! Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                showGpsOptions();
                            }
                        })
                .setNegativeButton("Do nothing",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }
    // show GPS Options
    private void showGpsOptions() {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(gpsOptionsIntent);
    }
}
