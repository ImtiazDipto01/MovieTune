package com.example.dipto.movietune.model;

/**
 * Created by Dipto on 8/22/2017.
 */

public class NewReleaseModel {

    String movie_id, movie_poster ;
    int movie_local ;

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public int getMovie_local() {
        return movie_local;
    }

    public void setMovie_local(int movie_local) {
        this.movie_local = movie_local;
    }
}
