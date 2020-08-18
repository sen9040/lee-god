package com.yijun.contest.Icon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.Icon.model.Icon;
import com.yijun.contest.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Icon> iconArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Icon> iconArrayList) {
        this.context = context;
        this.iconArrayList = iconArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Icon icon = iconArrayList.get(position);
        String title = icon.getTitle();
        String address = icon.getAddress();
        String img_url = icon.getImg_url();
        String priceTime = icon.getPriceTime();

        holder.title.setText(title);
        holder.address.setText(address);
        holder.priceTime.setText(priceTime);
    }

    @Override
    public int getItemCount() {
        return iconArrayList.size();
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
