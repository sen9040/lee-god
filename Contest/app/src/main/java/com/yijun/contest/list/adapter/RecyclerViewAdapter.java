package com.yijun.contest.list.adapter;


import android.content.Context;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.yijun.contest.R;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<SportsInfo> sportInfosList;


    public ArrayList<SportsInfo> getSportInfosList() {
        return sportInfosList;
    }

    public RecyclerViewAdapter(Context context, ArrayList<SportsInfo> sportInfosList){

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

        if (imgUrl.isEmpty() || imgUrl.equals("")){

        }else {
            Glide.with(context).load(imgUrl).into(holder.imgSvc);
        }


        holder.txtSvcNm.setText(svcNm);
        holder.txtPlaceNm.setText(placeNm);
        holder.txtPaYaTnm.setText(paYaTnm);

        if (svcStaTnm.equals("접수종료")){
            holder.txtTime.setText(svcStaTnm);
        }else {
            if (v_max.isEmpty() || v_max.equals("")){
                holder.txtTime.setText(svcStaTnm);
            }
            holder.txtTime.setText(svcStaTnm +" : "+v_min+" ~ "+v_max);
        }



        if (sportInfo.getIsFavorite() == 1){
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }else {
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_off);
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

            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    int is_favorite = sportInfosList.get(position).getIsFavorite();
                    if (is_favorite == 0){
                        // 별표가 이미 있으면, 즐겨찾기 삭제 함수 호출!
                        ((ListActivity)context).addSportFavorite(position);
                    }else {
                        ((ListActivity)context).deleteSportFavorite(position);
                    }

                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 SportsInfo sportsInfo =  sportInfosList.get(getAdapterPosition());
              Intent i =new Intent(context, ViewDetailsActivity.class);
              i.putExtra("sports",sportsInfo);
              context.startActivity(i);
                }
            });
        }

    }
}
