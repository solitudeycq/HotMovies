package com.solitudeycq.hotmovies.utils;

import android.os.AsyncTask;

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
    public FetchMovieTask(List<Movie> images,PictureAdapter mAdapter){
        this.images = images;
        this.mAdapter = mAdapter;
    }
    @Override
    protected List<Movie> doInBackground(String... strings) {
        String searchBy = strings[0];
        return ParseJSON.parseMoviesFromJson(OkHttpUtil.getMovies(searchBy));
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies!=null&&movies.size()!=0){
            images.clear();
            for(Movie m:movies){
                images.add(m);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
