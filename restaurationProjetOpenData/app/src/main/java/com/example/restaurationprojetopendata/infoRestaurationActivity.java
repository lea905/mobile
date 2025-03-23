package com.example.restaurationprojetopendata;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class infoRestaurationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contact);

        // Récupération des données du contact envoyées via Intent
        String name = getIntent().getStringExtra("nom");
        TextView n = findViewById(R.id.tvNomResto);
        // Charger la police
        n.setTypeface(ResourcesCompat.getFont(this, R.font.policenomresto));
        n.setText(name);


        String horaire = getIntent().getStringExtra("horaire");
        TextView h = findViewById(R.id.tvHoraire);
        h.setText(horaire);

        String type = getIntent().getStringExtra("type");
        TextView t = findViewById(R.id.tvTypeCuisine);
        t.setTypeface(ResourcesCompat.getFont(this, R.font.policetypeetcuisine));
        t.setText(type);

        String vegan = getIntent().getStringExtra("vegan");
        ImageView veg = findViewById(R.id.ivVegan);
        if (vegan.equals("yes")) {
            veg.setImageResource(R.drawable.ouivegan);
        } else {
            veg.setImageResource(R.drawable.nonvegan);
        }

        String vegetarian = getIntent().getStringExtra("vegetarien");
        ImageView vegt = findViewById(R.id.ivVegetarien);
        if (vegetarian.equals("yes")) {
            vegt.setImageResource(R.drawable.ouivegetarien);
        } else {
            vegt.setImageResource(R.drawable.nonvegetarien);
        }

        String access = getIntent().getStringExtra("accesHandicape");
        ImageView acc = findViewById(R.id.ivAccesHandicape);
        if (access.equals("yes")) {
            acc.setImageResource(R.drawable.handi_removebg_preview);
        } else {
            acc.setVisibility(View.INVISIBLE);
        }

        String livraison = getIntent().getStringExtra("livraison");
        ImageView liv = findViewById(R.id.ivLivraison);
        if (livraison.equals("yes")) {
            liv.setImageResource(R.drawable.livraison_removebg_preview);
        } else {
            liv.setImageResource(R.drawable.paslivraison_removebg_preview);
        }

        String empote = getIntent().getStringExtra("aEmporter");
        ImageView emp = findViewById(R.id.ivAEmporter);
        if (empote.equals("yes")) {
            emp.setImageResource(R.drawable.ouiemporter_removebg_preview);
        } else {
            emp.setImageResource(R.drawable.nonemporter_removebg_preview);
        }

        String web = getIntent().getStringExtra("siteWeb");
        TextView w = findViewById(R.id.tvWeb);
        w.setText(web);

        String tel = getIntent().getStringExtra("telephone");
        TextView telephone = findViewById(R.id.tvTel);
        telephone.setText(tel);

        String adresse = getIntent().getStringExtra("commune") + " "  + getIntent().getStringExtra("departement");
        TextView adr = findViewById(R.id.tvAddresse);
        adr.setText(adresse);

    }
}
//tv.setHeught(0);
//acc.setVisibility(View.INVISIBLE);