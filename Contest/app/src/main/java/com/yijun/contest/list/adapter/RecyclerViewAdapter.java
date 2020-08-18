package com.yijun.contest.list.adapter;


import android.content.Context;


import android.content.Intent;
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
import com.yijun.contest.favorite.data.DatabaseHandler;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import java.util.ArrayList;




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

        Favorite favorite = new Favorite();

        if (favorite.getIsFavorite() == 1){
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
                    Favorite favorite = new Favorite();
                    SportsInfo sportsInfo = sportInfosList.get(getAdapterPosition());
                    favorite.setId(sportsInfo.getSvcId());
                    favorite.setImgUrl(sportsInfo.getImgUrl());
                    favorite.setTitle(sportsInfo.getSvcNm());
                    favorite.setAddress(sportsInfo.getPlaceNm());
                    favorite.setPrice(sportsInfo.getPaYaTnm());
                    if (sportsInfo.getSvcStaTnm().equals("접수종료")){
                        favorite.setTime(sportsInfo.getSvcStaTnm());
                    }else {
                        if (sportsInfo.getV_max().isEmpty() || sportsInfo.getV_max().equals("")){
                            favorite.setTime(sportsInfo.getSvcStaTnm());
                        }
                        favorite.setTime(sportsInfo.getSvcStaTnm() +" : "+sportsInfo.getV_min()+" ~ "+sportsInfo.getV_max());
                    }

                    if (favorite.getIsFavorite() == 1){
                        favorite.setIsFavorite(android.R.drawable.btn_star_big_off);
                    }else {
                        favorite.setIsFavorite(android.R.drawable.btn_star_big_on);
                    }

                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addFavorite(favorite);
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
