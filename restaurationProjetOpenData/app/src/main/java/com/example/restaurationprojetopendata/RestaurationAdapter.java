package com.example.restaurationprojetopendata;

import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurationAdapter extends BaseAdapter {
    private final ArrayList<Restaurant> list;
    private final Context context;

    public RestaurationAdapter(Context context, ArrayList<Restaurant> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
             LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_restauration_adapter, parent, false);

             holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvHoraire = convertView.findViewById(R.id.tvHoraire);
            holder.ivProfile = convertView.findViewById(R.id.ivPhoto);
            holder.tvType = convertView.findViewById(R.id.tvType);
            holder.ivFavorite = convertView.findViewById(R.id.ivFavorite);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = list.get(i);
        String nom = restaurant.getName() != null ? restaurant.getName() : "Nom inconnu";
        String type = restaurant.getType() != null ? restaurant.getType() : "Type inconnu";
        String horaire = restaurant.getOpeningHours() != null ? restaurant.getOpeningHours().toString() : "Horaire inconnu";

        // Modifier l'image de l'étoile en fonction de l'état de favoris
        if (isFavorite(restaurant)) {
            holder.ivFavorite.setImageResource(R.drawable.etoile); // Étoile pleine si c'est un favori
        } else {
            holder.ivFavorite.setImageResource(R.drawable.etoilenonremplit); // Étoile vide si ce n'est pas un favori
        }

        // Photo
        if (type.equals("restaurant")) {
            holder.ivProfile.setImageResource(R.drawable.resto);
        } else if (type.equals("fast_food")) {
            holder.ivProfile.setImageResource(R.drawable.fastfood);
        } else if (type.equals("cafe")) {
            holder.ivProfile.setImageResource(R.drawable.cafe);
        } else if (type.equals("pub")) {
            holder.ivProfile.setImageResource(R.drawable.pub);
        } else if (type.equals("bar")) {
            holder.ivProfile.setImageResource(R.drawable.bar);
        } else if (type.equals("ice_cream")) {
            holder.ivProfile.setImageResource(R.drawable.icecream);
        } else {
            holder.ivProfile.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.tvName.setText(nom);
        holder.tvHoraire.setText(horaire);
        holder.tvType.setText(type);


        // Charger la police
        holder.tvName.setTypeface(ResourcesCompat.getFont(context, R.font.policenomresto));
        holder.tvHoraire.setTypeface(ResourcesCompat.getFont(context, R.font.policetypeetcuisine));
        holder.tvType.setTypeface(ResourcesCompat.getFont(context, R.font.policetypeetcuisine));

        return convertView;
    }



    private boolean isFavorite(Restaurant restaurant) {
        MainActivity mainActivity = (MainActivity) context;
        if (mainActivity != null) {
            for (int i = 0; i < mainActivity.getFavorites().size(); i++) {
                if (mainActivity.getFavorites().get(i).getName().equals(restaurant.getName())) {
                    return mainActivity.getFavorites().get(i).getName().equals(restaurant.getName());
                }
            }
        }
        return false;

    }


    public void updateList(List<Restaurant> newRestaurants) {
        list.clear();
        list.addAll(newRestaurants);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView ivFavorite;
        TextView tvName;  // Nom du restaurant
        TextView tvHoraire; // Horaires du restaurant
        ImageView ivProfile; // Image du restaurant
        TextView tvType; // Type du restaurant
    }


}

