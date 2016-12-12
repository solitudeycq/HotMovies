package com.solitudeycq.hotmovies.utils;

import android.util.Log;

import com.solitudeycq.hotmovies.BuildConfig;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by solitudeycq on 2016/12/12.
 */

public class OkHttpUtil {
    public static String getMovies(String searchBy,int page){
        String url = null;
        String movieJsonStr = null;
        if(searchBy.equals("popular")){
            url = Constants.GET_MOVIES_BY_POPULAR + BuildConfig.THEME_MOVIE_DB_API_KEY + "&page=" + page;
        }else{
            url = Constants.GET_MOVIES_BY_TOPRATED + BuildConfig.THEME_MOVIE_DB_API_KEY + "&page=" + page;
        }
        Log.d(TAG, url);
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
        return movieJsonStr;
    }
}