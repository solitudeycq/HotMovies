package com.solitudeycq.hotmovies.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.solitudeycq.hotmovies.BuildConfig;
import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.bean.Movie;
import com.solitudeycq.hotmovies.recylerview.OnItemClickLitener;
import com.solitudeycq.hotmovies.recylerview.PictureAdapter;
import com.solitudeycq.hotmovies.recylerview.SpaceItemDecoration;
import com.solitudeycq.hotmovies.utils.Constants;
import com.solitudeycq.hotmovies.utils.LogControl;
import com.solitudeycq.hotmovies.utils.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class RecyclerViewMoviesFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";

    private RecyclerView mRecyclerView;
    private List<Movie> images = new ArrayList<>();
    private PictureAdapter mPictureAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchMovieTask movies = new FetchMovieTask();
        movies.execute("popular");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_movies);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mPictureAdapter = new PictureAdapter(images);
        mPictureAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), (position + 1) + "", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mPictureAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recyclerviewfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            LogControl.d(TAG, "刷新");
            FetchMovieTask movies = new FetchMovieTask();
            movies.execute("popular");
            return true;
        }
        if (id == R.id.action_settings) {
            LogControl.d(TAG, "设置");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
        private static final String TAG = "FetchMovieTask";

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String movieJsonStr = null;
            String url = null;
            String searchBy = strings[0];
            if(searchBy.equals("popular")){
                url = Constants.GET_MOVIES_BY_POPULAR + BuildConfig.THEME_MOVIE_DB_API_KEY;
            }else{
                url = Constants.GET_MOVIES_BY_TOPRATED + BuildConfig.THEME_MOVIE_DB_API_KEY;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                movieJsonStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ParseJSON.parseMoviesFromJson(movieJsonStr);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies!=null&&movies.size()!=0){
                images.clear();
                for(Movie m:movies){
                    images.add(m);
                }
                mPictureAdapter.notifyDataSetChanged();
            }
        }
    }
}
