package com.example.restaurationprojetopendata;

import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PreferencesFragment extends Fragment {

    private CheckBox filterCafe, filterPub, filterBar, filterRestaurant, filterFastFood, filterIceCream, filterAll;

    private List<Restaurant> allRestaurants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allRestaurants = (List<Restaurant>) getArguments().getSerializable("allRestaurants");
            Log.d("PreferencesFragment", "Restaurants: " + allRestaurants.size());

        }
    }

    public interface OnFilterChangedListener {
        void onFilterChanged(List<Restaurant> filteredRestaurants);
    }

    private OnFilterChangedListener listener; // Variable pour l'interface

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterChangedListener) {
            listener = (OnFilterChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFilterChangedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        filterCafe = view.findViewById(R.id.filter_cafe);
        filterPub = view.findViewById(R.id.filter_pub);
        filterBar = view.findViewById(R.id.filter_bar);
        filterRestaurant = view.findViewById(R.id.filter_restaurant);
        filterFastFood = view.findViewById(R.id.filter_fastFood);
        filterIceCream = view.findViewById(R.id.filter_iceCream);
        filterAll = view.findViewById(R.id.filter_all);

        // Récupérer l'état des CheckBox depuis SharedPreferences
        filterCafe.setChecked(getPreference("filter_cafe"));
        filterPub.setChecked(getPreference("filter_pub"));
        filterBar.setChecked(getPreference("filter_bar"));
        filterRestaurant.setChecked(getPreference("filter_restaurant"));
        filterFastFood.setChecked(getPreference("filter_fastFood"));
        filterIceCream.setChecked(getPreference("filter_iceCream"));
        filterAll.setChecked(getPreference("filter_all"));

        // Gérer les événements de changement des CheckBox
        filterCafe.setOnClickListener(v -> savePreference("filter_cafe", filterCafe.isChecked()));
        filterPub.setOnClickListener(v -> savePreference("filter_pub", filterPub.isChecked()));
        filterBar.setOnClickListener(v -> savePreference("filter_bar", filterBar.isChecked()));
        filterRestaurant.setOnClickListener(v -> savePreference("filter_restaurant", filterRestaurant.isChecked()));
        filterFastFood.setOnClickListener(v -> savePreference("filter_fastFood", filterFastFood.isChecked()));
        filterIceCream.setOnClickListener(v -> savePreference("filter_iceCream", filterIceCream.isChecked()));
        filterAll.setOnClickListener(v -> {
            savePreference("filter_all", filterAll.isChecked());
            filterCafe.setChecked(filterAll.isChecked());
            filterPub.setChecked(filterAll.isChecked());
            filterBar.setChecked(filterAll.isChecked());
            filterRestaurant.setChecked(filterAll.isChecked());
            filterFastFood.setChecked(filterAll.isChecked());
            filterIceCream.setChecked(filterAll.isChecked());
        });

        // Appeler la méthode de filtrage dès que les préférences sont mises à jour
        filterRestaurants();

        return view;
    }

    private void savePreference(String key, boolean value) {
        SharedPreferences preferences = getActivity().getSharedPreferences("restaurant_filters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

        filterRestaurants();
    }

    private boolean getPreference(String key) {
        SharedPreferences preferences = getActivity().getSharedPreferences("restaurant_filters", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false); // valeur par défaut "false"
    }

    private void filterRestaurants() {
        boolean filterCafe = this.filterCafe.isChecked();
        boolean filterPub = this.filterPub.isChecked();
        boolean filterBar = this.filterBar.isChecked();
        boolean filterFastFood = this.filterFastFood.isChecked();
        boolean filterIceCream = this.filterIceCream.isChecked();
        boolean filterRestaurant = this.filterRestaurant.isChecked();
        boolean filterAll = this.filterAll.isChecked();

        List<Restaurant> filteredRestaurants = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {

            Log.d("PreferencesFragment", "Vérification du restaurant: " + restaurant.getName() + " - Type: " + restaurant.getType());

            if ((filterCafe && "cafe".equals(restaurant.getType())) ||
                    (filterPub && "pub".equals(restaurant.getType())) ||
                    (filterBar && "bar".equals(restaurant.getType())) ||
                    (filterFastFood && "fast_food".equals(restaurant.getType())) ||
                    (filterIceCream && "ice_cream".equals(restaurant.getType())) ||
                    (filterAll) ||
                    (filterRestaurant && "restaurant".equals(restaurant.getType()))) {

                filteredRestaurants.add(restaurant);
            }
        }
        Log.d("PreferencesFragment", "Restaurants filtrés: " + filteredRestaurants.size());

        if (listener != null) {
            listener.onFilterChanged(filteredRestaurants);
        }
    }

    private void saveFilterPreference(String key, boolean value) {
        SharedPreferences preferences = getActivity().getSharedPreferences("restaurant_filters", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

}
