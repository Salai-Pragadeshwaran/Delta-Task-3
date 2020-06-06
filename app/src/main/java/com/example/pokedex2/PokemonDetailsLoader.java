package com.example.pokedex2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.pokedex2.ui.main.Pokemon;
import com.example.pokedex2.ui.main.QueryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetailsLoader extends AsyncTaskLoader<PokemonDetails> {

    /** Tag for log messages */
    private static final String LOG_TAG = PokemonDetailsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public PokemonDetailsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public PokemonDetails loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        String jsonResponse = QueryList.fetchData(mUrl);



            // Create an empty ArrayList that we can start adding pokemons to
            PokemonDetails pokemonDetails ;

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject pokemonData = new JSONObject(jsonResponse);
                String img = pokemonData.getJSONObject("sprites").getString("front_default");
                String name = pokemonData.getString("name");
                int weight = Integer.valueOf(pokemonData.getString("weight"));
                int height = Integer.valueOf(pokemonData.getString("height"));
                int id = Integer.valueOf(pokemonData.getString("id"));
                String type = pokemonData.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name");
                String detail = "blah blah";
                // build up a list of Pokemon objects with the corresponding data.

                pokemonDetails = new PokemonDetails(name, id, type, height, weight, detail, img);

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the JSON results", e);
                return null;
            }

            // Return the list



        return pokemonDetails;
    }
}
