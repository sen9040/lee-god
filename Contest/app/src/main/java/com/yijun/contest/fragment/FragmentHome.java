package com.yijun.contest.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.LodingActivity;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.WeatherActivity;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

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

    private double lat ;
    private double lng ;
    LocationManager locationManager;
    LocationListener locationListener;

    ProgressDialog dialog;


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

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                Log.i("AAA","lat : "+lat +" lng : "+lng);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION},0);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000*60, 100, locationListener);

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







        btnSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent i = new Intent(context, com.yijun.contest.list.ListActivity.class);
                i.putExtra("sports","축구");
                i.putExtra("key",1);
                i.putExtra("lat",lat);
                i.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(i);


            }
        });
        btn_baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }



                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","야구");
                a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(a);



            }
        });
        btn_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }



                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","족구");
                a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","테니스");
                a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }



                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","풋살");
               a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);


            }
        });
        btn_pingpong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }



                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                a.putExtra("sports","탁구");
                a.putExtra("key",1);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }



                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","다목적");
               a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","골프");
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                a.putExtra("key",1);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","배드민턴");
                a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","운동장");
                a.putExtra("key",1);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","체육관");
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                a.putExtra("key",1);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_dulle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","둘레길");
                a.putExtra("key",2);
                a.putExtra("lat",lat);
                a.putExtra("lng",lng);
                if (lat==0||lng==0){
                    Toast.makeText(context,"Gps가 불안정합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(a);

            }
        });
        btn_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check GPS Enable
                LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    createGpsDisabledAlert();
                    return;
                }


                Intent a = new Intent(context, com.yijun.contest.list.ListActivity.class);
                a.putExtra("sports","공원");
                a.putExtra("key",3);
                startActivity(a);

            }
        });



        return view;

    }



    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중..");
            asyncDialog.show();
            asyncDialog.setCancelable(false);
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... strings){

            for(int i = 0; i<40000; i++){
                publishProgress(i);

            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean s){

            asyncDialog.dismiss();
            super.onPostExecute(s);
        }


        @Override
        protected void onCancelled(Boolean s){
            super.onCancelled(s);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0){
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},0);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000*60,
                    100,
                    locationListener);
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
        startActivity(gpsOptionsIntent);
    }


}
