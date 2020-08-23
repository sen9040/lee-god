package com.yijun.contest.list.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.yijun.contest.DebouncedOnClickListener;
import com.yijun.contest.R;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.Parking;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.network.CheckNetwork;
import com.yijun.contest.ranking.RankingActivity;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<SportsInfo> sportInfosList;


    public RecyclerViewAdapter(Context context, ArrayList<SportsInfo> sportInfosList){
        Log.i("AAA","recyclerView create : ");
        this.context = context;
        this.sportInfosList = sportInfosList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 첫번째 파라미터인, parent 로 부터 뷰(화면:하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        // 리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {

        SportsInfo sportInfo = sportInfosList.get(position);
        String svcNm = sportInfo.getSvcNm();
        String placeNm = sportInfo.getPlaceNm();
        String paYaTnm = sportInfo.getPaYaTnm();
        String v_min = sportInfo.getV_min();
        String v_max = sportInfo.getV_max();
        String svcStaTnm = sportInfo.getSvcStaTnm();
        String imgUrl = sportInfo.getImgUrl();
        double distance = sportInfo.getDistance();



        // 이미지 설정
        if (imgUrl.isEmpty() || imgUrl.equals("")){
           holder.imgSvc.setImageResource(R.drawable.no_image);
        }else {
            Glide.with(context).load(imgUrl).into(holder.imgSvc);
        }
        // text 설정
        holder.txtSvcNm.setText(svcNm);
        double distanceNum = Math.round(distance*100)/100.0;
        holder.txtPlaceNm.setText("나와의 거리 : "+distanceNum+"Km");
        holder.txtPaYaTnm.setText(paYaTnm);

        if(svcStaTnm.equals("예약일시중지")){
            // 글자색 바꾸는 코드
            final SpannableStringBuilder sp = new SpannableStringBuilder(svcStaTnm);
            sp.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 0, svcStaTnm.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); sp.setSpan(new ForegroundColorSpan(Color.RED), 0, svcStaTnm.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.txtTime.setText(sp);
        }

        if (svcStaTnm.equals("접수종료")){

            final SpannableStringBuilder sp = new SpannableStringBuilder(svcStaTnm);
            sp.setSpan(new ForegroundColorSpan(Color.rgb(255, 255, 255)), 0, svcStaTnm.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); sp.setSpan(new ForegroundColorSpan(Color.RED), 0, svcStaTnm.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.txtTime.setText(sp);
        }
        if(!svcStaTnm.equals("접수종료") && !svcStaTnm.equals("예약일시중지")) {
            if (v_max.isEmpty() || v_max.equals("")){
                holder.txtTime.setText(svcStaTnm);
            }
            holder.txtTime.setText(svcStaTnm +" : "+v_min+" ~ "+v_max);
        }

        if (sportInfo.getIsFavorite() == 1){
            holder.imgFavorite.setImageResource(R.drawable.heart_on);
        }else {
            holder.imgFavorite.setImageResource(R.drawable.heart_off);
        }
    }



    @Override
    public int getItemCount() {
        return sportInfosList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imgSvc;
        TextView txtSvcNm;
        TextView txtPlaceNm;
        TextView txtPaYaTnm;
        TextView txtTime;
        ImageView imgFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imgSvc = itemView.findViewById(R.id.imgSvc);
            txtSvcNm = itemView.findViewById(R.id.txtSvcNm);
            txtPlaceNm = itemView.findViewById(R.id.txtPlaceNm);
            txtPaYaTnm = itemView.findViewById(R.id.txtPaYaTnm);
            txtTime = itemView.findViewById(R.id.txtTime);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);

            imgFavorite.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    if(!CheckNetwork.isNetworkAvailable(context)){
                        Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final int position = getAdapterPosition();
                    final String id =
                            Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).trim();
                    final SportsInfo sportsInfo = sportInfosList.get(getAdapterPosition());
                    final String idx = sportsInfo.getSvcId();
                    int is_favorite = sportInfosList.get(position).getIsFavorite();
                    if (is_favorite == 0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 추가");
                        alert.setMessage("즐겨찾기 목록에 추가 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).addSportFavorite(idx,id,context);
                                sportInfosList.get(position).setIsFavorite(1);
                                notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No",null);
                        alert.setCancelable(false);
                        alert.show();

                    }  else if (is_favorite == 1){

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 삭제");
                        alert.setMessage("즐겨찾기 목록에서 삭제 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).deleteSportFavorite(idx,id,context);
                                sportInfosList.get(position).setIsFavorite(0);
                                notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No",null);
                        alert.setCancelable(false);
                        alert.show();

                    }

                }
            });

          cardView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(!CheckNetwork.isNetworkAvailable(context)){
                      Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  Intent i = new Intent(context, ViewDetailsActivity.class);
                  SportsInfo sportsInfo =  sportInfosList.get(getAdapterPosition());

                  i.putExtra("sports",sportsInfo);
                  i.putExtra("key",1);
                  context.startActivity(i);

              }
          });
        }

    }
}
