package com.yijun.contest.viewdetails;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yijun.contest.R;
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.Parking;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView imgSvc;
    TextView txtSvcNm;
    TextView txtPlaceNm;
    TextView txtPaYaTnm;
    TextView txtTime;
    TextView txtExam;
    Button btnhiper;
    double lat;
    double lng;
    int offset = 0;
    int key;
    RequestQueue requestQueue;
    String parkingBaseUrl = Utils.SERVER_BASE_URL+"/api/v1/parking";
    ArrayList<Parking> parkingArrayList = new ArrayList<>();
    ArrayList<MarkerOptions> list = new ArrayList<>();
    // GPS 좌표 가져오기 위한 LocationManager 멤버변수 선언.
    LocationManager locationManager;
    // 위치가 변경될 때마다, 처리해줄 리스너 멤버변수로 선언.
    LocationListener locationListener;
    private String url;

    ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtExam = findViewById(R.id.txtExm);
        imgSvc = findViewById(R.id.imgSvc1);
        txtSvcNm = findViewById(R.id.txtSvcNm1);
        txtPlaceNm = findViewById(R.id.txtPlaceNm1);
        txtPaYaTnm = findViewById(R.id.txtPaYaTnm1);
        txtTime = findViewById(R.id.txtTime1);
        btnhiper = findViewById(R.id.btnhiper1);


        requestQueue = Volley.newRequestQueue(ViewDetailsActivity.this);



        key = getIntent().getIntExtra("key",0);
       if (key==1){
           setting();
       }else if (key==2){
           nature_setting();
       }else {
           way_setting();
       }
        imgSvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key == 1) {
                    SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");
                    double sportLat = Double.parseDouble(sportInfo.getX());
                    double sportLng = Double.parseDouble(sportInfo.getY());
                    url = parkingBaseUrl+"/location?lat="+sportLat+"&lng="+sportLng+"&offset=0";
                    getParkingData(url);

                } else if (key == 2){
                    NatureInfo natureInfo = (NatureInfo)getIntent().getSerializableExtra("sports");
                    double natureLat = Double.parseDouble(natureInfo.getX());
                    double natureLng = Double.parseDouble(natureInfo.getY());
                    url = parkingBaseUrl+"/location?lat="+natureLat+"&lng="+natureLng+"&offset=0";
                    getParkingData(url);
                }else {

                }



            }
        });



        btnhiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key == 1) {
                SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");

                String svcUrl = sportInfo.getSvcUrl();
                String svcStaTnm = sportInfo.getSvcStaTnm();

                if (svcStaTnm.equals("접수종료")) {
                    Toast.makeText(ViewDetailsActivity.this, "접수가 종료되었습니다."
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(svcUrl));
                    startActivity(i);
                }
            } else if (key == 2){
                    NatureInfo natureInfo = (NatureInfo)getIntent().getSerializableExtra("sports");

                    String tempUrl = natureInfo.getTemplateUrl();
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tempUrl));
                    startActivity(i);
                }
                else {
                    Toast.makeText(ViewDetailsActivity.this,"링크가 없습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // 위치기반 서비스를 위해서, 안드로이드 시스템에 위치기반서비스 요청.
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("AAA", location.toString());
                lat = location.getLatitude();
                lng = location.getLongitude();

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 유저한테, 이 앱은 위치기반 권한이 있어야 한다고 알려야 한다.
            // 유저가 권한 설정을 하고 나면, 처리해야 할 코드를 작성하기 위해서,
            // requestCode 값을 설정한다.
            ActivityCompat.requestPermissions(ViewDetailsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 0, locationListener);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String x;
        String y;
        String svcNm;
        if (key==1){
            SportsInfo sportInfo =(SportsInfo) getIntent().getSerializableExtra("sports");
             x= sportInfo.getX();
             y= sportInfo.getY();
             svcNm= sportInfo.getSvcNm();
        }else if (key==2){
            NatureInfo natureInfo =(NatureInfo) getIntent().getSerializableExtra("sports");
            x =natureInfo.getX();
            y = natureInfo.getY();
            svcNm = natureInfo.getpPark();
        }else {
            WayInfo wayInfo =(WayInfo) getIntent().getSerializableExtra("sports");
            x = wayInfo.getX();
            y = wayInfo.getY();
            svcNm = wayInfo.getCpiName();

        }





        final LatLng main;
        if (x.isEmpty()||x.equals("")||y.isEmpty()||y.equals("")){
            Toast.makeText(ViewDetailsActivity.this,"해당 지도 정보가 없습니다.",Toast.LENGTH_LONG).show();
            main = new LatLng(37.554862899999996,126.97461089999997);
            svcNm = "서울시청";

        }else {
            lat = Double.parseDouble(x);
            lng = Double.parseDouble(y);
            main = new LatLng(lat, lng);

        }



        if (key == 1) {
            SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");
            double sportLat = Double.parseDouble(sportInfo.getX());
            double sportLng = Double.parseDouble(sportInfo.getY());
            url = parkingBaseUrl+"/location?lat="+sportLat+"&lng="+sportLng+"&offset=0";
            requestQueue = Volley.newRequestQueue(ViewDetailsActivity.this);
            final String finalSvcNm2 = svcNm;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("AAA","parking response : "+response);
                                JSONArray items = response.getJSONArray("items");
                                for (int i =0; i<3; i++){
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    String pay_yn = jsonObject.getString("pay_yn");
                                    String pay_nm = jsonObject.getString("pay_nm");
                                    String parkingname = jsonObject.getString("parking_name");
                                    String addr = jsonObject.getString("addr");
                                    double lat = jsonObject.getDouble("lat");
                                    double lng = jsonObject.getDouble("lng");
                                    double distance = jsonObject.getDouble("distance");

                                    Parking parking1 = new Parking(pay_yn,pay_nm,parkingname,addr,lat,lng);
                                    Log.i("AAA","parking : "+pay_nm+", "+pay_yn+", "+parkingname+", "+addr+", "+lat+", "+lng+", "+distance);
                                    parkingArrayList.add(parking1);
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);
                                }
                                mMap.addMarker(new MarkerOptions().position(main).title(finalSvcNm2));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(main,16));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("AAA","getParking error : "+error);
                }
            }

            );
            requestQueue.add(jsonObjectRequest);

        } else if (key == 2){
            NatureInfo natureInfo = (NatureInfo)getIntent().getSerializableExtra("sports");
            double natureLat = Double.parseDouble(natureInfo.getX());
            double natureLng = Double.parseDouble(natureInfo.getY());
            url = parkingBaseUrl+"/location?lat="+natureLat+"&lng="+natureLng+"&offset=0";
            requestQueue = Volley.newRequestQueue(ViewDetailsActivity.this);
            final String finalSvcNm = svcNm;
            final String finalSvcNm1 = svcNm;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.i("AAA","parking response : "+response);
                                JSONArray items = response.getJSONArray("items");
                                for (int i =0; i<3; i++){
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    String pay_yn = jsonObject.getString("pay_yn");
                                    String pay_nm = jsonObject.getString("pay_nm");
                                    String parkingname = jsonObject.getString("parking_name");
                                    String addr = jsonObject.getString("addr");
                                    double lat = jsonObject.getDouble("lat");
                                    double lng = jsonObject.getDouble("lng");
                                    double distance = jsonObject.getDouble("distance");

                                    Parking parking1 = new Parking(pay_yn,pay_nm,parkingname,addr,lat,lng);
                                    Log.i("AAA","parking : "+pay_nm+", "+pay_yn+", "+parkingname+", "+addr+", "+lat+", "+lng+", "+distance);
                                    parkingArrayList.add(parking1);
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);
                                }
                                mMap.addMarker(new MarkerOptions().position(main).title(finalSvcNm1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(main,16));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("AAA","getParking error : "+error);
                }
            }

            );
            requestQueue.add(jsonObjectRequest);
        }else {

        }

        Log.i("AAA","에베베베 : "+markerOptionsArrayList.toString());
        // Add a marker in Sydney and move the camera


    }



    public void setting(){


        SportsInfo sportInfo =(SportsInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = sportInfo.getSvcNm();
        String placeNm = sportInfo.getPlaceNm();
        String paYaTnm = sportInfo.getPaYaTnm();
        String v_min = sportInfo.getV_min();
        String v_max = sportInfo.getV_max();
        String svcStaTnm = sportInfo.getSvcStaTnm();
        String imgUrl = sportInfo.getImgUrl();
        if (imgUrl.isEmpty() || imgUrl.equals("")){
           imgSvc.setImageResource(R.drawable.butterfly);
        }else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);
        }

        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);

        if (svcStaTnm.equals("접수종료")){
            txtTime.setText(svcStaTnm);
        }else {
            if (v_max.isEmpty() || v_max.equals("")){
                txtTime.setText(svcStaTnm);
            }
            txtTime.setText(svcStaTnm +" : "+v_min+" ~ "+v_max);
        }



    }
    public void nature_setting(){

        NatureInfo natureInfo =(NatureInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = natureInfo.getpPark();
        String placeNm = natureInfo.getpAddr();
        String paYaTnm = natureInfo.getpName();
        String svcStaTnm = natureInfo.getpAdmintel();
        String imgUrl = natureInfo.getpImg();
        if (imgUrl.isEmpty() || imgUrl.equals("")){
            imgSvc.setImageResource(R.drawable.butterfly);
        }else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);
        }

        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);
        txtTime.setText(svcStaTnm);


    }
    public void way_setting(){


        WayInfo wayInfo =(WayInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = wayInfo.getCpiName();
        String placeNm = wayInfo.getDetailCourse();
        String paYaTnm = wayInfo.getDistance();
        String svcStaTnm = wayInfo.getLeadTime();


        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);
        txtTime.setText(svcStaTnm);




    }

