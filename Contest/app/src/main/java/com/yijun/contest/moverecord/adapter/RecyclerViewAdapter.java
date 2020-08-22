package com.yijun.contest.moverecord.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yijun.contest.DebouncedOnClickListener;
import com.yijun.contest.R;
import com.yijun.contest.fragment.FragmentFavorite;
import com.yijun.contest.moverecord.data.DatabaseHandler;
import com.yijun.contest.moverecord.model.MoveRecord;

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
        String title = moveRecord.getTitle();
        String address = moveRecord.getAddress();

        holder.name.setText(title);
        holder.address.setText(address);
    }

    @Override
    public int getItemCount() {
        return moveRecordArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        public ImageView img_delete;
        public Button btn_url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            img_delete = itemView.findViewById(R.id.img_delete);
            btn_url = itemView.findViewById(R.id.btn_url);

            btn_url.setOnClickListener(new DebouncedOnClickListener() {
                @Override
                public void onDebouncedClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    ArrayList<MoveRecord> moveRecordArrayList = db.getAllRecord();
                    MoveRecord moveRecord = moveRecordArrayList.get(getAdapterPosition());
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(moveRecord.getUrl()));
                    context.startActivity(i);
                }
            });

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("홈페이지 이동기록 삭제");
                    alert.setMessage("홈페이지 이동기록 목록에서\n삭제 하시겠습니까?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MoveRecord moveRecord = moveRecordArrayList.get(getAdapterPosition());
                            DatabaseHandler db = new DatabaseHandler(context);
                            db.deleteRecord(moveRecord);
                            moveRecordArrayList.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.setCancelable(false);
                    alert.show();

                }
            });

        }
    }
}
