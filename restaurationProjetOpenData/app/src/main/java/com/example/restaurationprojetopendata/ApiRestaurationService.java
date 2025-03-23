package com.example.restaurationprojetopendata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRestaurationService {

    // Premier appel API : GET avec un paramètre "start" pour la pagination
    @GET("explore/v2.1/catalog/datasets/osm-france-food-service/records?limit=20&timezone=Europe%2FParis")
    Call<RestaurationResponse> getRestaurationResponse(@Query("start") int start);  // Utilisation de "start" pour l'offset

}
