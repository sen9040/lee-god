package com.yijun.contest.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yijun.contest.R;
<<<<<<< Updated upstream
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
=======
>>>>>>> Stashed changes
import com.yijun.contest.model.Favorite;
import com.yijun.contest.model.SportsInfo;

import java.util.ArrayList;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Favorite> favoriteArrayList;

    public FavoriteRecyclerViewAdapter(Context context, ArrayList<Favorite> favoriteArrayList) {
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
    }

    @NonNull
    @Override
    public FavoriteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerViewAdapter.ViewHolder holder, int position) {
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String address = favorite.getAddress();
        String price = favorite.getPrice();
        String time = favorite.getTime();
        String imgUrl = favorite.getImgUrl();

        if (imgUrl.isEmpty() || imgUrl.equals("")){

        }else {
            Glide.with(context).load(imgUrl).into(holder.imgSvc);
        }

        holder.txtSvcNm.setText(title);
        holder.txtPlaceNm.setText(address);
        holder.txtPaYaTnm.setText(price);
        holder.txtTime.setText(time);
        holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);


    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
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

            final int position = getAdapterPosition();

            int is_favorite = favoriteArrayList.get(position).getIsFavorite();
            if (is_favorite == 1){
                // 별표가 이미 있으면, 즐겨찾기 보기 함수 호출!(FragmentFavorite 에 있는거)
//                (()context).addSportFavorite(position);
//                ((ListActivity)context).addParkFavorite(position);
//                ((ListActivity)context).addWayFavorite(position);
            }

            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListActivity)context).deleteSportFavorite(position);
                    ((ListActivity)context).deleteParkFavorite(position);
                    ((ListActivity)context).deleteWayFavorite(position);
                }
            });

        }
    }
}
