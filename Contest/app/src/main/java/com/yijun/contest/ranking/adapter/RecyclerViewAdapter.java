package com.yijun.contest.ranking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.R;
import com.yijun.contest.ranking.model.Ranking;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<Ranking> rankingArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Ranking> rankingArrayList) {
        this.context = context;
        this.rankingArrayList = rankingArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent 로 부터 뷰(화면 : 하나의 셀)를 생성한다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_row,parent,false); //inflate=만들라는 뜻
        //리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Ranking ranking = rankingArrayList.get(position);
        String title = ranking.getTitle();
        String img = ranking.getImg_url();
        String address = ranking.getAddress();
        String priceTime = ranking.getPriceTime();

        holder.title.setText(title);
        holder.address.setText(address);
        holder.priceTime.setText((priceTime));
    }

    @Override
    public int getItemCount() {
        return rankingArrayList.size();
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
