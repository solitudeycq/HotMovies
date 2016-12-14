package com.solitudeycq.hotmovies.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.solitudeycq.hotmovies.bean.Movie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by solitudeycq on 2016/12/14.
 */

public class WriteAndReadJsonFromSD {
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
            } catch (IOException e) {
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
}
