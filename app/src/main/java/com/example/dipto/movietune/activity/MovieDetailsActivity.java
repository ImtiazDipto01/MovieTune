package com.example.dipto.movietune.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.dipto.movietune.R;

/**
 * Created by Dipto on 8/23/2017.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    Toolbar toolbar ;
    TextView movie_name, release_year, rating, popularity, gener, production_name, language ;
    TextView production_country, budget, movie_description ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String movie_id = new Intent().getStringExtra("id") ;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initilization();


    }

    private void initilization(){

        movie_name = (TextView) findViewById(R.id.movie_name);
        release_year = (TextView) findViewById(R.id.release_year);
        rating = (TextView) findViewById(R.id.rating);
        popularity = (TextView) findViewById(R.id.popularity);
        gener = (TextView) findViewById(R.id.movie_gener);
        production_name = (TextView) findViewById(R.id.production_name);
        production_country = (TextView) findViewById(R.id.production_country);
        language = (TextView) findViewById(R.id.language);
        budget = (TextView) findViewById(R.id.Budget_amount);
        movie_description = (TextView) findViewById(R.id.movie_description);
    }
}
