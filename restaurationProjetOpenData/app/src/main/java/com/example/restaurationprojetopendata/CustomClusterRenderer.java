package com.example.restaurationprojetopendata;
import android.content.Context;
import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
public class CustomClusterRenderer extends DefaultClusterRenderer<RestaurantClusterItem> {

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<RestaurantClusterItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(RestaurantClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(getMarkerColor(item));
        markerOptions.snippet(item.getSnippet());
    }

    private BitmapDescriptor getMarkerColor(RestaurantClusterItem item) {
        if (item.getType().equals("restaurant")) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        }
        if (item.getType().equals("fast_food")) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
        if (item.getType().equals("cafe")) {
            return getCustomMarkerColor("#462E01");
        }
        if (item.getType().equals("pub")) {
            return getCustomMarkerColor("#DA781B");
        }if (item.getType().equals("bar")) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }if (item.getType().equals("ice_cream")) {
            return getCustomMarkerColor("#FFF44F");
        }
        return getCustomMarkerColor("#FFFFFF");
    }

    private BitmapDescriptor getCustomMarkerColor(String colorStr) {
        float[] hsv = new float[3];
        int color = Color.parseColor(colorStr);
        Color.colorToHSV(color, hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

}
