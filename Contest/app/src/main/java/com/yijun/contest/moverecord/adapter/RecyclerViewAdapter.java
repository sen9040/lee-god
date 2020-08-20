package com.yijun.contest.moverecord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.moverecord.model.MoveRecord;
import com.yijun.contest.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<MoveRecord> moveRecordArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<MoveRecord> moveRecordArrayList) {
        this.context = context;
        this.moveRecordArrayList = moveRecordArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.move_record_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        MoveRecord moveRecord = moveRecordArrayList.get(position);
        String name = moveRecord.getName();
        String address = moveRecord.getAddress();

        holder.name.setText(name);
        holder.address.setText(address);
    }

    @Override
    public int getItemCount() {
        return moveRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        public ImageView img_check;
        public Button btn_url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            img_check = itemView.findViewById(R.id.img_check);
            btn_url = itemView.findViewById(R.id.btn_url);

            btn_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
