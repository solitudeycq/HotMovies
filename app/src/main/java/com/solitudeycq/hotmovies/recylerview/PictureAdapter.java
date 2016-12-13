package com.solitudeycq.hotmovies.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.bean.Movie;
import com.solitudeycq.hotmovies.utils.LogControl;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {
    private static final String TAG = "PictureAdapter";
    private OnItemClickLitener mOnItemClickLitener;
    List<Movie> images;
    public PictureAdapter(List<Movie> resources){
        images = resources;
    }
    //设置监听事件
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_movie,null);
        return new PictureViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final PictureViewHolder holder, int position) {
        if(images.size()==0){
            holder.mImageView.setImageResource(R.drawable.default_pic);
        }else{
            Picasso.with(holder.itemView.getContext())
                    .load(images.get(position).getPicture())
                    .placeholder(R.drawable.default_pic)
                    .into(holder.mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            LogControl.d(TAG,"图片加载成功！");
                        }
                        @Override
                        public void onError() {
                            Toast.makeText(holder.itemView.getContext(),"图片加载失败，使用默认图片代替",Toast.LENGTH_SHORT).show();
                        }
                    });
            holder.mMovieName.setText(images.get(position).getName());
            holder.mRating.setText(images.get(position).getRating());
            holder.mRatingBar.setRating((Float.parseFloat(images.get(position).getRating()))/2.0f);
        }

        if(mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
        }
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
