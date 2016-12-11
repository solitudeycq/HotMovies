package com.solitudeycq.hotmovies.fragment;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.solitudeycq.hotmovies.BuildConfig;
import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.bean.Movie;
import com.solitudeycq.hotmovies.recylerview.OnItemClickLitener;
import com.solitudeycq.hotmovies.recylerview.PictureAdapter;
import com.solitudeycq.hotmovies.recylerview.SpaceItemDecoration;
import com.solitudeycq.hotmovies.utils.Constants;
import com.solitudeycq.hotmovies.utils.ParseJSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        movies.execute();
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
            Log.d(TAG, "刷新");
            return true;
        }
        if (id == R.id.action_settings) {
            Log.d(TAG, "设置");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
        private static final String TAG = "FetchMovieTask";

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String movieJsonStr = null;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(Constants.GET_MOVIES_BY_POPULAR + BuildConfig.THEME_MOVIE_DB_API_KEY);
                Log.d(TAG, "URL:" + url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
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
