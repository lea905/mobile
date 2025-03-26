package com.example.restaurationprojetopendata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaGeoPoint implements Serializable
{

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;
    private final static long serialVersionUID = 1118415590770273295L;

    public Double getLon() {
        return lon;
    }


    public Double getLat() {
        return lat;
    }

}
