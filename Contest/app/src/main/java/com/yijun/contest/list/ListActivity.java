package com.yijun.contest.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yijun.contest.R;
import com.yijun.contest.list.adapter.NatureRecyclerViewAdapter;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.list.adapter.WayRecyclerViewAdapter;
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;

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

    String testUrl = "http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/ListPublicReservationSport/1/25";
    String natureTestUrl = "http://openapi.seoul.go.kr:8088/474f4e6f42746b6436386354566d65/json/SearchParkInfoService/1/25/";
    String wayTestUrl = "http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/SeoulGilWalkCourse/1/25/";

    int list_total_count;
    RecyclerViewAdapter adapter;
    NatureRecyclerViewAdapter natureAdapter;
    WayRecyclerViewAdapter wayAdapter;

    ArrayList<SportsInfo> sportInfoArrayList = new ArrayList<>();
    ArrayList<NatureInfo> natureInfoArrayList = new ArrayList<>();
    ArrayList<WayInfo> wayInfoArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtSport = findViewById(R.id.txtSport);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));


        requestQueue = Volley.newRequestQueue(ListActivity.this);

        String sports = getIntent().getStringExtra("sports");


        if (sports.equals("축구")){
            testUrl = testUrl+ "/축구장";
            String soccer = "축구장";
            txtSport.setText(soccer);


        }else if (sports.equals("야구")){
            testUrl = testUrl+ "/야구장";
            txtSport.setText("야구장");


        }else if (sports.equals("족구")){
            testUrl = testUrl+ "/족구장";
            txtSport.setText("족구");

        }else if (sports.equals("테니스")){
            testUrl = testUrl+ "/테니스장";
            txtSport.setText("테니스");


        }else if (sports.equals("풋살")){
            testUrl = testUrl+ "/풋살경기장";
            txtSport.setText("풋살경기장");

        }else if (sports.equals("탁구")){
            testUrl = testUrl+ "/탁구장";
            txtSport.setText("탁구장");


        }else if (sports.equals("다목적")){
            testUrl = testUrl+ "/다목적경기장";
            txtSport.setText("다목적경기장");


        }else if (sports.equals("골프")){
            testUrl = testUrl+ "/파크골프장";
            txtSport.setText("파크골프장");


        }else if (sports.equals("배드민턴")){
            testUrl = testUrl+ "/배드민턴장";
            txtSport.setText("배드민턴장");


        }else if (sports.equals("운동장")){
            testUrl = testUrl+ "/운동장";
            txtSport.setText("운동장");

        }else if (sports.equals("체육관")){
            testUrl = testUrl+ "/체육관";
            txtSport.setText("체육관");


        }else if (sports.equals("둘레길")){
            txtSport.setText("둘레길");
            wayInfo();
            return;

        }else if (sports.equals("산")){
            testUrl = testUrl+ "/산";
            txtSport.setText("산");

        } else if (sports.equals("공원")){
            txtSport.setText("공원");
            natureInfo(natureTestUrl);
            return;
        }
        sportInfo(testUrl);



    }


    public void sportInfo(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("AAA","search response : "+response);
                try {
                    JSONObject sport = response.getJSONObject("ListPublicReservationSport");
                    list_total_count = sport.getInt("list_total_count");
                    JSONArray row = sport.getJSONArray("row");
                    for(int i = 0; i < row.length(); i++){

                        JSONObject object = row.getJSONObject(i);
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
                        Log.i("AAA","search for : "+svcId);

                        SportsInfo sportInfo = new SportsInfo(svcId,maxClassNm,minClassNm,svcStaTnm,svcNm,paYaTnm,
                                placeNm,useTgtInfo,svcUrl,x,y,svcOpnBgnDt,svcOpnEndDt,rcptBgnDt,rcptEndDt,areaNm,imgUrl,
                                dtlCont,telNo,v_min,v_max,revStdDayNm,revStdDay, 0);
                        sportInfoArrayList.add(sportInfo);
                    }
                    adapter = new RecyclerViewAdapter(ListActivity.this,sportInfoArrayList);
                    recyclerView.setAdapter(adapter);


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

    public void natureInfo(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","nature response : "+response);
                        try {
                            JSONObject nature = response.getJSONObject("SearchParkInfoService");
                            list_total_count = nature.getInt("list_total_count");
                            JSONArray row = nature.getJSONArray("row");
                            for (int i = 0; i < row.length(); i++){
                                JSONObject object = row.getJSONObject(i);
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
                                String x = object.getString("LONGITUDE");
                                String y = object.getString("LATITUDE");
                                String templateUrl = object.getString("TEMPLATE_URL");


                                NatureInfo natureInfo = new NatureInfo(pIdx,pPark,pListContent,area,openDt,mainEquip,mainPlants,
                                        guidance,visitRoad,useRefer,pImg,pZone,pAddr,pName,pAdmintel,x,y,templateUrl, 0);
                                natureInfoArrayList.add(natureInfo);
                            }
                            natureAdapter = new NatureRecyclerViewAdapter(ListActivity.this, natureInfoArrayList);
                            recyclerView.setAdapter(natureAdapter);
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
    public void wayInfo(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, wayTestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject way = response.getJSONObject("SeoulGilWalkCourse");
                            list_total_count = way.getInt("list_total_count");
                            JSONArray row = way.getJSONArray("row");
                            for (int i = 0; i < row.length(); i++){
                                JSONObject object = row.getJSONObject(i);
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

                                WayInfo wayInfo = new WayInfo(courseCategory,courseCategoryNm,southNorthDiv,southNorthDivNm,
                                        areaGu,distance,leadTime,courseLevel,voteCnt,relateSubway,trafficInfo,content,pdfFilePath,
                                        courseName,regDate,detailCourse,cpiIdx,cpiName,x,y,cpiContent, 0);
                                wayInfoArrayList.add(wayInfo);
                            }
                            wayAdapter = new WayRecyclerViewAdapter(ListActivity.this, wayInfoArrayList);
                            recyclerView.setAdapter(wayAdapter);
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



    // sport 즐겨찾기 추가 함수
    public void addSportFavorite(final int position){

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        SportsInfo sportsInfo = sportInfoArrayList.get(position);
        String idx = sportsInfo.getSvcId();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                testUrl + "/api/v1/favorite",
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

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        NatureInfo natureInfo = natureInfoArrayList.get(position);
        String idx = natureInfo.getpIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                natureTestUrl + "/api/v1/favorite",
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

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        WayInfo wayInfo = wayInfoArrayList.get(position);
        String idx = wayInfo.getCpiIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "localhost:5776/api/v1/favorite",
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

}