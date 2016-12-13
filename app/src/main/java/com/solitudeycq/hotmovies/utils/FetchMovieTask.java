package com.solitudeycq.hotmovies.utils;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.solitudeycq.hotmovies.bean.Movie;
import com.solitudeycq.hotmovies.recylerview.PictureAdapter;

import java.util.List;

/**
 * Created by solitudeycq on 2016/12/12.
 */

public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String TAG = "FetchMovieTask";
    private List<Movie> images;
    private PictureAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private int page = 1;
    private boolean isPrefsChanged = false;

    public FetchMovieTask(List<Movie> images,PictureAdapter mAdapter){
        this.images = images;
        this.mAdapter = mAdapter;
    }

    public FetchMovieTask(List<Movie> images,PictureAdapter mAdapter,boolean isPrefsChanged){
        this.images = images;
        this.mAdapter = mAdapter;
        this.isPrefsChanged = isPrefsChanged;
    }
    public FetchMovieTask(List<Movie> images,PictureAdapter mAdapter,SwipeRefreshLayout mSwipe){
        this.images = images;
        this.mAdapter = mAdapter;
        this.mSwipe = mSwipe;
    }
    public FetchMovieTask(List<Movie> images, PictureAdapter mAdapter, int page){
        this.images = images;
        this.mAdapter = mAdapter;
        this.page = page;
    }
    @Override
    protected List<Movie> doInBackground(String... strings) {
        String searchBy = strings[0];
        return ParseJSON.parseMoviesFromJson(OkHttpUtil.getMovies(searchBy,page));
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies!=null&&movies.size()!=0){
            if(isPrefsChanged){
                images.clear();
                mAdapter.notifyDataSetChanged();
            }
            for(Movie m:movies){
                if(!images.contains(m)){
                    images.add(m);
                    mAdapter.notifyItemChanged(images.size()-1);
                }
            }
            if(mSwipe!=null){
                mSwipe.setRefreshing(false);
            }
        }
    }
}
