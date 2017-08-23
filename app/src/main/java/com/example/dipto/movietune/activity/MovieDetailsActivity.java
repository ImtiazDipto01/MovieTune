package com.example.dipto.movietune.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dipto.movietune.Constant;
import com.example.dipto.movietune.R;
import com.example.dipto.movietune.RetrofitModel.Genres;
import com.example.dipto.movietune.RetrofitModel.ProductionCountry;
import com.example.dipto.movietune.RetrofitModel.ProductionName;
import com.example.dipto.movietune.RetrofitModel.RootModel;
import com.example.dipto.movietune.RetrofitNetworking.RetrofitClient;
import com.example.dipto.movietune.RetrofitNetworking.RetrofitInterface;
import com.example.dipto.movietune.model.NewReleaseModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dipto on 8/23/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    Toolbar toolbar ;
    TextView movie_name_textview, release_year, rating, popularity, gener, production_name, language ;
    TextView production_country, budget, movie_description ;
    ProgressDialog progressDialog ;
    ImageView imageView, back ;
    RetrofitInterface retrofitInterface ;
    String api_key = "?api_key=c37d3b40004717511adb2c1fbb15eda4" ;
    List<ProductionCountry> pro_country ;
    List<ProductionName> pro_name ;
    List<Genres> pro_genre ;
    String movie_name, movie_overview, movie_release_year, movie_genre, movie_poster, movie_rating,
    movie_popularity, movie_pro_name, movie_country, movie_release,
    movie_budget, movie_language ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_movie_details);
        Intent intent = this.getIntent() ;
        String movie_id = intent.getStringExtra("id") ;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initilization();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_intent = new Intent(MovieDetailsActivity.this, HomeActivity.class) ;
                startActivity(back_intent);
            }
        });

        Log.d("+++movie_id+++", movie_id) ;

        String url_link = RetrofitClient.BASE_URL+movie_id+api_key ;
        Log.d("++++TAG++++", url_link) ;

        progressDialog = new ProgressDialog(MovieDetailsActivity.this) ;
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        retrofitInterface = RetrofitClient.getRetrofitClient().create(RetrofitInterface.class) ;

        Call<RootModel> call = retrofitInterface.getMovieDetails(url_link) ;

        call.enqueue(new Callback<RootModel>() {
            @Override
            public void onResponse(Call<RootModel> call, Response<RootModel> response) {

                progressDialog.dismiss();
                Log.d("+++CONNECTION++", "asche") ;
                //Log.d("+++CONNECTION_ON++", response.body().getOriginal_title());
                if(response.body().getOriginal_title() != null){
                    movie_name = response.body().getOriginal_title() ;
                }
                else{
                    movie_name = "" ;
                }
                if(response.body().getOverview() != null){
                    movie_overview = response.body().getOverview() ;
                }
                else{
                    movie_overview = "" ;
                }
                if(Double.toString(response.body().getVote_average()) != null){
                    movie_rating = Double.toString(response.body().getVote_average()) ;
                }
                else{
                    movie_rating = "" ;
                }
                if(Double.toString(response.body().getPopularity()) != null){
                    movie_popularity = Double.toString(response.body().getPopularity()) ;
                }
                else{
                    movie_popularity = "" ;
                }
                if(response.body().getBackdrop_path() != null){
                    movie_poster = RetrofitClient.image_show+response.body().getBackdrop_path() ;
                }
                else{
                    movie_poster = "" ;
                }
                Log.d("+++MOVIE_POSTER+++", movie_poster) ;
                if(Long.toString(response.body().getBudget()) != null){
                    movie_budget = Long.toString(response.body().getBudget()) ;
                }
                else{
                    movie_budget = "" ;
                }

                if(response.body().getRelease_date() != null){
                    movie_release = response.body().getRelease_date() ;
                    String[] split_year ;
                    split_year = movie_release.split("-") ;
                    movie_release_year = split_year[0] ;
                }
                else{
                    movie_release_year = "" ;
                }
                ProductionCountry productionCountry = null ;
                ProductionName productionName = null;

                if(response.body().getProduction_countries() != null){
                    pro_country = response.body().getProduction_countries() ;
                    if(pro_country.size() > 0){
                        productionCountry = pro_country.get(0) ;
                        if(productionCountry.getName() != null){
                            movie_country = productionCountry.getName() ;
                        }
                        else{
                            movie_country = "" ;
                        }
                    }
                }
                if(response.body().getProduction_companies() != null){
                    pro_name =response.body().getProduction_companies() ;
                    if(pro_name.size() > 0){
                        productionName = pro_name.get(0) ;
                        if(productionName.getName() != null){
                            movie_pro_name = productionName.getName() ;
                        }
                        else{
                            movie_pro_name = "" ;
                        }
                    }

                }

                if(response.body().getGenres() != null){
                    pro_genre = response.body().getGenres() ;
                    if(pro_genre.size() > 0){
                        for(int i = 0 ; i < pro_genre.size() ; i++){
                            Genres genres = pro_genre.get(i);
                            movie_genre = movie_genre+genres.getName()+", " ;
                            if(i == 2){
                                break ;
                            }
                        }
                    }
                }


                if(response.body().getOriginal_language() != null){
                    Locale loc = new Locale(response.body().getOriginal_language());
                    movie_language = loc.getDisplayLanguage() ;
                }
                else{
                    movie_language = "" ;
                }


                setValue(movie_name, movie_overview, movie_release_year, movie_genre, movie_poster, movie_rating,
                        movie_popularity, movie_pro_name, movie_country,
                        movie_budget, movie_language) ;
            }

            @Override
            public void onFailure(Call<RootModel> call, Throwable t) {

                Log.d("+++CONNECTION++", "ase nai") ;
            }
        });
    }

    private void initilization(){

        movie_name_textview = (TextView) findViewById(R.id.movie_name);
        release_year = (TextView) findViewById(R.id.release_year);
        rating = (TextView) findViewById(R.id.rating);
        popularity = (TextView) findViewById(R.id.popularity);
        gener = (TextView) findViewById(R.id.movie_gener);
        production_name = (TextView) findViewById(R.id.production_name);
        production_country = (TextView) findViewById(R.id.production_country);
        language = (TextView) findViewById(R.id.language);
        budget = (TextView) findViewById(R.id.Budget_amount);
        movie_description = (TextView) findViewById(R.id.movie_description);
        imageView = (ImageView) findViewById(R.id.movie_details_poster);
        back = (ImageView) findViewById(R.id.back) ;
    }

    private void fullscreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private void setValue(String movie_name, String movie_overview, String movie_release_year,
                          String movie_genre, String movie_poster, String movie_rating,
                          String movie_popularity, String movie_pro_name, String movie_country,
                          String movie_budget, String movie_language){
        movie_name_textview.setText(movie_name);
        movie_description.setText(movie_overview);
        budget.setText("$ "+movie_budget);
        language.setText(movie_language);
        gener.setText(movie_genre);
        release_year.setText("("+movie_release_year+")");
        production_name.setText(movie_pro_name);
        production_country.setText(movie_country);
        popularity.setText(movie_popularity.substring(0,2)+"%");
        rating.setText(movie_rating);
        Glide.with(MovieDetailsActivity.this).load(movie_poster).placeholder(R.drawable.movie_details_background).into(imageView);


    }
}
