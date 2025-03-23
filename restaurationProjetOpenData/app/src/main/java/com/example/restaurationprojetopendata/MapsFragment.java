package com.example.restaurationprojetopendata;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.color.DynamicColors;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.io.Serializable;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Restaurant> restaurantList; // Liste des restaurants

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            restaurantList = (List<Restaurant>) getArguments().getSerializable("list");
        }
        // Vérifie si la liste est bien reçue
        if (restaurantList == null) {
            Log.e("MapsFragment", "La liste des restaurants est NULL !");
        } else {
            Log.d("MapsFragment", "Nombre de restaurants reçus : " + restaurantList.size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Définir la position centrale de la France (approximativement Paris)
        LatLng franceCenter = new LatLng(48.858370, 2.294481);
        float zoomLevel = 5.5f; // Niveau de zoom pour voir toute la France

        // Déplacer la caméra sur la France au démarrage
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(franceCenter, zoomLevel));

        // Appeler la méthode de configuration des clusters
        setupClusterManager(mMap);

        // Si la liste de restaurants est disponible, recentrer sur le premier restaurant
        if (restaurantList != null && !restaurantList.isEmpty()) {
            LatLng firstRestaurant = new LatLng(
                    restaurantList.get(0).getMetaGeoPoint().getLat(),
                    restaurantList.get(0).getMetaGeoPoint().getLon()
            );

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstRestaurant, 12));
        }
    }


    private void setupClusterManager(GoogleMap googleMap) {
        ClusterManager<RestaurantClusterItem> clusterManager = new ClusterManager<>(getContext(), googleMap);

        // Appliquer notre rendu personnalisé
        CustomClusterRenderer renderer = new CustomClusterRenderer(getContext(), googleMap, clusterManager);
        clusterManager.setRenderer(renderer);

        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);

        if (restaurantList != null) {
            for (Restaurant restaurant : restaurantList) {
                LatLng position = new LatLng(restaurant.getMetaGeoPoint().getLat(), restaurant.getMetaGeoPoint().getLon());
                RestaurantClusterItem item = new RestaurantClusterItem(position, restaurant.getName(),restaurant.getType());

                clusterManager.addItem(item);
            }
        }

        clusterManager.cluster();
    }


}
