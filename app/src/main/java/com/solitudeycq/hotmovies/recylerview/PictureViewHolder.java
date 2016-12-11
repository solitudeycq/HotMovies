package com.solitudeycq.hotmovies.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.solitudeycq.hotmovies.R;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class PictureViewHolder extends RecyclerView.ViewHolder {
    ImageView mImageView;
    RatingBar mRatingBar;
    TextView mRating;
    TextView mMovieName;
    public PictureViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.movie_pic);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);
        mRating  = (TextView) itemView.findViewById(R.id.rating_mark);
        mMovieName = (TextView) itemView.findViewById(R.id.movie_name);
    }
}
