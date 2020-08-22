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
import com.yijun.contest.model.NatureInfo;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import java.util.ArrayList;

public class NatureRecyclerViewAdapter extends RecyclerView.Adapter<NatureRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<NatureInfo> natureInfoArrayList;

    public NatureRecyclerViewAdapter(Context context, ArrayList<NatureInfo> natureInfoArrayList) {
        this.context = context;
        this.natureInfoArrayList = natureInfoArrayList;
    }

    @NonNull
    @Override
    public NatureRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NatureRecyclerViewAdapter.ViewHolder holder, int position) {
        NatureInfo natureInfo = natureInfoArrayList.get(position);
        String pPark = natureInfo.getpPark();
        String pAddr = natureInfo.getpAddr();
        String pName = natureInfo.getpName();
        String pAdmintel = natureInfo.getpAdmintel();
        String pImg = natureInfo.getpImg();
        double distance = natureInfo.getDistance();
        if (pImg.isEmpty() || pImg.equals("")){
            holder.imgSvc.setImageResource(R.drawable.no_image);
        }else {
            Glide.with(context).load(pImg).into(holder.imgSvc);
        }
        double distanceNum = Math.round(distance*100)/100.0;
        holder.txtSvcNm.setText(pPark);
        holder.txtPlaceNm.setText("나와의 거리 : "+distanceNum+"Km");
        holder.txtPaYaTnm.setText("");
        holder.txtTime.setText(pAdmintel);

        if (natureInfo.getIsFavorite() == 1){
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        }else {
            holder.imgFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public int getItemCount() {
        return natureInfoArrayList.size();
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
                    final int position = getAdapterPosition();
                    final String id =
                            Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).trim();
                    final NatureInfo natureInfo = natureInfoArrayList.get(position);
                    final String idx = natureInfo.getpIdx();
                    int is_favorite = natureInfoArrayList.get(position).getIsFavorite();
                    if (is_favorite == 0){
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 추가");
                        alert.setMessage("즐겨찾기 목록에 추가 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).addNatureFavorite(idx,id,context);
                                natureInfoArrayList.get(position).setIsFavorite(1);
                                notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No",null);
                        alert.setCancelable(false);
                        alert.show();

                    }  else if (is_favorite == 1){

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 삭제");
                        alert.setMessage("즐겨찾기 목록에서 삭제 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).deleteNatureFavorite(idx,id,context);
                                natureInfoArrayList.get(position).setIsFavorite(0);
                                notifyDataSetChanged();
                            }
                        });
                        alert.setNegativeButton("No",null);
                        alert.setCancelable(false);
                        alert.show();

                    }

                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ViewDetailsActivity.class);
                    NatureInfo natureInfo =  natureInfoArrayList.get(getAdapterPosition());

                    i.putExtra("sports",natureInfo);
                    i.putExtra("key",2);
                    context.startActivity(i);
                }
            });
        }
    }
}
