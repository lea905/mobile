package com.example.restaurationprojetopendata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable
{

    public Restaurant(String name, String type, Object cuisine) {
        this.name = name;
        this.type = type;
        this.cuisine = cuisine;

    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("cuisine")
    @Expose
    private Object cuisine;
    @SerializedName("vegetarian")
    @Expose
    private Object vegetarian;
    @SerializedName("vegan")
    @Expose
    private Object vegan;
    @SerializedName("opening_hours")
    @Expose
    private Object openingHours;
    @SerializedName("wheelchair")
    @Expose
    private Object wheelchair;
    @SerializedName("delivery")
    @Expose
    private Object delivery;
    @SerializedName("takeaway")
    @Expose
    private Object takeaway;

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("meta_name_com")
    @Expose
    private String metaNameCom;
    @SerializedName("meta_code_com")
    @Expose
    private String metaCodeCom;
    @SerializedName("meta_name_dep")
    @Expose
    private String metaNameDep;
    @SerializedName("meta_code_dep")
    @Expose
    private String metaCodeDep;
    @SerializedName("meta_name_reg")
    @Expose
    private String metaNameReg;
    @SerializedName("meta_code_reg")
    @Expose
    private String metaCodeReg;
    @SerializedName("meta_geo_point")
    @Expose
    private MetaGeoPoint metaGeoPoint;
    @SerializedName("meta_osm_id")
    @Expose
    private String metaOsmId;
    @SerializedName("meta_osm_url")
    @Expose
    private String metaOsmUrl;
    @SerializedName("meta_first_update")

    private final static long serialVersionUID = 6551618835676258172L;

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public Object getVegetarian() {
        return vegetarian;
    }


    public Object getVegan() {
        return vegan;
    }


    public Object getOpeningHours() {
        return openingHours;
    }


    public Object getWheelchair() {
        return wheelchair;
    }


    public Object getDelivery() {
        return delivery;
    }


    public Object getTakeaway() {
        return takeaway;
    }


    public String getPhone() {
        return phone;
    }


    public String getWebsite() {
        return website;
    }


    public String getMetaCodeCom() {
        return metaCodeCom;
    }


    public String getMetaNameDep() {
        return metaNameDep;
    }


    public MetaGeoPoint getMetaGeoPoint() {
        return metaGeoPoint;
    }

}
