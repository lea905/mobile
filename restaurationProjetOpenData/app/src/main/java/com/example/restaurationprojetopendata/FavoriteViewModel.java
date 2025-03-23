package com.example.restaurationprojetopendata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Restaurant>> favorites;

    public FavoriteViewModel() {
        favorites = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Restaurant>> getFavorites() {
        return favorites;
    }

    public void addToFavorites(Restaurant restaurant) {
        ArrayList<Restaurant> currentFavorites = favorites.getValue();
        if (currentFavorites != null && !currentFavorites.contains(restaurant)) {
            currentFavorites.add(restaurant);
            favorites.setValue(currentFavorites); // Met à jour la liste
        }
    }
}
