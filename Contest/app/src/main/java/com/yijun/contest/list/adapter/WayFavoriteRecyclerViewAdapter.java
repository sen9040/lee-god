package com.yijun.contest.list.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
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
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import java.util.ArrayList;

public class WayFavoriteRecyclerViewAdapter extends RecyclerView.Adapter<WayFavoriteRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Favorite> favoriteArrayList;

    public WayFavoriteRecyclerViewAdapter(Context context, ArrayList<Favorite> favoriteArrayList) {
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
    }

    @NonNull
    @Override
    public WayFavoriteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WayFavoriteRecyclerViewAdapter.ViewHolder holder, int position) {
        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String address = favorite.getAddress();
        String price = favorite.getPrice();
        String time = favorite.getTime();
        int isFavorite = favorite.getIsFavorite();

        if (isFavorite == 1){
            holder.title.setText(title);
            holder.address.setText(address);
            holder.price.setText(price);
            holder.time.setText(time);
            holder.img.setImageResource(R.drawable.walk);
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }else{
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_off);
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

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    return false;
                }
            });

            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    Favorite favorite = favoriteArrayList.get(position);
                    int isFavorite = favorite.getIsFavorite();
                    if (isFavorite == 1){
                        final String id =
                                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).trim();

                        final String idx = favoriteArrayList.get(position).getIdx();
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 삭제");
                        alert.setMessage("즐겨찾기 목록에서 삭제 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).deleteWayFavorite(idx,id,context);
                                favoriteArrayList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No",null);
                        alert.setCancelable(false);
                        alert.show();

                    }

                }
            });


        }
    }
}
