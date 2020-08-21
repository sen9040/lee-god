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
import com.yijun.contest.location.GpsInfo;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.WeatherActivity;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentHome extends Fragment {


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

    // fragment 는 생성자를 쓰면 안됨
    public FragmentHome(){
    }

    GpsInfo gps;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BoomMenuButton bmb = (BoomMenuButton) view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(getActivity(), bmb);


        gps = new GpsInfo(getContext());
        if (gps.isGetLocation()) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
            Log.i("AAA","lat : "+lat +" lng : "+lng);



            recyclerView = view.findViewById(R.id.recyclerView);
            btnSoccer = view.findViewById(R.id.btnSoccer);
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
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        return;
                    }


                    Intent i = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    i.putExtra("sports", "축구");
                    i.putExtra("key", 1);
                    i.putExtra("lat", lat);
                    i.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(i);


                }
            });
            btn_baseball.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "야구");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(a);


                }
            });
            btn_foot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "족구");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_tennis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "테니스");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_futsal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "풋살");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);


                }
            });
            btn_pingpong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    a.putExtra("sports", "탁구");
                    a.putExtra("key", 1);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_multi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "다목적");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_golf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "골프");
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    a.putExtra("key", 1);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_badminton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "배드민턴");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_ground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "운동장");
                    a.putExtra("key", 1);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_gym.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "체육관");
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    a.putExtra("key", 1);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_dulle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "둘레길");
                    a.putExtra("key", 2);
                    a.putExtra("lat", lat);
                    a.putExtra("lng", lng);
                    if (lat == 0 || lng == 0) {
                        Toast.makeText(getActivity(), "Gps가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(a);

                }
            });
            btn_park.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check GPS Enable
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        return;
                    }


                    Intent a = new Intent(getActivity(), com.yijun.contest.list.ListActivity.class);
                    a.putExtra("sports", "공원");
                    a.putExtra("key", 3);
                    startActivity(a);

                }
            });


            return view;

        }


        class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
            ProgressDialog asyncDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("로딩중..");
                asyncDialog.show();
                asyncDialog.setCancelable(false);
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... strings) {

                for (int i = 0; i < 40000; i++) {
                    publishProgress(i);

                }
                return true;

            }

            @Override
            protected void onPostExecute(Boolean s) {

                asyncDialog.dismiss();
                super.onPostExecute(s);
            }


            @Override
            protected void onCancelled(Boolean s) {
                super.onCancelled(s);
            }

        }

        return view;
    }
}
