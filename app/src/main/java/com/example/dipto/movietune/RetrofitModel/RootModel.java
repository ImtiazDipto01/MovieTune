package com.example.dipto.movietune.RetrofitModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dipto on 8/23/2017.
 */

public class RootModel {

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("budget")
    private String budget;

    @SerializedName("vote_average")
    private String vote_average;

    @SerializedName("genres")
    private List<Genres> genres ;

    @SerializedName("production_companies")
    private List<ProductionName> production_companies ;

    @SerializedName("production_countries")
    private List<ProductionCountry> production_countries ;

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<ProductionName> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionName> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountry> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<ProductionCountry> production_countries) {
        this.production_countries = production_countries;
    }
}
