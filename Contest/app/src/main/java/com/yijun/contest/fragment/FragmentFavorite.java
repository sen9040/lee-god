package com.yijun.contest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nightonke.boommenu.BoomMenuButton;
import com.yijun.contest.R;
import com.yijun.contest.boommenu.BoomMenu;
import com.yijun.contest.list.adapter.FavoriteRecyclerViewAdapter;
import com.yijun.contest.model.Favorite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentFavorite extends Fragment {
    Context context;
    RecyclerView recyclerView;
    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;
    ArrayList<Favorite> favoriteArrayList = new ArrayList<>();

    public FragmentFavorite(){
    }

    public FragmentFavorite(Context context){
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);;
        BoomMenuButton bmb = (BoomMenuButton)view.findViewById(R.id.bmb);
        BoomMenu boomMenu = new BoomMenu();
        boomMenu.getBoomMenu(context,bmb);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        return view;
    }
}