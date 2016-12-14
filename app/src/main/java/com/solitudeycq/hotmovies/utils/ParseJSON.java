package com.solitudeycq.hotmovies.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solitudeycq.hotmovies.bean.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by solitudeycq on 2016/12/11.
 */

public class ParseJSON {
    private static final String TAG = "ParseJSON";

    public static List<Movie> parseMoviesFromJson(String result){
        List<Movie> movies = new ArrayList<>();
        if(result!=null&&result.length()!=0){
            try {
                JSONObject moviesJson = new JSONObject(result);
                JSONArray moviesArray = moviesJson.getJSONArray("results");
                for(int i = 0;i<moviesArray.length();i++){
                    JSONObject j = moviesArray.getJSONObject(i);
                    Movie m = new Movie();
                    m.setId(j.getString("id"));
                    m.setPicture(Constants.GET_PICTURE_BASIC_URL+j.getString("poster_path"));
                    m.setIntroduction(j.getString("overview"));
                    m.setName(j.getString("title"));
                    m.setRating(j.getString("vote_average"));
                    m.setReleaseDate(j.getString("release_date"));
                    movies.add(m);
                }
                LogControl.d(TAG,"从网络解析电影成功");
            } catch (JSONException e) {
                LogControl.d(TAG,"从网络解析电影失败");
                e.printStackTrace();
            }
        }
        return movies;
    }
    public static List<Movie> parseMoviesFromJsonFile(String result){
        List<Movie> movies = new ArrayList<>();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Movie>>(){}.getType();
        movies = gson.fromJson(result,collectionType);
        return movies;
    }
}
