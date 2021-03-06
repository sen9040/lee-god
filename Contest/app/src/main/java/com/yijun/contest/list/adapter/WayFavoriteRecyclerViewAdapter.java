package com.yijun.contest.list.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yijun.contest.DebouncedOnClickListener;
import com.yijun.contest.R;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.list.ListActivity;
import com.yijun.contest.model.Favorite;
import com.yijun.contest.network.CheckNetwork;
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
            double cd = favorite.getCurDistance();
            double distanceNum = Math.round(cd*100)/100.0;

            holder.title.setText(title);
            holder.address.setText(address);
            holder.price.setText(price);
            holder.time.setText(time+"+"+distanceNum+"Km");
            holder.img.setImageResource(R.drawable.walk);
            holder.imgFavorite.setImageResource(R.drawable.heart_on);
        }else{
            holder.imgFavorite.setImageResource(R.drawable.heart_off);
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

            imgFavorite.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    if(!CheckNetwork.isNetworkAvailable(context)){
                        Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
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

            cardView.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    if(!CheckNetwork.isNetworkAvailable(context)){
                        Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Favorite favorite = favoriteArrayList.get(getAdapterPosition());
                    Intent intent = new Intent(context,ViewDetailsActivity.class);
                    intent.putExtra("sports",favorite);
                    Log.i("AAA","way Favorite adapter title: " + favorite.getTitle());
                    intent.putExtra("key",4);
                    context.startActivity(intent);
                }
            });


        }
    }
}
