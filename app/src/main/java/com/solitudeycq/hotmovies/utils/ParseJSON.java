package com.solitudeycq.hotmovies.utils;

import com.solitudeycq.hotmovies.bean.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solitudeycq on 2016/12/11.
 */

public class ParseJSON {

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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
