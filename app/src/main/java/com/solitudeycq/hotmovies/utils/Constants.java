package com.solitudeycq.hotmovies.utils;

import com.solitudeycq.hotmovies.BuildConfig;

/**
 * Created by solitudeycq on 2016/12/10.
 */

public class Constants {
    public static String API_KEY = BuildConfig.THEME_MOVIE_DB_API_KEY;
    public static String GET_PICTURE_BASIC_URL = "http://image.tmdb.org/t/p/w185";
    public static String GET_MOVIES_BY_POPULAR = "http://api.themoviedb.org/3/movie/popular?language=zh&api_key=";
    public static String GET_MOVIES_BY_TOPRATED = "http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key=";
}
