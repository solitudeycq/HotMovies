package com.solitudeycq.hotmovies.recylerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by solitudeycq on 2016/12/11.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    public SpaceItemDecoration(int space) {
        this.mSpace =space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildPosition(view);
        if(position%3==0){
            if (position==0){
                outRect.right = mSpace/2;
                outRect.bottom = mSpace/2;
            }else{
                outRect.top = mSpace/2;
                outRect.right = mSpace/2;
                outRect.bottom = mSpace/2;
            }
        }else if (position%3==1){
            if(position == 1){
                outRect.left = mSpace/2;
                outRect.right = mSpace/2;
                outRect.bottom = mSpace/2;
            }else{
                outRect.top = mSpace/2;
                outRect.left = mSpace/2;
                outRect.right = mSpace/2;
                outRect.bottom = mSpace/2;
            }
        }else{
            if(position==2){
                outRect.left = mSpace/2;
                outRect.bottom = mSpace/2;
            }else{
                outRect.left = mSpace/2;
                outRect.bottom = mSpace/2;
                outRect.top = mSpace/2;
            }
        }
    }
}
