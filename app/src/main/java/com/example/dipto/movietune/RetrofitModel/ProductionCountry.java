package com.example.dipto.movietune.RetrofitModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dipto on 8/23/2017.
 */

public class ProductionCountry {

    @SerializedName("name")
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