public void getParkingData(String url){
        Log.i("AAA","getParking : on");
        requestQueue = Volley.newRequestQueue(ViewDetailsActivity.this);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        Log.i("AAA","parking response : "+response);
                        JSONArray items = response.getJSONArray("items");
                        for (int i =0; i<3; i++){
                            JSONObject jsonObject = items.getJSONObject(i);
                            String pay_yn = jsonObject.getString("pay_yn");
                            String pay_nm = jsonObject.getString("pay_nm");
                            String parkingname = jsonObject.getString("parking_name");
                            String addr = jsonObject.getString("addr");
                            double lat = jsonObject.getDouble("lat");
                            double lng = jsonObject.getDouble("lng");
                            double distance = jsonObject.getDouble("distance");

                            Parking parking1 = new Parking(pay_yn,pay_nm,parkingname,addr,lat,lng);
                            Log.i("AAA","parking : "+pay_nm+", "+pay_yn+", "+parkingname+", "+addr+", "+lat+", "+lng+", "+distance);
                            parkingArrayList.add(parking1);
                            MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title(parkingname);
                            markerOptionsArrayList.add(options);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("AAA","getParking error : "+error);
        }
    }

    );
    requestQueue.add(jsonObjectRequest);
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0){
            if(ActivityCompat.checkSelfPermission(ViewDetailsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ViewDetailsActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(ViewDetailsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,   // 밀리세컨드,  1000 : 1초
                    0,   // 미터   10m
                    locationListener);
        }
    }

}

