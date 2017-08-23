package com.example.dipto.movietune.RetrofitNetworking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dipto on 8/23/2017.
 */

public class RetrofitClient {

    public static final String BASE_URL = "https://api.themoviedb.org/3/" ;
    public static final String image_show = "http://image.tmdb.org/t/p/w500/" ;

    public static Retrofit retrofit = null ;

    public static Retrofit getRetrofitClient(){

        if(retrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient() ;

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).
                    addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit ;
    }
}
