package com.example.pokedex2.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class PokemonLoader extends AsyncTaskLoader<List<Pokemon>> {

    /** Tag for log messages */
    private static final String LOG_TAG = PokemonLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public PokemonLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Pokemon> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Pokemon> pokemons = QueryList.fetchData(mUrl);
        return pokemons;
    }
}
