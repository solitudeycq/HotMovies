package com.solitudeycq.hotmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";

    private RecyclerView mRecyclerView;
    private List<Integer> images = new ArrayList<Integer>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        for(int i = 0;i<20;i++) {
            images.add(R.drawable.default_pic);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_movies);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        PictureAdapter mPictureAdapter = new PictureAdapter(images);
        mRecyclerView.setAdapter(mPictureAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recyclerviewfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            Log.d(TAG, "刷新");
            return true;
        }
        if(id == R.id.action_settings){
            Log.d(TAG, "设置");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
