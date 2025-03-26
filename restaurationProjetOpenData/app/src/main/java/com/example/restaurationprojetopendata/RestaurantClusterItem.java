package com.example.restaurationprojetopendata;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class RestaurantClusterItem implements ClusterItem {
    private final LatLng position;
    private final String title;
    private final String type;

    public RestaurantClusterItem(LatLng position, String title, String type) {
        this.position = position;
        this.title = title;
        this.type = type;

    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getSnippet() {
        String res= type;
        return res;
    }
}
