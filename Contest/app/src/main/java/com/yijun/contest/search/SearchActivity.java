package com.yijun.contest.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.utils.Utils;

import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    String searchBaseUrl = "http://openAPI.seoul.go.kr:8088/"+ Utils.authKey+"/json/ListPublicReservationDetail/1/5/S140714091649053869";

    EditText editSearch;
    Button btn;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editSearch = findViewById(R.id.editSearch);
        btn = findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editSearch.getText().toString().trim();

                requestQueue = Volley.newRequestQueue(SearchActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchBaseUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("AAA","search response : "+response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AAA","search error : "+error);
                    }
                });
                requestQueue.add(request);

            }
        });

    }
}