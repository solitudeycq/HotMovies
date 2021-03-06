package com.solitudeycq.hotmovies.utils;

import com.solitudeycq.hotmovies.BuildConfig;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by solitudeycq on 2016/12/12.
 */

public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";
    public static String getMovies(String searchBy,int page){
        String url = null;
        String movieJsonStr = null;
        if(searchBy.equals("popular")){
            url = Constants.GET_MOVIES_BY_POPULAR + BuildConfig.THEME_MOVIE_DB_API_KEY + "&page=" + page;
        }else{
            url = Constants.GET_MOVIES_BY_TOPRATED + BuildConfig.THEME_MOVIE_DB_API_KEY + "&page=" + page;
        }
        LogControl.d(TAG, url);
        LogControl.d(TAG,page);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            movieJsonStr = response.body().string();
            LogControl.d(TAG,"movieJsonStr: "+movieJsonStr);
        } catch (IOException e) {
            LogControl.e(TAG,"请求出错了");
            LogControl.e(TAG,"movieJsonStr: "+movieJsonStr);
        }
        return movieJsonStr;
    }
}
