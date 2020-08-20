package com.yijun.contest.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.R;
<<<<<<< Updated upstream
import com.yijun.contest.list.ListActivity;
=======
>>>>>>> Stashed changes
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;

import java.util.ArrayList;

public class WayRecyclerViewAdapter extends RecyclerView.Adapter<WayRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<WayInfo> wayInfoArrayList;

    public WayRecyclerViewAdapter(Context context, ArrayList<WayInfo> wayInfoArrayList) {
        this.context = context;
        this.wayInfoArrayList = wayInfoArrayList;
    }

    @NonNull
    @Override
    public WayRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WayRecyclerViewAdapter.ViewHolder holder, int position) {
        WayInfo wayInfo = wayInfoArrayList.get(position);
        String cpiName = wayInfo.getCpiName();
        String detailCourse = wayInfo.getDetailCourse();
        String distance = wayInfo.getDistance();
        String leadTime = wayInfo.getLeadTime();

        holder.txtSvcNm.setText(cpiName);
        holder.txtPlaceNm.setText(detailCourse);
        holder.txtPaYaTnm.setText(distance);
        holder.txtTime.setText(leadTime);

        if (wayInfo.getIsFavorite() == 1){
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }else {
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public int getItemCount() {
        return wayInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
<<<<<<< Updated upstream
                    int position = getAdapterPosition();

                    int is_favorite = wayInfoArrayList.get(position).getIsFavorite();
                    if (is_favorite == 0){
                        // 별표가 이미 있으면, 즐겨찾기 삭제 함수 호출!
                        ((ListActivity)context).addWayFavorite(position);
                    }
=======
>>>>>>> Stashed changes

                }
            });

        }
    }
}
