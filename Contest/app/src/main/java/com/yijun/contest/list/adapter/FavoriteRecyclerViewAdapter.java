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
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecyclerViewAdapter.ViewHolder holder, int position) {

        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String address = favorite.getAddress();
        String price = favorite.getPrice();
        String time = favorite.getTime();
        String imgUrl = favorite.getImgUrl();

        if (imgUrl.isEmpty() || imgUrl.equals("")){

        }else {
            Glide.with(context).load(imgUrl).into(holder.img);
        }

        if (favorite.getIsFavorite() == 1){
            holder.title.setText(title);
            holder.address.setText(address);
            holder.price.setText(price);
            holder.time.setText(time);
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }


    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView img;
        TextView title;
        TextView address;
        TextView price;
        TextView time;
        ImageView imgFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);

            final int position = getAdapterPosition();

            int is_favorite = favoriteArrayList.get(position).getIsFavorite();
            if (is_favorite == 1){
                FragmentFavorite ff = new FragmentFavorite();
                // 별표가 이미 있으면, 즐겨찾기 보기 함수 호출!(FragmentFavorite 에 있는거)
                ff.getSportFavoriteData(position);
                ff.getParkFavoriteData(position);
                ff.getWayFavoriteData(position);
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
