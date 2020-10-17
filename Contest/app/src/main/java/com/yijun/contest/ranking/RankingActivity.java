package com.yijun.contest.ranking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.MainActivity;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.adapter.RecyclerViewAdapter;
import com.yijun.contest.list.adapter.SportFavoriteRecyclerViewAdapter;
import com.yijun.contest.model.Favorite;

import com.yijun.contest.ranking.adapter.ChildAnimationExample;

import com.yijun.contest.ranking.adapter.RankingViewAdapter;
import com.yijun.contest.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class RankingActivity extends AppCompatActivity implements RankingActivityInterface {

    HashMap<String, String> url_maps;
    TextSliderView textSliderView;
    RecyclerView recyclerView;
    RankingViewAdapter rankingViewAdapter;


    private SliderLayout mDemoSlider;
    private ArrayList<Favorite> favoriteArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(RankingActivity.this, bmb,true);
        bmb.bringToFront();
        recyclerView = findViewById(R.id.recyclerViewRanking);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        url_maps = new HashMap<String, String>();




        String url = Utils.SERVER_BASE_URL + "/api/v1/ranking/count";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url
                ,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("RRR", "volley :" + response);


                            JSONArray array = response.getJSONArray("rows");


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                String id = jsonObject.getString("SVCID");
                                String areaNm = jsonObject.getString("AREANM");
                                String svcNm = jsonObject.getString("SVCNM");
                                String imgUrl = jsonObject.getString("IMGURL");
                                String svcUrl = jsonObject.getString("SVCURL");
                                String category = jsonObject.getString("Category");
                                int cnt = jsonObject.getInt("CNT");

                                if (category.equals("sport")){
                                    if (imgUrl.equals("")){
                                        imgUrl = "https://yeyak.seoul.go.kr/fileDownload.web?p=/TB_SVCIMG/2020/07/26/S200508085807880647&n=gxj5NouvYjy2Q1HQIb5gBc14bAt1e6&on=방배.jpg";
                                    }
                                    if (svcUrl.equals("")){
                                        svcUrl = "http://yeyak.seoul.go.kr/main.web";
                                    }

                                }else if (category.equals("park")){
                                    if (imgUrl.equals("")){
                                        imgUrl = "http://parks.seoul.go.kr/file/info/view.do?fIdx=19411";
                                    }
                                    if (svcUrl.equals("")){
                                        svcUrl = "http://parks.seoul.go.kr/";
                                    }
                                }else if (category.equals("way")){
                                    if (imgUrl.equals("")){
                                        imgUrl = "http://parks.seoul.go.kr/file/info/view.do?fIdx=1473";
                                    }
                                    if (svcUrl.equals("")){
                                        svcUrl = "http://gil.seoul.go.kr/walk/index.jsp";
                                    }
                                }

                                url_maps.put(svcNm,imgUrl);

                                Favorite favorite = new Favorite(id,areaNm,svcNm,imgUrl,svcUrl,category,cnt);
                                favoriteArrayList.add(favorite);
                            }


                            rankingViewAdapter = new RankingViewAdapter(RankingActivity.this,favoriteArrayList);
                            recyclerView.setAdapter(rankingViewAdapter);

                            for (String name : url_maps.keySet()) {
                                textSliderView = new TextSliderView(RankingActivity.this);
                                // initialize a SliderLayout
                                textSliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                            @Override
                                            public void onSliderClick(BaseSliderView slider) {

                                            }
                                        });

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);

                    

                                mDemoSlider.addSlider(textSliderView);
                            }
                            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                            mDemoSlider.setDuration(5000);
                            mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {

                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

//                            ListView l = (ListView) findViewById(R.id.transformers);
//                            l.setAdapter(new TransformerAdapter(RankingActivity.this));
//                            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
//                                    Toast.makeText(RankingActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
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

        Volley.newRequestQueue(RankingActivity.this).add(request);





    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.bat);
        file_maps.put("Big Bang Theory", R.drawable.bear);
        file_maps.put("House of Cards", R.drawable.snake);
        file_maps.put("Game of Thrones", R.drawable.dolphin);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ranking_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_custom_indicator:
                mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
                break;
            case R.id.action_custom_child_animation:
                mDemoSlider.setCustomAnimation(new ChildAnimationExample());
                break;
            case R.id.action_restore_default:
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                break;
            case R.id.action_github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia/AndroidImageSlider"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void getRanking() {



    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}

