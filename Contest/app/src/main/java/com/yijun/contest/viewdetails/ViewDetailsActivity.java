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
import android.widget.FrameLayout;
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
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.airInfo.AirInfoActivity;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.location.GpsInfo;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.Parking;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.moverecord.data.DatabaseHandler;
import com.yijun.contest.moverecord.model.MoveRecord;
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
    String parkingBaseUrl = Utils.SERVER_BASE_URL + "/api/v1/parking";
    ArrayList<Parking> parkingArrayList = new ArrayList<>();
    ArrayList<MarkerOptions> list = new ArrayList<>();
    // GPS 좌표 가져오기 위한 LocationManager 멤버변수 선언.
    LocationManager locationManager;
    // 위치가 변경될 때마다, 처리해줄 리스너 멤버변수로 선언.
    LocationListener locationListener;
    private String url;

    ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>();
    private GpsInfo gps;

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


        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(ViewDetailsActivity.this, bmb);
        bmb.bringToFront();
        btnhiper.bringToFront();

        // gps class
        gps = new GpsInfo(getBaseContext());
        if (gps.isGetLocation()) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
            Log.i("AAA", "lat details : " + lat + " lng details : " + lng);
        }
            key = getIntent().getIntExtra("key", 0);
            if (key == 1) {
                setting();
            } else if (key == 2) {
                nature_setting();
            } else if(key == 3){
                way_setting();
            }else if(key == 4){
                favoriteSetting();
            }else {
                finish();
            }
            imgSvc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (key == 1) {
                        SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");
                        double sportLat = Double.parseDouble(sportInfo.getX());
                        double sportLng = Double.parseDouble(sportInfo.getY());
                        url = parkingBaseUrl + "/location?lat=" + sportLat + "&lng=" + sportLng + "&offset=0";
                        getParkingData(url);

                    } else if (key == 2) {
                        NatureInfo natureInfo = (NatureInfo) getIntent().getSerializableExtra("sports");
                        double natureLat = Double.parseDouble(natureInfo.getX());
                        double natureLng = Double.parseDouble(natureInfo.getY());
                        url = parkingBaseUrl + "/location?lat=" + natureLat + "&lng=" + natureLng + "&offset=0";
                        getParkingData(url);
                    } else if(key == 3){
                        WayInfo wayInfo = (WayInfo) getIntent().getSerializableExtra("sports");
                        double natureLat = Double.parseDouble(wayInfo.getX());
                        double natureLng = Double.parseDouble(wayInfo.getY());
                        url = parkingBaseUrl + "/location?lat=" + natureLat + "&lng=" + natureLng + "&offset=0";
                        getParkingData(url);
                    }else if(key == 4){
                        Favorite favorite = (Favorite) getIntent().getSerializableExtra("sports");
                        double natureLat = Double.parseDouble(favorite.getLat());
                        double natureLng = Double.parseDouble(favorite.getLng());
                        url = parkingBaseUrl + "/location?lat=" + natureLat + "&lng=" + natureLng + "&offset=0";
                        getParkingData(url);
                    }else {
                        finish();
                    }


                }
            });


            btnhiper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoveRecord moveRecord = new MoveRecord();
                    if (key == 1) {
                        SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");

                        String svcNm = sportInfo.getSvcNm();
                        String placeNm = sportInfo.getPlaceNm();
                        String svcUrl = sportInfo.getSvcUrl();
                        String svcStaTnm = sportInfo.getSvcStaTnm();


                         Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(svcUrl));
                         startActivity(i);


                        moveRecord.setTitle(svcNm);
                        moveRecord.setAddress(placeNm);
                        moveRecord.setUrl(svcUrl);

                        DatabaseHandler db = new DatabaseHandler(ViewDetailsActivity.this);
                        db.addMoveRecord(moveRecord);

                    } else if (key == 2) {
                        NatureInfo natureInfo = (NatureInfo) getIntent().getSerializableExtra("sports");

                        String pPark = natureInfo.getpPark();
                        String pAddr = natureInfo.getpAddr();
                        String tempUrl = natureInfo.getTemplateUrl();

                        moveRecord.setTitle(pPark);
                        moveRecord.setAddress(pAddr);
                        moveRecord.setUrl(tempUrl);

                        DatabaseHandler db = new DatabaseHandler(ViewDetailsActivity.this);
                        db.addMoveRecord(moveRecord);
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tempUrl));
                        startActivity(i);
                    }else if(key == 3){
                        WayInfo wayInfo = (WayInfo) getIntent().getSerializableExtra("sports");

                        String pPark = wayInfo.getCpiName();
                        String pAddr = wayInfo.getTrafficInfo();
                        String tempUrl = wayInfo.getPageUrl();

                        moveRecord.setTitle(pPark);
                        moveRecord.setAddress(pAddr);
                        moveRecord.setUrl(tempUrl);

                        DatabaseHandler db = new DatabaseHandler(ViewDetailsActivity.this);
                        db.addMoveRecord(moveRecord);
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tempUrl));
                        startActivity(i);
                    }else if(key == 4){
                        Favorite favorite = (Favorite) getIntent().getSerializableExtra("sports");

                        String pPark = favorite.getTitle();
                        String pAddr = favorite.getAddress();
                        String tempUrl = favorite.getPageUrl();

                        moveRecord.setTitle(pPark);
                        moveRecord.setAddress(pAddr);
                        moveRecord.setUrl(tempUrl);

                        DatabaseHandler db = new DatabaseHandler(ViewDetailsActivity.this);
                        db.addMoveRecord(moveRecord);
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(tempUrl));
                        startActivity(i);
                    }else {
                        finish();
                    }


                }
            });


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
        }else if (key==3){
            WayInfo wayInfo =(WayInfo) getIntent().getSerializableExtra("sports");
            x = wayInfo.getX();
            y = wayInfo.getY();
            svcNm = wayInfo.getCpiName();
        }else if (key==4){
            Favorite favorite =(Favorite) getIntent().getSerializableExtra("sports");
            x = favorite.getLat();
            y = favorite.getLng();
            svcNm = favorite.getTitle();
            Log.i("AAA","lat lng details :"+lat+lng);
        }else {
            x = "37.554862899999996";
            y = "126.97461089999997";
            svcNm = "";
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
            final String nm = sportInfo.getSvcNm();
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
                                for (int i =0; i<10; i++){
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
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title("주차장").snippet(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);

                                }

                                mMap.addMarker(new MarkerOptions().position(main).title("체육시설").snippet(nm));
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
            final String pPark = natureInfo.getpPark();
            url = parkingBaseUrl+"/location?lat="+natureLat+"&lng="+natureLng+"&offset=0";
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
                                for (int i =0; i<10; i++){
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
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title("주차장").snippet(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);
                                }
                                mMap.addMarker(new MarkerOptions().position(main).title("공원과 산").snippet(pPark));
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
        } else if (key == 3){
            WayInfo wayInfo = (WayInfo) getIntent().getSerializableExtra("sports");
            final String cpiName = wayInfo.getCpiName();
//            double natureLat = Double.parseDouble(wayInfo.getX());
//            double natureLng = Double.parseDouble(wayInfo.getY());
            // 임시로 서울시청 으로
            x = "37.554862899999996";
            y = "126.97461089999997";

            url = parkingBaseUrl+"/location?lat="+x+"&lng="+y+"&offset=0";
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
                                for (int i =0; i<10; i++){
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
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title("주차장").snippet(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);
                                }
                                mMap.addMarker(new MarkerOptions().position(main).title("두드림길").snippet(cpiName));
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
        } else if (key == 4){
            Favorite favorite = (Favorite) getIntent().getSerializableExtra("sports");
            double natureLat = Double.parseDouble(favorite.getLat());
            double natureLng = Double.parseDouble(favorite.getLng());
            final String title = favorite.getTitle();
            url = parkingBaseUrl+"/location?lat="+natureLat+"&lng="+natureLng+"&offset=0";
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
                                for (int i =0; i<10; i++){
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
                                    MarkerOptions options = new MarkerOptions().position(new LatLng(lat, lng)).title("주차장").snippet(parkingname).
                                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    markerOptionsArrayList.add(options);
                                }
                                for (MarkerOptions options : markerOptionsArrayList){
                                    mMap.addMarker(options);
                                }
                                mMap.addMarker(new MarkerOptions().position(main).title("선택한 장소").snippet(title));
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
            mMap.addMarker(new MarkerOptions().position(main).title(""));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(main,16));
        }

        Log.i("AAA","에베베베 : "+markerOptionsArrayList.toString());
        // Add a marker in Sydney and move the camera


    }

    public void setting () {


        SportsInfo sportInfo = (SportsInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = sportInfo.getSvcNm();
        String placeNm = sportInfo.getPlaceNm();
        String paYaTnm = sportInfo.getPaYaTnm();
        String v_min = sportInfo.getV_min();
        String v_max = sportInfo.getV_max();
        String svcStaTnm = sportInfo.getSvcStaTnm();
        String imgUrl = sportInfo.getImgUrl();
        String dtlCont = sportInfo.getDtlCont();
        if (imgUrl.isEmpty() || imgUrl.equals("")) {
            imgSvc.setImageResource(R.drawable.butterfly);
        } else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);
        }


        String removeStr = null;
        try {
            removeStr = removeTag(dtlCont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtExam.setText("상세 정보\n" + removeStr + "\n" + "\n" + "\n" + "\n");

        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);

        if (svcStaTnm.equals("접수종료")) {
            txtTime.setText(svcStaTnm);
        } else {
            if (v_max.isEmpty() || v_max.equals("")) {
                txtTime.setText(svcStaTnm);
            }
            txtTime.setText(svcStaTnm + " : " + v_min + " ~ " + v_max);
        }


    }
    public void nature_setting () {

        NatureInfo natureInfo = (NatureInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = natureInfo.getpPark();
        String placeNm = natureInfo.getpAddr();
        String paYaTnm = natureInfo.getpName();
        String svcStaTnm = natureInfo.getpAdmintel();
        String imgUrl = natureInfo.getpImg();
        String p_listContent = natureInfo.getpListContent();
        String mainPlants = natureInfo.getMainPlants();
        if (imgUrl.isEmpty() || imgUrl.equals("")) {
            imgSvc.setImageResource(R.drawable.butterfly);
        } else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);
        }

        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);
        txtTime.setText(svcStaTnm);

        String removeStr = null;
        try {
            removeStr = removeTag(mainPlants);
            removeStr = removeTag(p_listContent)+removeStr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        txtExam.setText("상세 정보\n" + removeStr + "\n" + "\n" + "\n" + "\n");

    }
    public void way_setting () {


        WayInfo wayInfo = (WayInfo) getIntent().getSerializableExtra("sports");


        // 사진이 없을시 튕긴다.
        String svcNm = wayInfo.getCpiName();
        String placeNm = wayInfo.getDetailCourse();
        String paYaTnm = wayInfo.getDistance();
        String svcStaTnm = wayInfo.getLeadTime();
        String content = wayInfo.getContent();


        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);
        txtTime.setText(svcStaTnm);

        String removeStr = null;
        try {
            removeStr = removeTag(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtExam.setText("상세 정보\n" + removeStr + "\n" + "\n" + "\n" + "\n");


    }

    private void favoriteSetting() {
        Favorite favorite = (Favorite) getIntent().getSerializableExtra("sports");
        String add = favorite.getAddress();
        String imgUrl = favorite.getImgUrl();
        String content = favorite.getContent();
        if (imgUrl == null || imgUrl.equals("")){
            imgSvc.setImageResource(R.drawable.no_image);
        }else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);;
        }


        txtSvcNm.setText(    favorite.getTitle());
        txtPlaceNm.setText(add);
        txtTime.setText( favorite.getTime());

        String removeStr = null;
        try {
            removeStr = removeTag(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtExam.setText("상세 정보\n" + removeStr + "\n" + "\n" + "\n" + "\n");

    }

    // html 제거
    public String removeTag (String html) throws Exception {

         html =  html.replaceAll("&nbsp;", "");
         html = html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        return html;

    }

    public void getParkingData (String url){
        Log.i("AAA", "getParking : on");
        requestQueue = Volley.newRequestQueue(ViewDetailsActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // 주차장 5개 찍는 중
                            Log.i("AAA", "parking response : " + response);
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < 5; i++) {
                                JSONObject jsonObject = items.getJSONObject(i);
                                String pay_yn = jsonObject.getString("pay_yn");
                                String pay_nm = jsonObject.getString("pay_nm");
                                String parkingname = jsonObject.getString("parking_name");
                                String addr = jsonObject.getString("addr");
                                double lat = jsonObject.getDouble("lat");
                                double lng = jsonObject.getDouble("lng");
                                double distance = jsonObject.getDouble("distance");

                                Parking parking1 = new Parking(pay_yn, pay_nm, parkingname, addr, lat, lng);
                                Log.i("AAA", "parking : " + pay_nm + ", " + pay_yn + ", " + parkingname + ", " + addr + ", " + lat + ", " + lng + ", " + distance);
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
                Log.i("AAA", "getParking error : " + error);
            }
        }

        );
        requestQueue.add(jsonObjectRequest);
    }



}
