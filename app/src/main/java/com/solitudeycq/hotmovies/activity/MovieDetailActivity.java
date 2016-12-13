package com.solitudeycq.hotmovies.activity;

import android.os.Bundle;
import android.app.Activity;

import com.solitudeycq.hotmovies.R;

public class MovieDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
