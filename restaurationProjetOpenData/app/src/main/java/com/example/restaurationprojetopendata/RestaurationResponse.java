package com.example.restaurationprojetopendata;

import java.io.Serializable;
import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RestaurationResponse implements Serializable
{

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("results")
    @Expose
    private List<Restaurant> restaurants;
    private final static long serialVersionUID = 2944024579792200815L;


    public List<Restaurant> getRestaurants() {
        return restaurants;
    }



}


