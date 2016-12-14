package com.solitudeycq.hotmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.activity.MovieDetailActivity;
import com.solitudeycq.hotmovies.activity.SettingsActivity;
import com.solitudeycq.hotmovies.bean.Movie;
import com.solitudeycq.hotmovies.recylerview.OnItemClickLitener;
import com.solitudeycq.hotmovies.recylerview.PictureAdapter;
import com.solitudeycq.hotmovies.recylerview.SpaceItemDecoration;
import com.solitudeycq.hotmovies.utils.FetchMovieTask;
import com.solitudeycq.hotmovies.utils.LogControl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by solitudeycq on 2016/12/9.
 */

public class RecyclerViewMoviesFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    private static int PAGE = 1;

    private RecyclerView mRecyclerView;
    private List<Movie> images = new ArrayList<>();
    private PictureAdapter mPictureAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private String searchBy = "popular";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        searchBy = prefs.getString(getString(R.string.pref_searchBy_key),"popular");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipetorefresh);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_movies);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        mRecyclerView.setItemAnimator(new ScaleInAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(500);
        mPictureAdapter = new PictureAdapter(images);
        mPictureAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(), (position + 1) + "", Toast.LENGTH_SHORT).show();
                Movie m = images.get(position);
                Intent i = new Intent(getActivity(), MovieDetailActivity.class);
                i.putExtra("movie",m);
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(mPictureAdapter);
        initLoadMore();
        getMovies();
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchMovieTask movies = new FetchMovieTask(images,mPictureAdapter,mSwipeRefresh);
                movies.execute(searchBy);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!(searchBy.equals(prefs.getString(getString(R.string.pref_searchBy_key),"popular")))){
            searchBy = prefs.getString(getString(R.string.pref_searchBy_key),"popular");
            FetchMovieTask movies = new FetchMovieTask(images,mPictureAdapter,true);
            PAGE = 1;
            LogControl.d(TAG, searchBy);
            movies.execute(searchBy);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Gson gson = new Gson();
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        if(images!=null&&images.size()!=0){
            String json = gson.toJson(images);
            File file = new File(getActivity().getExternalFilesDir(null),"images.json");
            try {
                writer = new FileWriter(file);
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(bufferedWriter!=null){
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recyclerviewfragment, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchMovieTask task = new FetchMovieTask(images,mPictureAdapter);
            task.execute(searchBy);
            return true;
        }
        if (id == R.id.action_settings) {
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initLoadMore(){
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&(lastVisibleItem+1)==PAGE*20){
                    FetchMovieTask task = new FetchMovieTask(images,mPictureAdapter,++PAGE);
                    task.execute(searchBy);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layout = (GridLayoutManager) mRecyclerView.getLayoutManager();
                lastVisibleItem = layout.findLastCompletelyVisibleItemPosition();
            }
        });
    }
    private void getMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        searchBy = prefs.getString(getString(R.string.pref_searchBy_key),"popular");
        FetchMovieTask movies = new FetchMovieTask(images,mPictureAdapter,true);
        LogControl.d(TAG, searchBy);
        movies.execute(searchBy);
    }
}
