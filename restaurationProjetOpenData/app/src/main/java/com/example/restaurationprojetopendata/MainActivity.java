package com.example.restaurationprojetopendata;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements PreferencesFragment.OnFilterChangedListener {
    private List<Restaurant> list = new ArrayList<>(); // Liste des restaurants
    private ArrayList<Restaurant> favorites = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(" ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du DrawerLayout et de la Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ajout du bouton pour ouvrir le menu latéral
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestion des clics dans le Navigation Drawer
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_preferences) {
                    // Créer un nouvel objet PreferencesFragment
                    PreferencesFragment preferencesFragment = new PreferencesFragment();

                    // Passer la liste des restaurants via un Bundle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("allRestaurants", (Serializable) list);  // list étant ta liste de restaurants
                    preferencesFragment.setArguments(bundle);

                    // Remplacer le fragment dans le conteneur
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, preferencesFragment)
                            .commit();

                } else if (item.getItemId() == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "Crée par Léa Barnezet", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawers();
                return true;
            }

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_lst) {
                    selectedFragment = new ListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("restaurants", (Serializable) list);
                    selectedFragment.setArguments(bundle);
                } else if (item.getItemId() == R.id.nav_map) {
                    if (list != null && !list.isEmpty()) {
                        MapsFragment mapsFragment = new MapsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) list);
                        mapsFragment.setArguments(bundle);
                        selectedFragment = mapsFragment;
                    } else {
                        Log.e("pb", "Aucun restaurant à afficher sur la carte");
                        return false; // Empêche la navigation si pas de restaurants
                    }
                } else if (item.getItemId() == R.id.nav_fav) {
                    FavorisFragment favorisFragment = new FavorisFragment();
                    if (favorites != null && !favorites.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("favorites", favorites);
                        favorisFragment.setArguments(bundle);
                        selectedFragment = favorisFragment;
                    } else {
                        Log.e("MainActivity", "Aucun favori à afficher");
                    }
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new ListFragment())
                .commit();
    }

    @Override
    public void onFilterChanged(List<Restaurant> filteredRestaurants) {
        this.list = filteredRestaurants;

        ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment");
        if (listFragment != null) {
            listFragment.updateRestaurants(filteredRestaurants);
        }
    }

    // Autres méthodes de gestion des restaurants (comme addToFavorites, etc.)
    public void setRestaurants(List<Restaurant> restaurants) {
        this.list = restaurants;
    }

    public boolean compare(Restaurant restaurant) {
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getName().equals(restaurant.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addToFavorites(Restaurant restaurant) {
        if (!compare(restaurant)) {
            if (!favorites.contains(restaurant)) {
                favorites.add(restaurant);
                Log.d("Favorites", restaurant.getName() + " ajouté aux favoris.");
            }
        }
    }

    public ArrayList<Restaurant> getFavorites() {
        return favorites;
    }
}
