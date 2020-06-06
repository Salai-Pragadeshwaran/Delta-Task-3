package com.example.pokedex2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.bumptech.glide.Glide;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.List;

public class PokemonDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonDetails> {

    public static String PokemonName = "pikachu";
    LoaderManager loaderManager;
    ImageView pokeimg;
    TextView pokename;
    TextView poketype;
    TextView pokeHeight;
    TextView pokeWeight;
    TextView pokeDetail;
    TextView pokeid;
    String URL = "https://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);

        PokemonName = getIntent().getStringExtra("NAME");

        pokeimg = findViewById(R.id.pokemon_detail_image);
        pokename = findViewById(R.id.pokemon_detail_name);
        poketype = findViewById(R.id.pokemon_detail_type);
        pokeHeight = findViewById(R.id.pokemon_detail_height);
        pokeWeight = findViewById(R.id.pokemon_detail_weight);
        pokeid = findViewById(R.id.pokemon_detail_id);
        pokeDetail = findViewById(R.id.pokemon_detail_detail);

        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        loaderManager = getSupportLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            //mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @NonNull
    @Override
    public Loader<PokemonDetails> onCreateLoader(int id, @Nullable Bundle args) {
        return new PokemonDetailsLoader(this, URL + PokemonName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<PokemonDetails> loader, PokemonDetails data) {
        pokename.setText(data.getName());
        pokeWeight.setText(String.valueOf(data.getWeight()));
        pokeHeight.setText(String.valueOf(data.getHeight()));
        poketype.setText(String.valueOf(data.getType()));
        pokeid.setText(String.valueOf(data.getId()));
        pokeDetail.setText(data.getDetail());
        Glide.with(this)
                .asBitmap()
                .load(data.getImageUrl())
                .into(pokeimg);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonDetails> loader) {

    }
}
