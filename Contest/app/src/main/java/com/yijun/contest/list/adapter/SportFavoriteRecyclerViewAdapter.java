package com.yijun.contest.list.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import com.yijun.contest.model.SportsInfo;
import com.yijun.contest.network.CheckNetwork;
import com.yijun.contest.viewdetails.ViewDetailsActivity;

import java.util.ArrayList;

public class SportFavoriteRecyclerViewAdapter extends RecyclerView.Adapter<SportFavoriteRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Favorite> favoriteArrayList;

    public SportFavoriteRecyclerViewAdapter(Context context, ArrayList<Favorite> favoriteArrayList) {
        this.context = context;
        this.favoriteArrayList = favoriteArrayList;
    }

    @NonNull
    @Override
    public SportFavoriteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportFavoriteRecyclerViewAdapter.ViewHolder holder, int position) {
        Favorite favorite = favoriteArrayList.get(position);
        String title = favorite.getTitle();
        String address = favorite.getAddress();
        String price = favorite.getPrice();
        String time = favorite.getTime();
        String imgUrl = favorite.getImgUrl();
        int isFavorite = favorite.getIsFavorite();

        if (isFavorite == 1){
            holder.title.setText(title);
            holder.address.setText(address);
            holder.price.setText(price);
            holder.time.setText(time);
            holder.imgFavorite.setImageResource(R.drawable.heart_on);

            if (imgUrl == null || imgUrl.equals("")){
                holder.img.setImageResource(R.drawable.no_image);
            }else {
                Glide.with(context).load(imgUrl).into(holder.img);
            }
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

            imgFavorite.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    if(!CheckNetwork.isNetworkAvailable(context)){
                        Toast.makeText(context, "네트워크 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final int position = getAdapterPosition();
                    final String id =
                            Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).trim();
                    int is_favorite = favoriteArrayList.get(position).getIsFavorite();
                    final String idx = favoriteArrayList.get(position).getIdx();
                    if (is_favorite == 1){

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("즐겨찾기 삭제");
                        alert.setMessage("즐겨찾기 목록에서 삭제 하시겠습니까?");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentFavorite frag = new FragmentFavorite();
                                (frag).deleteSportFavorite(idx,id,context);
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
                    intent.putExtra("key",4);
                    context.startActivity(intent);
                }
            });

        }
    }
}
