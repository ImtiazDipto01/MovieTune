package com.example.dipto.movietune;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dipto on 8/23/2017.
 */

public class Constant {

    Context context ;
    public Constant(Context context){
        this.context = context ;
    }

    public static String API_KEY = "c37d3b40004717511adb2c1fbb15eda4" ;
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500/" ;

    public int isNetworkActive(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo() ;
        if(networkInfo != null){
            return 1 ;
        }
        else{
            return 0 ;
        }

    }
}
