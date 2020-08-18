package com.yijun.contest.Icon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yijun.contest.Icon.adapter.RecyclerViewAdapter;
import com.yijun.contest.Icon.model.Icon;
import com.yijun.contest.Icon.utils.Utils;
import com.yijun.contest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IconActivity extends AppCompatActivity {

    TextView iconName;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Icon> iconArrayList = new ArrayList<>();
    RequestQueue requestQueue;

    int list_total_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);

        iconName = findViewById(R.id.iconName);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(IconActivity.this));

        getNetworkData();

    }

    public void getNetworkData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utils.BASEURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject seoulGilWalkCourse = response.getJSONObject("SeoulGilWalkCourse");
                            list_total_count = seoulGilWalkCourse.getInt("list_total_count");
                            JSONArray row = seoulGilWalkCourse.getJSONArray("row");
                            for (int i = 0; i < row.length(); i++){
                                JSONObject jsonObject = row.getJSONObject(i);
                                String cpi_name = jsonObject.getString("CPI_NAME");
                                String category_name = jsonObject.getString("COURSE_CATEGORY_NM");
                                String detail_course = jsonObject.getString("DETAIL_COURSE");

                                Icon icon = new Icon(cpi_name,category_name,detail_course);
                                iconArrayList.add(icon);
                            }
                            recyclerViewAdapter = new RecyclerViewAdapter(IconActivity.this, iconArrayList);
                            recyclerView.setAdapter(recyclerViewAdapter);
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
        requestQueue.add(jsonObjectRequest);
    }
}
