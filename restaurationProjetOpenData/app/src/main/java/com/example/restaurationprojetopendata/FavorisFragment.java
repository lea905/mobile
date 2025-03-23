package com.example.restaurationprojetopendata;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavorisFragment extends Fragment {
    private ArrayList<Restaurant> favoriteRestaurants = new ArrayList<>();
    private RestaurationAdapter adapter;
    private ListView listView;
    private ProgressDialog progressDialog;

    public FavorisFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favoris, container, false);

        // Afficher le loader
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Chargement des favoris...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Initialiser la ListView
        listView = rootView.findViewById(R.id.lvFavoris);

        // Récupérer les favoris depuis les arguments
        if (getArguments() != null) {
            favoriteRestaurants = (ArrayList<Restaurant>) getArguments().getSerializable("favorites");

            if (favoriteRestaurants != null) {
                Log.e("FavorisFragment", "Favoris reçus: " + favoriteRestaurants.size() + " éléments.");
            } else {
                Log.e("FavorisFragment", "Aucun favori dans les arguments.");
            }
        }

        // Configurer l'adaptateur si la liste n'est pas vide
        if (favoriteRestaurants != null && !favoriteRestaurants.isEmpty()) {
             adapter = new RestaurationAdapter(getContext(), favoriteRestaurants);

            listView.setAdapter(adapter);
        }

        // Cacher le loader une fois les données chargées
        progressDialog.dismiss();


        // Ajouter un appui long pour supprimer un restaurant aux favoris
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Restaurant restaurant = favoriteRestaurants.get(position);

            // Supprimer le restaurant des favoris
            favoriteRestaurants.remove(restaurant);
            adapter.notifyDataSetChanged();
            return true;
        });


        return rootView;
    }


    public void updateFavorites(ArrayList<Restaurant> updatedFavorites) {
        if (updatedFavorites != null) {
            favoriteRestaurants = updatedFavorites;
            Log.e("FavorisFragment", "Favoris mis à jour : " + favoriteRestaurants.size() + " éléments.");
            adapter.notifyDataSetChanged();
        }
    }



}
