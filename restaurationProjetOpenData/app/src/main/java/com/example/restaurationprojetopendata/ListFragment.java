package com.example.restaurationprojetopendata;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    private ArrayList<Restaurant> list;
    private RestaurationAdapter adapter;
    private ListView listView;
    private ProgressDialog progressDialog;
    private boolean isLoading = false; // Évite les appels multiples simultanés
    ArrayList<Restaurant> favoriteRestaurants = new ArrayList<>();


    public ListFragment() {
        // Constructeur vide requis
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        // Afficher le loader
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Chargement des données...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Initialiser la ListView
        listView = rootView.findViewById(R.id.lvRestaurant);
        list = new ArrayList<>(); // Initialisation de la liste pour éviter les erreurs

        //WIfi/4G
        ConnectivityManager connectivityManager =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder().build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }


        // Appeler l'API pour récupérer les restaurants
        ApiRestaurationService service = initRetrofit();
        Call<RestaurationResponse> callRestauration = service.getRestaurationResponse(0);

        callRestauration.enqueue(new Callback<RestaurationResponse>() {
            @Override
            public void onResponse(Call<RestaurationResponse> call, Response<RestaurationResponse> response) {
                progressDialog.dismiss(); // Cacher le loader

                if (response.isSuccessful() && response.body() != null) {
                    RestaurationResponse restauration = response.body();
                    List<Restaurant> restaurants = restauration.getRestaurants();

                    if (restaurants != null && !restaurants.isEmpty()) {
                        list.addAll(restaurants);
                        adapter = new RestaurationAdapter(getContext(), list);
                        listView.setAdapter(adapter);

                        // Mettre à jour la liste des restaurants dans MainActivity
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).setRestaurants(list);
                        }
                    } else {
                        Toast.makeText(getContext(), "Aucun restaurant trouvé", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Erreur : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurationResponse> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Échec de connexion", Toast.LENGTH_SHORT).show();
            }
        });

        // GESTION DU CLIC SUR LA LISTE DES RESTAURANTS
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(getContext(), infoRestaurationActivity.class);
            Restaurant restaurant = list.get(position);

            // Passer les données du restaurant à l'Activity
            intent.putExtra("nom", restaurant.getName() != null ? restaurant.getName() : "Nom inconnu");
            intent.putExtra("type", restaurant.getType() != null ? restaurant.getType() : "Type inconnu");
            intent.putExtra("vegetarien", restaurant.getVegetarian() != null ? restaurant.getVegetarian().toString() : "Non végétarien");
            intent.putExtra("vegan", restaurant.getVegan() != null ? restaurant.getVegan().toString() : "Non vegan");
            intent.putExtra("horaire", restaurant.getOpeningHours() != null ? restaurant.getOpeningHours().toString() : "Horaire inconnu");
            intent.putExtra("accesHandicape", restaurant.getWheelchair() != null ? restaurant.getWheelchair().toString() : "Non accessible aux handicapés");
            intent.putExtra("livraison", restaurant.getDelivery() != null ? restaurant.getDelivery().toString() : "Non livraison");
            intent.putExtra("aEmporter", restaurant.getTakeaway() != null ? restaurant.getTakeaway().toString() : "Non à emporter");
            intent.putExtra("siteWeb", restaurant.getWebsite() != null ? restaurant.getWebsite() : "Site web inconnu");
            intent.putExtra("commune", restaurant.getMetaCodeCom() != null ? restaurant.getMetaCodeCom() : "Commune inconnu");
            intent.putExtra("departement", restaurant.getMetaNameDep() != null ? restaurant.getMetaNameDep() : "Département inconnu");
            intent.putExtra("telephone", restaurant.getPhone() != null ? restaurant.getPhone() : "Téléphone inconnu");

            startActivity(intent);
        });

        // Ajouter un appui long pour ajouter un restaurant aux favoris
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Restaurant restaurant = list.get(position);

            // Ajout du restaurant aux favoris dans MainActivity
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.addToFavorites(restaurant); // Ajouter à la liste des favoris de MainActivity
            }

            // Mettre à jour l'étoile du restaurant dans la vue
            RestaurationAdapter adapter = (RestaurationAdapter) listView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged(); // Notifier l'adapter pour que la vue soit mise à jour
            }
            return true;
        });

        // Ajouter un OnScrollListener pour détecter le défilement
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!isLoading) {
                    int totalItemCount = listView.getAdapter().getCount();
                    int lastVisibleItem = listView.getLastVisiblePosition();

                    if (lastVisibleItem == totalItemCount - 1) {
                        isLoading = true;
                        loadMoreData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Optionnel pour d'autres optimisations
            }
        });

        return rootView;
    }

    // Méthode pour initialiser Retrofit
    private ApiRestaurationService initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://public.opendatasoft.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiRestaurationService.class);
    }

    // Méthode pour charger plus de données
    private void loadMoreData() {
        final int pageNumber = list.size() / 20 + 1;

        ApiRestaurationService service = initRetrofit();
        Call<RestaurationResponse> callRestauration = service.getRestaurationResponse(pageNumber);

        callRestauration.enqueue(new Callback<RestaurationResponse>() {
            @Override
            public void onResponse(Call<RestaurationResponse> call, Response<RestaurationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RestaurationResponse restauration = response.body();
                    List<Restaurant> restaurants = restauration.getRestaurants();

                    if (restaurants != null && !restaurants.isEmpty()) {
                        list.addAll(restaurants);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Plus de restaurants disponibles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Erreur : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<RestaurationResponse> call, Throwable throwable) {
                isLoading = false;
                Toast.makeText(getContext(), "Échec de la récupération des données", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //WIfi/4G
    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            // Le réseau est disponible (Wi-Fi ou 4G)
            Log.d("NetworkCallback", "Connexion disponible !");
            Toast.makeText(getContext(), "Connexion disponible", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            // La connexion est perdue
            Log.d("NetworkCallback", "Connexion perdue !");
            Toast.makeText(getContext(), "Connexion perdue", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
            Log.d("NetworkCallback", "Connexion Wi-Fi ? " + unmetered);
            Toast.makeText(getContext(), "Connexion Wi-Fi ? " + unmetered, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }


}
