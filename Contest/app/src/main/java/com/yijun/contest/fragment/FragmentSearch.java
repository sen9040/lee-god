package com.yijun.contest.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.list.adapter.NatureRecyclerViewAdapter;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.list.adapter.WayRecyclerViewAdapter;
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.utils.Utils;
import com.yijun.contest.weather.WeatherActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentSearch extends Fragment {

    Context context;
    Button btnSearch;
    String keyword;
    String url;
    int cnt;
    private String event = "";
    private String baseUrl;
    private WayRecyclerViewAdapter wayAdapter;
    private NatureRecyclerViewAdapter natureAdapter;

    public FragmentSearch(){

    }
    public FragmentSearch(Context context){
        this.context = context;
    }


    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<SportsInfo> sportInfoArrayList = new ArrayList<>();
    ArrayList<NatureInfo> natureInfoArrayList = new ArrayList<>();
    ArrayList<WayInfo> wayInfoArrayList = new ArrayList<>();

    EditText editSearch;
    RadioGroup radioGroup;
    RadioButton radioSport;
    RadioButton radioNature;
    RadioButton radioWay;

    LocationManager locationManager;
    LocationListener locationListener;
    double lat;
    double lng;
    int offset = 0;
    RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editSearch = view.findViewById(R.id.editSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        radioSport = view.findViewById(R.id.radioSport);
        radioNature = view.findViewById(R.id.radioNature);
        radioWay = view.findViewById(R.id.radioWay);
        radioGroup = view.findViewById(R.id.radioGroup);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(context,bmb);

        radioSport.setChecked(true);



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
                    Log.i("AAA","serach cnt : " + cnt);
                    if(cnt != 0){
                        if (radioGroup.getCheckedRadioButtonId() == R.id.radioSport){
                            event = "sport";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/sportsearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getSportInfo(url,offset,context,recyclerView);
                        }else if (radioGroup.getCheckedRadioButtonId() == R.id.radioNature){
                            event = "nature";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/parksearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getNatureInfo(url,offset,context,recyclerView);
                        }else if (radioGroup.getCheckedRadioButtonId() == R.id.radioWay){
                            event = "way";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/waysearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getWayInfo(url,offset,context,recyclerView);
                        }


                    }
                }
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTypesTask task = new CheckTypesTask();
                task.execute();


                offset = 0;
            keyword = editSearch.getText().toString().trim();
            if (keyword.equals("") || keyword.isEmpty()){
                Toast.makeText(context, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

                locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        Log.i("AAA","lat : "+lat +" lng : "+lng);

                        if (radioGroup.getCheckedRadioButtonId() == R.id.radioSport){
                            event = "sport";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/sportsearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getSportInfo(url,offset,context,recyclerView);
                        }else if (radioGroup.getCheckedRadioButtonId() == R.id.radioNature){
                            event = "nature";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/parksearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getNatureInfo(url,offset,context,recyclerView);
                            Log.i("AAA","search nature : "+url);
                        }else if (radioGroup.getCheckedRadioButtonId() == R.id.radioWay){
                            event = "way";
                            baseUrl =Utils.SERVER_BASE_URL +"/api/v1/search/waysearch"+ "?keyword=" + keyword+"&lat="+lat+"&lng="+lng;
                            url = baseUrl+"&offset="+offset;
                            getWayInfo(url,offset,context,recyclerView);
                        }
                        Log.i("AAA","search radio event: "+event);
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
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000*60, 100, locationListener);


            }
        });

        return view;
    }

    private  class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("검색 중..");
            asyncDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void...arg0){
            try {
                for(int i = 0; i<5; i++){
                    asyncDialog.setProgress(i*90);
                    Thread.sleep(5000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result){
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }

    }


    @Override
    public void onResume() {
        adapter = new RecyclerViewAdapter(context,sportInfoArrayList);
        recyclerView.setAdapter(adapter);
        super.onResume();
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
                    if (cnt == 0){
                        Toast.makeText(context, "표시 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
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

                        SportsInfo sportInfo = new SportsInfo(svcId,maxClassNm,minClassNm,svcStaTnm,svcNm,paYaTnm,
                                placeNm,useTgtInfo,svcUrl,x,y,svcOpnBgnDt,svcOpnEndDt,rcptBgnDt,rcptEndDt,areaNm,imgUrl,
                                dtlCont,telNo,v_min,v_max,revStdDayNm,revStdDay,distance);
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
                                double distance = object.getDouble("distance");

                                NatureInfo natureInfo = new NatureInfo(pIdx,pPark,pListContent,area,openDt,mainEquip,mainPlants,
                                        guidance,visitRoad,useRefer,pImg,pZone,pAddr,pName,pAdmintel,x,y,templateUrl, 0,distance);
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
                            Log.i("AAA","search nature volley offset : " + offset);
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

                                WayInfo wayInfo = new WayInfo(courseCategory,courseCategoryNm,southNorthDiv,southNorthDivNm,
                                        areaGu,distance,leadTime,courseLevel,voteCnt,relateSubway,trafficInfo,content,pdfFilePath,
                                        courseName,regDate,detailCourse,cpiIdx,cpiName,x,y,cpiContent, 0);
                                wayInfoArrayList.add(wayInfo);
                            }
                            if (offset_cnt == 0){
                                wayAdapter = new WayRecyclerViewAdapter(volleyContext, wayInfoArrayList);
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

}