package com.yijun.contest.Favorite.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.R;

public class RecyclerViewAdapter {

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
