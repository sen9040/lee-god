package com.yijun.contest.Favorite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.Favorite.model.Favorite;
import com.yijun.contest.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Favorite> favoriteArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Favorite> favoriteArrayList) {
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String img = favorite.getImg_url();
        String address = favorite.getAddress();
        String priceTime = favorite.getPriceTime();

        holder.title.setText(title);
        holder.address.setText(address);
        holder.priceTime.setText(priceTime);
    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView img;
        public TextView address;
        public TextView priceTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            address = itemView.findViewById(R.id.address);
            priceTime = itemView.findViewById(R.id.priceTime);

        }
    }
}
