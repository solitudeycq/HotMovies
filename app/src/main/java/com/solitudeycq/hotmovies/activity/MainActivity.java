package com.solitudeycq.hotmovies.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.solitudeycq.hotmovies.R;
import com.solitudeycq.hotmovies.fragment.RecyclerViewMoviesFragment;

public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RecyclerViewMoviesFragment())
                    .commit();
        }
    }
}
