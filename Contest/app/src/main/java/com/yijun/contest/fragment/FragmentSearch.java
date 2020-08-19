package com.yijun.contest.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.model.SportsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentSearch extends Fragment {

    Context context;
    Button btnSearch;

    public FragmentSearch(){

    }
    public FragmentSearch(Context context){
        this.context = context;
    }
    String testUrl = "http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/ListPublicReservationSport/1/5/%ED%85%8C%EB%8B%88%EC%8A%A4%EC%9E%A5";
    String baseUrl = "localhost:5776/api/v1/search";
    int list_total_count;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<SportsInfo> sportInfoArrayList = new ArrayList<>();

    EditText editSearch;

    LocationManager locationManager;
    LocationListener locationListener;
    double lat;
    double lng;

    RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editSearch = view.findViewById(R.id.editSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(context,bmb);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String keyword = editSearch.getText().toString().trim();
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

            Log.i("AAA","lat : "+lat +" lng : "+lng);
            String url = baseUrl + "?keyword=" + keyword;
            // todo 서버 연결후 작업
            getSearch(testUrl);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        adapter = new RecyclerViewAdapter(context,sportInfoArrayList);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }

    public void getLocation(){


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

    public void getSearch(String url){
        requestQueue = Volley.newRequestQueue(context);
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
                                dtlCont,telNo,v_min,v_max,revStdDayNm,revStdDay);
                        sportInfoArrayList.add(sportInfo);
                    }
                    adapter = new RecyclerViewAdapter(context,sportInfoArrayList);
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
}