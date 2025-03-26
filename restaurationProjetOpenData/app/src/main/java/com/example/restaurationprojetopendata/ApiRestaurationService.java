package com.example.restaurationprojetopendata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRestaurationService {

    @GET("explore/v2.1/catalog/datasets/osm-france-food-service/records?limit=20&timezone=Europe%2FParis")
    Call<RestaurationResponse> getRestaurationResponse(@Query("start") int start);

}
