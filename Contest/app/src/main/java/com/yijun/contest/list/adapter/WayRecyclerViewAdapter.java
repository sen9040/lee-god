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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.DebouncedOnClickListener;
import com.yijun.contest.R;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.model.WayInfo;
import com.yijun.contest.network.CheckNetwork;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

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
        double cd = wayInfo.getCurDistance();
        double distanceNum = Math.round(cd*100)/100.0;
        holder.txtSvcNm.setText(cpiName);
        holder.txtPlaceNm.setText(detailCourse);
        holder.txtPaYaTnm.setText(distance+"+"+distanceNum+"km");
        holder.txtTime.setText(leadTime);
        holder.imgSvc.setImageResource(R.drawable.walk);


        if (wayInfo.getIsFavorite() == 1){
            holder.imgFavorite.setImageResource(android.R.drawable.star_on);
        }else {
            holder.imgFavorite.setImageResource(android.R.drawable.star_off);
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

            imgFavorite.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    if(!CheckNetwork.isNetworkAvailable(context)){
                        Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final int position = getAdapterPosition();

                    int is_favorite = wayInfoArrayList.get(position).getIsFavorite();

                    if (is_favorite == 0) {


                        final String id =
                                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).trim();
                        final WayInfo wayInfo = wayInfoArrayList.get(getAdapterPosition());
                        final String idx = wayInfo.getCpiName();

                        if (is_favorite == 0) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("즐겨찾기 추가");
                            alert.setMessage("즐겨찾기 목록에 추가 하시겠습니까?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    FragmentFavorite frag = new FragmentFavorite();
                                    (frag).addWayFavorite(idx, id, context);
                                    wayInfoArrayList.get(position).setIsFavorite(1);
                                    notifyDataSetChanged();
                                }
                            });
                            alert.setNegativeButton("No", null);
                            alert.setCancelable(false);
                            alert.show();

                        } else if (is_favorite == 1) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("즐겨찾기 삭제");
                            alert.setMessage("즐겨찾기 목록에서 삭제 하시겠습니까?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FragmentFavorite frag = new FragmentFavorite();
                                    (frag).deleteWayFavorite(idx, id, context);
                                    wayInfoArrayList.get(position).setIsFavorite(0);
                                    notifyDataSetChanged();
                                }
                            });
                            alert.setNegativeButton("No", null);
                            alert.setCancelable(false);
                            alert.show();

                        }
                    }
                }
            });

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!CheckNetwork.isNetworkAvailable(context)) {
                                Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent i = new Intent(context, ViewDetailsActivity.class);
                            WayInfo wayInfo = wayInfoArrayList.get(getAdapterPosition());

                            i.putExtra("sports", wayInfo);
                            i.putExtra("key", 3);
                            context.startActivity(i);
                        }
                    });
        }
    }
}
