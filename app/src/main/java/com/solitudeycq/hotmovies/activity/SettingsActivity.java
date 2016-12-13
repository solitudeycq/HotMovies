package com.solitudeycq.hotmovies.activity;

import android.os.Bundle;
import android.app.Activity;

import com.solitudeycq.hotmovies.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
