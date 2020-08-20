package com.yijun.contest.viewdetails;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yijun.contest.R;
import com.yijun.contest.model.SportsInfo;

import java.util.ArrayList;

public class ViewDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ImageView imgSvc;
    TextView txtSvcNm;
    TextView txtPlaceNm;
    TextView txtPaYaTnm;
    TextView txtTime;
    ArrayList<SportsInfo> sportInfosList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imgSvc = findViewById(R.id.imgSvc);
        txtSvcNm = findViewById(R.id.txtSvcNm);
        txtPlaceNm = findViewById(R.id.txtPlaceNm);
        txtPaYaTnm = findViewById(R.id.txtPaYaTnm);
        txtTime = findViewById(R.id.txtTime);


     SportsInfo sportInfo =(SportsInfo) getIntent().getSerializableExtra("sports");

        String svcNm = sportInfo.getSvcNm();
        String placeNm = sportInfo.getPlaceNm();
        String paYaTnm = sportInfo.getPaYaTnm();
        String v_min = sportInfo.getV_min();
        String v_max = sportInfo.getV_max();
        String svcStaTnm = sportInfo.getSvcStaTnm();
        String imgUrl = sportInfo.getImgUrl();
        Log.i("AAA","detail imgUrl : "+imgUrl);
        if (imgUrl.isEmpty() || imgUrl.equals("") ){

        }else {
            Glide.with(ViewDetailsActivity.this).load(imgUrl).into(imgSvc);
        }


        txtSvcNm.setText(svcNm);
        txtPlaceNm.setText(placeNm);
        txtPaYaTnm.setText(paYaTnm);

        if (svcStaTnm.equals("접수종료")){
           txtTime.setText(svcStaTnm);
        }else {
            if (v_max.isEmpty() || v_max.equals("")){
                txtTime.setText(svcStaTnm);
            }
           txtTime.setText(svcStaTnm +" : "+v_min+" ~ "+v_max);
        }

//        if (sportInfo.getIsFavorite() == 1){
//            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
//        }else {
//            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_off);
//        }

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
