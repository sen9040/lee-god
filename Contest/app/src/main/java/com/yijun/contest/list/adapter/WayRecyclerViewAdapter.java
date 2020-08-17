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
        String courseCategoryName = wayInfo.getCourseCategoryNm();
        String detailCourse = wayInfo.getDatailCourse();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imgSvc = itemView.findViewById(R.id.imgSvc);
            txtSvcNm = itemView.findViewById(R.id.txtSvcNm);
            txtPlaceNm = itemView.findViewById(R.id.txtPlaceNm);
            txtPaYaTnm = itemView.findViewById(R.id.txtPaYaTnm);
            txtTime = itemView.findViewById(R.id.txtTime);

        }
    }
}
