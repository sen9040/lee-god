package com.yijun.contest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.list.adapter.NatureFavoriteRecyclerViewAdapter;
import com.yijun.contest.list.adapter.NatureRecyclerViewAdapter;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.list.adapter.SportFavoriteRecyclerViewAdapter;
import com.yijun.contest.list.adapter.WayFavoriteRecyclerViewAdapter;
import com.yijun.contest.list.adapter.WayRecyclerViewAdapter;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentFavorite extends Fragment {

    RecyclerView sportRecyclerView;
    RecyclerView parkRecyclerView;
    RecyclerView wayRecyclerView;

    SportFavoriteRecyclerViewAdapter sportFavoriteRecyclerViewAdapter;
    NatureFavoriteRecyclerViewAdapter natureFavoriteRecyclerViewAdapter;
    WayFavoriteRecyclerViewAdapter wayFavoriteRecyclerViewAdapter;



    ArrayList<Favorite> favoriteArrayList1 = new ArrayList<>();
    ArrayList<Favorite> favoriteArrayList2 = new ArrayList<>();
    ArrayList<Favorite> favoriteArrayList3 = new ArrayList<>();

    int offset = 0;
    int cnt;
    private String idByANDROID_ID;
    private String parkUrl;
    private String sportUrl;
    private String wayUrl;


    public FragmentFavorite(){
    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(getActivity(),bmb);

        idByANDROID_ID =
                Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID).trim();
        sportRecyclerView = view.findViewById(R.id.sportRecyclerView);
        sportRecyclerView.setHasFixedSize(true);
        sportRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        getSportFavoriteData();

        parkRecyclerView = view.findViewById(R.id.parkRecyclerView);
        parkRecyclerView.setHasFixedSize(true);
        parkRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        getParkFavoriteData();

        wayRecyclerView = view.findViewById(R.id.wayRecyclerView);
        wayRecyclerView.setHasFixedSize(true);
        wayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        getWayFavoriteData();


        return view;
    }

    // 스포츠 즐겨찾기데이터 전부 가져오기
    public void getSportFavoriteData() {

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit
        sportUrl = Utils.SERVER_BASE_URL+"/api/v1/favorite/sport?id="+idByANDROID_ID+"&offset="+offset;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,sportUrl
                ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            int cnt = response.getInt("cnt");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(getActivity(), "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String imgUrl = jsonObject.getString("IMGURL");
                                    String SvcNm = jsonObject.getString("SVCNM");
                                    String PlaceNm = jsonObject.getString("PLACENM");
                                    String PaYaTnm = jsonObject.getString("PAYATNM");
                                    String SvcStaTnm = jsonObject.getString("SVCSTATNM");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, imgUrl, SvcNm, PlaceNm, PaYaTnm, SvcStaTnm, is_favorite);
                                    favoriteArrayList1.add(favorite);
                                }
                                sportFavoriteRecyclerViewAdapter = new SportFavoriteRecyclerViewAdapter(getActivity(), favoriteArrayList1);
                                sportRecyclerView.setAdapter(sportFavoriteRecyclerViewAdapter);

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

        Volley.newRequestQueue(getActivity()).add(request);
    }

    // 공원 즐겨찾기데이터 전부 가져오기
    public void getParkFavoriteData() {

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit
        parkUrl = Utils.SERVER_BASE_URL+"/api/v1/favorite/park?id="+idByANDROID_ID+"&offset="+offset;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,parkUrl
                ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            int cnt = response.getInt("cnt");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(getActivity(), "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String pImg = jsonObject.getString("P_IMG");
                                    String pPark = jsonObject.getString("P_PARK");
                                    String pAddr = jsonObject.getString("P_ADDR");
                                    String pName = jsonObject.getString("P_NAME");
                                    String pAdmintel = jsonObject.getString("P_ADMINTEL");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, pImg, pPark, pAddr, pName, pAdmintel, is_favorite);
                                    favoriteArrayList2.add(favorite);
                                }
                                natureFavoriteRecyclerViewAdapter = new NatureFavoriteRecyclerViewAdapter(getActivity(), favoriteArrayList2);
                                parkRecyclerView.setAdapter(natureFavoriteRecyclerViewAdapter);

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
        Volley.newRequestQueue(getActivity()).add(request);
    }

    // 두드림길 즐겨찾기데이터 전부 가져오기
    public void getWayFavoriteData() {

//        Utils.BASEURL + "/api/v1/favorite" +"?offset="+offset+"&limit="+limit
        wayUrl = Utils.SERVER_BASE_URL+"/api/v1/favorite/way?id="+idByANDROID_ID+"&offset="+offset;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                wayUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            int cnt = response.getInt("cnt");
                            if (success == false){
                                // 유저한테 에러있다고 알리고 리턴.
                                Toast.makeText(getActivity(), "success가 false입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONArray items = response.getJSONArray("items");
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject jsonObject = items.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String idx = jsonObject.getString("idx");
                                    String cpiName = jsonObject.getString("CPI_NAME");
                                    String detailCourse = jsonObject.getString("DETAIL_COURSE");
                                    String distance = jsonObject.getString("DISTANCE");
                                    String leadTime = jsonObject.getString("LEAD_TIME");
                                    int is_favorite;
                                    if (items.getJSONObject(i).isNull("isFavorite")){
                                        is_favorite = 0;
                                    }else {
                                        is_favorite = items.getJSONObject(i).getInt("isFavorite");
                                    }

                                    Log.i("가져와", response.toString());

                                    Favorite favorite = new Favorite(id, idx, null, cpiName, detailCourse, distance, leadTime, is_favorite);
                                    favoriteArrayList3.add(favorite);
                                }
                                wayFavoriteRecyclerViewAdapter = new WayFavoriteRecyclerViewAdapter(getActivity(), favoriteArrayList3);
                                wayRecyclerView.setAdapter(wayFavoriteRecyclerViewAdapter);

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
        Volley.newRequestQueue(getActivity()).add(request);
    }

        // 스포츠
    public void addSportFavorite(String idx, String id, final Context context){

        Log.i("AAA","idx : "+idx+", id "+id);



        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("isFavorite", 1);
            body.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AAA"," delete error : "+error);
                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }

    // 공원 즐겨찾기 추가
    public void addNatureFavorite(String idx, String id, final Context context){


        // 서버에 보내기 위해서 필요


        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",id);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }

    // 두드림길 즐겨찾기추가
    public void addWayFavorite(String idx, String id, final Context context){




        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",id);
            body.put("isFavorite", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "즐겨찾기 삭제", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }

    // 스포츠 즐겨찾기 삭제
    public void deleteSportFavorite(String idx, String id, final Context context){

        Log.i("AAA","idx : "+idx+", id "+id);



        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(context, "즐겨찾기 삭제", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("AAA"," delete error : "+error);
                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }

    // 공원 즐겨찾기 삭제
    public void deleteNatureFavorite(String idx, String id, final Context context){


        // 서버에 보내기 위해서 필요


        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Toast.makeText(context, "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }

    // 두드림길 즐겨찾기 삭제
    public void deleteWayFavorite(String idx, String id, final Context context){




        JSONObject body = new JSONObject();
        try {
            body.put("idx", idx);
            body.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Utils.SERVER_BASE_URL+"/api/v1/favorite/delete",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "즐겨찾기 삭제", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(context).add(request);
    }
}