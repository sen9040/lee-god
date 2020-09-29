package com.yijun.contest.ranking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.R;
import com.yijun.contest.model.Favorite;

import java.util.ArrayList;

public class RankingViewAdapter extends RecyclerView.Adapter<RankingViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Favorite> favoriteArrayList;

    public RankingViewAdapter(Context context, ArrayList<Favorite> favoriteArrayList) {
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
    }



    @NonNull
    @Override
    public RankingViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewAdapter.ViewHolder holder, int position) {
        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String content = favorite.getContent();
        double cnt =Math.round(favorite.getCurDistance()) ;


        holder.txtSvcNm.setText(title);
        holder.txtPlaceNm.setText(content);
        holder.txtPaYaTnm.setText(cnt+"점");
    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        TextView txtSvcNm;
        TextView txtPlaceNm;
        TextView txtPaYaTnm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);

            txtSvcNm = itemView.findViewById(R.id.txtSvcNm);
            txtPlaceNm = itemView.findViewById(R.id.txtPlaceNm);
            txtPaYaTnm = itemView.findViewById(R.id.txtPaYaTnm);

        }
    }
}
