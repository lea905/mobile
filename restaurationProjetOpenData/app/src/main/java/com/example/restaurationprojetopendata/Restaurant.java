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

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCuisine() {
        return cuisine;
    }

    public void setCuisine(Object cuisine) {
        this.cuisine = cuisine;
    }

    public Object getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Object vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Object getVegan() {
        return vegan;
    }

    public void setVegan(Object vegan) {
        this.vegan = vegan;
    }

    public Object getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Object openingHours) {
        this.openingHours = openingHours;
    }

    public Object getWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(Object wheelchair) {
        this.wheelchair = wheelchair;
    }

    public Object getDelivery() {
        return delivery;
    }

    public void setDelivery(Object delivery) {
        this.delivery = delivery;
    }

    public Object getTakeaway() {
        return takeaway;
    }

    public void setTakeaway(Object takeaway) {
        this.takeaway = takeaway;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getMetaNameCom() {
        return metaNameCom;
    }

    public void setMetaNameCom(String metaNameCom) {
        this.metaNameCom = metaNameCom;
    }

    public String getMetaCodeCom() {
        return metaCodeCom;
    }

    public void setMetaCodeCom(String metaCodeCom) {
        this.metaCodeCom = metaCodeCom;
    }

    public String getMetaNameDep() {
        return metaNameDep;
    }

    public void setMetaNameDep(String metaNameDep) {
        this.metaNameDep = metaNameDep;
    }

    public String getMetaCodeDep() {
        return metaCodeDep;
    }

    public void setMetaCodeDep(String metaCodeDep) {
        this.metaCodeDep = metaCodeDep;
    }

    public String getMetaNameReg() {
        return metaNameReg;
    }

    public void setMetaNameReg(String metaNameReg) {
        this.metaNameReg = metaNameReg;
    }

    public String getMetaCodeReg() {
        return metaCodeReg;
    }

    public void setMetaCodeReg(String metaCodeReg) {
        this.metaCodeReg = metaCodeReg;
    }

    public MetaGeoPoint getMetaGeoPoint() {
        return metaGeoPoint;
    }

    public void setMetaGeoPoint(MetaGeoPoint metaGeoPoint) {
        this.metaGeoPoint = metaGeoPoint;
    }

    public String getMetaOsmId() {
        return metaOsmId;
    }

    public void setMetaOsmId(String metaOsmId) {
        this.metaOsmId = metaOsmId;
    }

    public String getMetaOsmUrl() {
        return metaOsmUrl;
    }

    public void setMetaOsmUrl(String metaOsmUrl) {
        this.metaOsmUrl = metaOsmUrl;
    }


}
