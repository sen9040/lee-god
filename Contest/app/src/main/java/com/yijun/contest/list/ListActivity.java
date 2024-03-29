package com.yijun.contest.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.airInfo.AirInfoActivity;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.fragment.FragmentSearch;

import com.yijun.contest.list.adapter.NatureRecyclerViewAdapter;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.list.adapter.WayRecyclerViewAdapter;
import com.yijun.contest.location.GpsInfo;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    TextView txtSport;
    RecyclerView recyclerView;

    RequestQueue requestQueue;

    String baseUrl = Utils.SERVER_BASE_URL+"/api/v1/search";
    String url;
    String natureTestUrl = "http://openapi.seoul.go.kr:8088/474f4e6f42746b6436386354566d65/json/SearchParkInfoService/1/25/";



    RecyclerViewAdapter adapter;
    NatureRecyclerViewAdapter natureAdapter;
    WayRecyclerViewAdapter wayAdapter;

    ArrayList<SportsInfo> sportInfoArrayList = new ArrayList<>();
    ArrayList<NatureInfo> natureInfoArrayList = new ArrayList<>();
    ArrayList<WayInfo> wayInfoArrayList = new ArrayList<>();
    int offset = 0;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat;
    double lng;
    int cnt;
    private String event = "";
    private GpsInfo gps;
    private String idByANDROID_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtSport = findViewById(R.id.txtSport);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

        idByANDROID_ID =
                Settings.Secure.getString(ListActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID).trim();

        if (lat == 0 || lng == 0 ){
            lat = getIntent().getDoubleExtra("lat",0);
            lng = getIntent().getDoubleExtra("lng",0);
        }


        // gps 클래스
        gps = new GpsInfo(getBaseContext());
        if (gps.isGetLocation()) {
            lat = gps.getLatitude();
            lng = gps.getLongitude();
            Log.i("AAA", "lat : " + lat + " lng : " + lng);

        }
        BoomMenuButton bmb = (BoomMenuButton)findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(ListActivity.this,bmb,true);
        bmb.bringToFront();
        getSettingUrl(lat,lng);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();
                lastPosition = lastPosition +1;   // 인덱스값이라 -1 인듯... ?
                if(lastPosition == totalCount){
                    //아이템 추가 ! 입맛에 맞게 설정하시면됩니다.
                    if(cnt != 0){
                        Log.i("AAA","List offset : "+offset+event);
                        url = baseUrl+"&offset="+offset;
                        if (event.equals("sport")){
                            getSportInfo(url,offset,ListActivity.this,recyclerView);
                        }else if(event.equals("nature")){
                            getNatureInfo(url,offset,ListActivity.this,recyclerView);
                        }else if(event.equals("way")){
                            getWayInfo(url,offset,ListActivity.this,recyclerView);
                        }

                    }else {
                        Toast.makeText(ListActivity.this, "모든 시설물을 표시 했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CheckTypesTask task = new CheckTypesTask();
        task.execute();

    }



    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(ListActivity.this);

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

                for(int i = 0; i<10000; i++){
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





    private void getSettingUrl(double setLat,double setLng) {
        String sports = getIntent().getStringExtra("sports");

        if (sports.equals("축구")){

            Log.i("AAA","id : "+idByANDROID_ID);
            baseUrl = baseUrl+ "/sports?keyword=축구장&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            String soccer = "축구장";
            txtSport.setText(soccer);
            Log.i("AAA","id url : " +url);
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("야구")){
            baseUrl = baseUrl+ "/sports?keyword=야구장&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("야구장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("족구")){
            baseUrl = baseUrl+ "/sports?keyword=족구장&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("족구");
            getSportInfo(url,offset,ListActivity.this,recyclerView);
        }else if (sports.equals("테니스")){
            baseUrl = baseUrl+ "/sports?keyword=테니스장&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("테니스");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("풋살")){
            baseUrl = baseUrl+ "/sports?keyword=풋살&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("풋살경기장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);
        }else if (sports.equals("탁구")){
            baseUrl = baseUrl+ "/sports?keyword=탁구&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("탁구장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("다목적")){
            baseUrl = baseUrl+ "/sports?keyword=다목적경기장&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("다목적경기장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("골프")){
            baseUrl = baseUrl+ "/sports?keyword=골프&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("파크골프장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("배드민턴")){
            baseUrl = baseUrl+ "/sports?keyword=배드민턴&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("배드민턴장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("운동장")){
            baseUrl = baseUrl+ "/sports?keyword=운동&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("운동장");
            getSportInfo(url,offset,ListActivity.this,recyclerView);
        }else if (sports.equals("체육관")){
            baseUrl = baseUrl+ "/sports?keyword=체육관&lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            txtSport.setText("체육관");
            getSportInfo(url,offset,ListActivity.this,recyclerView);

        }else if (sports.equals("둘레길")){
            txtSport.setText("둘레길");
            baseUrl = baseUrl +"/way"+"?lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            getWayInfo(url,offset,ListActivity.this,recyclerView);
            return;

        }else if (sports.equals("공원")){
            txtSport.setText("공원");
            baseUrl = baseUrl+"/park?lat="+setLat+"&lng="+setLng+"&id="+idByANDROID_ID;
            url = baseUrl+"&offset="+offset;
            getNatureInfo(url,offset,ListActivity.this,recyclerView);
            return;
        }

    }



    // 필드 설정 필요한것 requestQueue , recyclerViewAdapter,recyclerView,offset,cnt,sportInfoArrayList
    public void getSportInfo(String url, final int offset_cnt, final Context volleyContext, final RecyclerView recycler){
        if (offset_cnt == 0){
            sportInfoArrayList.clear();
        }
        event = "sport";
        requestQueue = Volley.newRequestQueue(volleyContext);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("AAA","sport response : "+response);
                try {
                    cnt = response.getInt("cnt");
                    JSONArray items = response.getJSONArray("items");
                    for(int i = 0; i < items.length(); i++){

                        JSONObject object = items.getJSONObject(i);
                        String svcId = object.getString("SVCID");
                        String maxClassNm= object.getString("MAXCLASSNM");
                        String minClassNm= object.getString("MINCLASSNM");
                        String svcStaTnm= object.getString("SVCSTATNM");
                        String svcNm= object.getString("SVCNM");
                        String paYaTnm= object.getString("PAYATNM");
                        String placeNm= object.getString("PLACENM");
                        String useTgtInfo= object.getString("USETGTINFO");
                        String svcUrl= object.getString("SVCURL");
                        String x= object.getString("X");
                        String y= object.getString("Y");
                        String svcOpnBgnDt= object.getString("SVCOPNBGNDT");
                        String svcOpnEndDt= object.getString("SVCOPNENDDT");
                        String rcptEndDt= object.getString("RCPTBGNDT");
                        String rcptBgnDt= object.getString("RCPTENDDT");
                        String areaNm= object.getString("AREANM");
                        String imgUrl= object.getString("IMGURL");
                        String dtlCont= object.getString("DTLCONT");
                        String telNo= object.getString("TELNO");
                        String v_min= object.getString("V_MIN");
                        String v_max= object.getString("V_MAX");
                        String revStdDayNm= object.getString("REVSTDDAYNM");
                        String revStdDay= object.getString("REVSTDDAY");
                        double distance = object.getDouble("distance");
                        Log.i("AAA","search for : "+svcStaTnm);
                        int isFavorite = object.getInt("isFavorite");
                        SportsInfo sportInfo = new SportsInfo(svcId,maxClassNm,minClassNm,svcStaTnm,svcNm,paYaTnm,
                                placeNm,useTgtInfo,svcUrl,x,y,svcOpnBgnDt,svcOpnEndDt,rcptBgnDt,rcptEndDt,areaNm,imgUrl,
                                dtlCont,telNo,v_min,v_max,revStdDayNm,revStdDay,distance,isFavorite);
                        sportInfoArrayList.add(sportInfo);
                    }
                    if (offset_cnt == 0){
                        adapter = new RecyclerViewAdapter(volleyContext,sportInfoArrayList);
                        recycler.setAdapter(adapter);
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                    // 페이징
                    offset = offset + cnt;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AAA","search error : "+error);
            }
        });
        requestQueue.add(request);
    }

    public void getNatureInfo(String url, final int offset_cnt, final Context volleyContext, final RecyclerView recycler){
        if (offset_cnt == 0){
            natureInfoArrayList.clear();
        }
        event = "nature";
        requestQueue = Volley.newRequestQueue(volleyContext);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","nature response : "+response);
                        try {
                            JSONArray items = response.getJSONArray("items");
                            cnt = response.getInt("cnt");

                            for (int i = 0; i < items.length(); i++){
                                JSONObject object = items.getJSONObject(i);
                                String pIdx = object.getString("P_IDX");
                                String pPark = object.getString("P_PARK");
                                String pListContent = object.getString("P_LIST_CONTENT");
                                String area = object.getString("AREA");
                                String openDt = object.getString("OPEN_DT");
                                String mainEquip = object.getString("MAIN_EQUIP");
                                String mainPlants = object.getString("MAIN_PLANTS");
                                String guidance = object.getString("GUIDANCE");
                                String visitRoad = object.getString("VISIT_ROAD");
                                String useRefer = object.getString("USE_REFER");
                                String pImg = object.getString("P_IMG");
                                String pZone = object.getString("P_ZONE");
                                String pAddr = object.getString("P_ADDR");
                                String pName = object.getString("P_NAME");
                                String pAdmintel = object.getString("P_ADMINTEL");
                                String y = object.getString("LONGITUDE");
                                String x = object.getString("LATITUDE");
                                String templateUrl = object.getString("TEMPLATE_URL");
                                if (templateUrl.equals("")){
                                    templateUrl = "http://parks.seoul.go.kr/";
                                }
                                double distance = object.getDouble("distance");
                                int isFavorite = object.getInt("isFavorite");
                                NatureInfo natureInfo = new NatureInfo(pIdx,pPark,pListContent,area,openDt,mainEquip,mainPlants,
                                        guidance,visitRoad,useRefer,pImg,pZone,pAddr,pName,pAdmintel,x,y,templateUrl, isFavorite,distance);
                                natureInfoArrayList.add(natureInfo);
                            }

                            if (offset_cnt == 0){
                                natureAdapter = new NatureRecyclerViewAdapter(volleyContext, natureInfoArrayList);
                                recycler.setAdapter(natureAdapter);
                            }else {
                                natureAdapter.notifyDataSetChanged();
                            }
                            // 페이징
                            offset = offset + cnt;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(request);
    }
    public void getWayInfo(String url, final int offset_cnt, final Context volleyContext, final RecyclerView recycler){
        if (offset_cnt == 0){
            wayInfoArrayList.clear();
        }
        event = "way";
        requestQueue = Volley.newRequestQueue(volleyContext);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("AAA","way response : "+response);
                            JSONArray items = response.getJSONArray("items");
                            cnt = response.getInt("cnt");
                            Log.i("AAA","List volley cnt : " + cnt);
                            for (int i = 0; i < items.length(); i++){
                                JSONObject object = items.getJSONObject(i);
                                String courseCategory = object.getString("COURSE_CATEGORY");
                                String courseCategoryNm = object.getString("COURSE_CATEGORY_NM");
                                String southNorthDiv = object.getString("SOUTH_NORTH_DIV");
                                String southNorthDivNm = object.getString("SOUTH_NORTH_DIV_NM");
                                String areaGu = object.getString("AREA_GU");
                                String distance = object.getString("DISTANCE");
                                String leadTime = object.getString("LEAD_TIME");
                                String courseLevel = object.getString("COURSE_LEVEL");
                                String voteCnt = object.getString("VOTE_CNT");
                                String relateSubway = object.getString("RELATE_SUBWAY");
                                String trafficInfo = object.getString("TRAFFIC_INFO");
                                String content = object.getString("CONTENT");
                                String pdfFilePath = object.getString("PDF_FILE_PATH");
                                String courseName = object.getString("COURSE_NAME");
                                String regDate = object.getString("REG_DATE");
                                String detailCourse = object.getString("DETAIL_COURSE");
                                String cpiIdx = object.getString("CPI_IDX");
                                String cpiName = object.getString("CPI_NAME");
                                String x = object.getString("X");
                                String y = object.getString("Y");
                                String cpiContent = object.getString("CPI_CONTENT");
                                int isFavorite = object.getInt("isFavorite");
                                String pageUrl = "http://gil.seoul.go.kr/walk/index.jsp";
                                double curDistance = object.getDouble("distance");
                                WayInfo wayInfo = new WayInfo(courseCategory,courseCategoryNm,southNorthDiv,southNorthDivNm,
                                        areaGu,distance,leadTime,courseLevel,voteCnt,relateSubway,trafficInfo,content,pdfFilePath,
                                        courseName,regDate,detailCourse,cpiIdx,cpiName,x,y,cpiContent, isFavorite,pageUrl,curDistance);
                                wayInfoArrayList.add(wayInfo);
                            }
                            if (offset_cnt == 0){
                                wayAdapter = new WayRecyclerViewAdapter(ListActivity.this, wayInfoArrayList);
                                recycler.setAdapter(wayAdapter);
                            }else {
                                wayAdapter.notifyDataSetChanged();
                            }
                            // 페이징
                            offset = offset + cnt;
                            Log.i("AAA","List volley offset : " + offset);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("AAA","List error : "+error);
                    }
                });
        requestQueue.add(request);
    }



    // sport 즐겨찾기 추가 함수
    public void addSportFavorite(final int position){
        SportsInfo sportsInfo = sportInfoArrayList.get(position);
        String idx = sportsInfo.getSvcId();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL + "/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","add sport favorite : "+response.toString());
                        // 어레이리스트의 값을 변경시켜줘야 한다.
                        SportsInfo sportsInfo = sportInfoArrayList.get(position);
                        sportsInfo.setIsFavorite(1);

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(ListActivity.this).add(request);
    }

    // nature(park) 즐겨찾기 추가 함수
    public void addParkFavorite(final int position){
        NatureInfo natureInfo = natureInfoArrayList.get(position);
        String idx = natureInfo.getpIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL + "/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","add park favorite : "+response.toString());
                        // 어레이리스트의 값을 변경시켜줘야 한다.
                        NatureInfo natureInfo = natureInfoArrayList.get(position);
                        natureInfo.setIsFavorite(1);

                        natureAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(ListActivity.this).add(request);
    }

    // way 즐겨찾기 추가 함수
    public void addWayFavorite(final int position){
        WayInfo wayInfo = wayInfoArrayList.get(position);
        String idx = wayInfo.getCpiIdx();
        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL +"/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","add park favorite : "+response.toString());
                        // 어레이리스트의 값을 변경시켜줘야 한다.
                        WayInfo wayInfo = wayInfoArrayList.get(position);
                        wayInfo.setIsFavorite(1);

                        wayAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(ListActivity.this).add(request);
    }



    // 스포츠 즐겨찾기 삭제
    public void deleteSportFavorite(final int position){
        SportsInfo sportsInfo = sportInfoArrayList.get(position);

        // 서버에 보내기 위해서 필요
        String idx = sportsInfo.getSvcId();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL +"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SportsInfo sportsInfo = sportInfoArrayList.get(position);
                        sportsInfo.setIsFavorite(0);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Log.i("AAA","sport delete error : "+error);
                    }
                }
        );
        requestQueue.add(request);
    }

    // 공원 즐겨찾기 삭제
    public void deleteParkFavorite(final int position){
        NatureInfo natureInfo = natureInfoArrayList.get(position);

        // 서버에 보내기 위해서 필요
        String idx = natureInfo.getpIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL +"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        NatureInfo natureInfo = natureInfoArrayList.get(position);
                        natureInfo.setIsFavorite(0);
                        natureAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

    // 두드림길 즐겨찾기 삭제
    public void deleteWayFavorite(final int position){
        WayInfo wayInfo = wayInfoArrayList.get(position);

        // 서버에 보내기 위해서 필요
        String idx = wayInfo.getCpiIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",idByANDROID_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL +"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WayInfo wayInfo = wayInfoArrayList.get(position);
                        wayInfo.setIsFavorite(0);
                        wayAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

}