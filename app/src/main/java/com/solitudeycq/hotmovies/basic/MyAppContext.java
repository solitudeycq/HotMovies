package com.solitudeycq.hotmovies.basic;

import android.app.Application;

import com.solitudeycq.hotmovies.utils.LogControl;

/**
 * Created by solitudeycq on 2016/12/11.
 */

public class MyAppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogControl.setUSable(true);
    }
}
