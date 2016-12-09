package com.solitudeycq.hotmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class PictureViewHolder extends RecyclerView.ViewHolder {
    ImageView mImageView;
    public PictureViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.movie_pic);
    }
}
