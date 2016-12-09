package com.solitudeycq.hotmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {
    List<Integer> images;
    public PictureAdapter(List<Integer> resources){
        images = resources;
    }
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.list_item_movie,null);
        return new PictureViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        holder.mImageView.setImageResource(images.get(position).intValue());
    }
    @Override
    public int getItemCount() {
        if(images!=null){
            return images.size();
        }else{
            return 0;
        }
    }
}
