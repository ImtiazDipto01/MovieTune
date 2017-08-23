package com.example.dipto.movietune.RetrofitNetworking;

import com.example.dipto.movietune.RetrofitModel.RootModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Dipto on 8/23/2017.
 */

public interface RetrofitInterface {

    @GET
    Call<RootModel> getMovieDetails(@Url String url) ;
}
