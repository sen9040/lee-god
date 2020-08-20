package com.yijun.contest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.list.adapter.FavoriteRecyclerViewAdapter;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentFavorite extends Fragment {
    Context context;
    RecyclerView sportRecyclerView;
    RecyclerView parkRecyclerView;
    RecyclerView wayRecyclerView;

    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;
    ArrayList<Favorite> favoriteArrayList = new ArrayList<>();
    RequestQueue requestQueue;

    public FragmentFavorite(){
    }

    public FragmentFavorite(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);;
        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(context,bmb);

        sportRecyclerView = view.findViewById(R.id.sportRecyclerView);
        sportRecyclerView.setHasFixedSize(true);
        sportRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        parkRecyclerView = view.findViewById(R.id.parkRecyclerView);
        parkRecyclerView.setHasFixedSize(true);
        parkRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        wayRecyclerView = view.findViewById(R.id.wayRecyclerView);
        wayRecyclerView.setHasFixedSize(true);
        wayRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        requestQueue = Volley.newRequestQueue(context);


        return view;
    }

    // 스포츠 즐겨찾기데이터 전부 가져오기
    public void getSportFavoriteData(int position) {

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        Favorite favorite = favoriteArrayList.get(position);
        String idx = favorite.getIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(context, "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String imgUrl = jsonObject.getString("ImgUrl");
                                    String SvcNm = jsonObject.getString("SvcNm");
                                    String PlaceNm = jsonObject.getString("PlaceNm");
                                    String PaYaTnm = jsonObject.getString("PaYaTnm");
                                    String SvcStaTnm = jsonObject.getString("SvcStaTnm");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, imgUrl, SvcNm, PlaceNm, PaYaTnm, SvcStaTnm, is_favorite);
                                    favoriteArrayList.add(favorite);
                                }
                                favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(context, favoriteArrayList);
                                sportRecyclerView.setAdapter(favoriteRecyclerViewAdapter);

                                // 페이징을 위해서, 오프셋을 증가 시킨다. 그래야 리스트 끝에가서 네트워크 다시 호출할 때,
                                // 해당 offset으로 서버에 요청이 가능하다.
//                                offset = offset + response.getInt("count");
//                                cnt = response.getInt("count");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("가져와", error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

    // 공원 즐겨찾기데이터 전부 가져오기
    public void getParkFavoriteData(int position) {

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        Favorite favorite = favoriteArrayList.get(position);
        String idx = favorite.getIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(context, "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String pImg = jsonObject.getString("pImg");
                                    String pPark = jsonObject.getString("pPark");
                                    String pAddr = jsonObject.getString("pAddr");
                                    String pName = jsonObject.getString("pName");
                                    String pAdmintel = jsonObject.getString("pAdmintel");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, pImg, pPark, pAddr, pName, pAdmintel, is_favorite);
                                    favoriteArrayList.add(favorite);
                                }
                                favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(context, favoriteArrayList);
                                parkRecyclerView.setAdapter(favoriteRecyclerViewAdapter);

                                // 페이징을 위해서, 오프셋을 증가 시킨다. 그래야 리스트 끝에가서 네트워크 다시 호출할 때,
                                // 해당 offset으로 서버에 요청이 가능하다.
//                                offset = offset + response.getInt("count");
//                                cnt = response.getInt("count");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("가져와", error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

    // 두드림길 즐겨찾기데이터 전부 가져오기
    public void getWayFavoriteData(int position) {

        // position을 통해서, 즐겨찾기 추가할 movie_id 값을 가져올 수 있습니다.
        Favorite favorite = favoriteArrayList.get(position);
        String idx = favorite.getIdx();

        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(context, "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String cpiName	 = jsonObject.getString("CpiName");
                                    String detailCourse = jsonObject.getString("DetailCourse");
                                    String distance = jsonObject.getString("Distance");
                                    String leadTime = jsonObject.getString("LeadTime");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, null, cpiName, detailCourse, distance, leadTime, is_favorite);
                                    favoriteArrayList.add(favorite);
                                }
                                favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(context, favoriteArrayList);
                                wayRecyclerView.setAdapter(favoriteRecyclerViewAdapter);

                                // 페이징을 위해서, 오프셋을 증가 시킨다. 그래야 리스트 끝에가서 네트워크 다시 호출할 때,
                                // 해당 offset으로 서버에 요청이 가능하다.
//                                offset = offset + response.getInt("count");
//                                cnt = response.getInt("count");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("가져와", error.toString());
                    }
                }
        );
        requestQueue.add(request);
    }

}