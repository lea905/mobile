package com.example.restaurationprojetopendata;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PreferencesFragment extends Fragment {

    private CheckBox filterCafe, filterPub, filterBar, filterRestaurant;
    private RestaurationAdapter restaurantAdapter;


    // Liste des restaurants (à remplacer par une vraie source de données plus tard)
    private List<Restaurant> allRestaurants = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        // Initialiser les CheckBox
        filterCafe = view.findViewById(R.id.filter_cafe);
        filterPub = view.findViewById(R.id.filter_pub);
        filterBar = view.findViewById(R.id.filter_bar);
        filterRestaurant = view.findViewById(R.id.filter_restaurant);

        // Récupérer l'état des CheckBox depuis SharedPreferences
        filterCafe.setChecked(getPreference("filter_cafe"));
        filterPub.setChecked(getPreference("filter_pub"));
        filterBar.setChecked(getPreference("filter_bar"));
        filterRestaurant.setChecked(getPreference("filter_restaurant"));

        // Gérer les événements de changement des CheckBox
        filterCafe.setOnClickListener(v -> savePreference("filter_cafe", filterCafe.isChecked()));
        filterPub.setOnClickListener(v -> savePreference("filter_pub", filterPub.isChecked()));
        filterBar.setOnClickListener(v -> savePreference("filter_bar", filterBar.isChecked()));
        filterRestaurant.setOnClickListener(v -> savePreference("filter_restaurant", filterRestaurant.isChecked()));

        return view;
    }

    // Sauvegarder les préférences dans SharedPreferences
    private void savePreference(String key, boolean value) {
        SharedPreferences preferences = getActivity().getSharedPreferences("restaurant_filters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Récupérer l'état des préférences
    private boolean getPreference(String key) {
        SharedPreferences preferences = getActivity().getSharedPreferences("restaurant_filters", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false); // valeur par défaut "false"
    }

    // Filtrer les restaurants en fonction des CheckBox sélectionnés
    public void filterRestaurants() {
        boolean filterCafe = this.filterCafe.isChecked();
        boolean filterPub = this.filterPub.isChecked();
        boolean filterBar = this.filterBar.isChecked();
        boolean filterRestaurant = this.filterRestaurant.isChecked();

        // Créer une liste pour stocker les restaurants filtrés
        List<Restaurant> filteredRestaurants = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {
            if ((filterCafe && "Café".equals(restaurant.getType())) ||
                    (filterPub && "Pub".equals(restaurant.getType())) ||
                    (filterBar && "Bar".equals(restaurant.getType())) ||
                    (filterRestaurant && "Restaurant".equals(restaurant.getType()))) {
                filteredRestaurants.add(restaurant);
            }
        }

        // Mets à jour la liste affichée (en supposant que tu aies un adapter)
        restaurantAdapter.updateList(filteredRestaurants);
    }
}
