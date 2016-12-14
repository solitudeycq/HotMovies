package com.solitudeycq.hotmovies.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.solitudeycq.hotmovies.bean.Movie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by solitudeycq on 2016/12/14.
 */

public class WriteAndReadJsonFromSD {
    private static final String TAG = "WriteAndReadJsonFromSD";
    public static void WriteMoviesToFile(Context context,List<Movie> images){
        Gson gson = new Gson();
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        if(images!=null&&images.size()!=0){
            String json = gson.toJson(images);
            File file = new File(context.getExternalFilesDir(null),"images.json");
            try {
                writer = new FileWriter(file);
                bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(json);
                LogControl.d(TAG,"存储完成");
            } catch (IOException e) {
                LogControl.d(TAG,"存储出错");
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
    public static List<Movie> ReadMoviesFromFile(Context context,List<Movie> images){
        String result = null;
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        File file = new File(context.getExternalFilesDir(null),"images.json");
        if(file.exists()){
            try {
                reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                result = sb.toString();
                LogControl.d(TAG,"读取完成");
                if(result!=null&&result.length()!=0){
                    images = ParseJSON.parseMoviesFromJsonFile(result);
                }
            } catch (IOException e) {
                LogControl.d(TAG,"读取失败");
                e.printStackTrace();
            }finally{
                if(bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return images;
    }
}
