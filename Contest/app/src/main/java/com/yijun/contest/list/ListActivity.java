package com.yijun.contest.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yijun.contest.R;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.model.SportInfo;
import com.yijun.contest.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    TextView txtSport;
    RecyclerView recyclerView;

    RequestQueue requestQueue;

    String testUrl = "http://openapi.seoul.go.kr:8088/765867555473656e353874786d6572/json/ListPublicReservationSport/1/5/%ED%85%8C%EB%8B%88%EC%8A%A4%EC%9E%A5";

    int list_total_count;
    RecyclerViewAdapter adapter;
    ArrayList<SportInfo> sportInfoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtSport = findViewById(R.id.txtSport);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));


        requestQueue = Volley.newRequestQueue(ListActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, testUrl, null, new Response.Listener<JSONObject>() {
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

                        SportInfo sportInfo = new SportInfo(svcId,maxClassNm,minClassNm,svcStaTnm,svcNm,paYaTnm,
                                placeNm,useTgtInfo,svcUrl,x,y,svcOpnBgnDt,svcOpnEndDt,rcptBgnDt,rcptEndDt,areaNm,imgUrl,
                                dtlCont,telNo,v_min,v_max,revStdDayNm,revStdDay);
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
}