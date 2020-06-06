package com.example.pokedex2.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonLoader extends AsyncTaskLoader<List<Pokemon>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = PokemonLoader.class.getName();

    /**
     * Query URL
     */
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
        String jsonResponse = QueryList.fetchData(mUrl);


        // Create an empty ArrayList that we can start adding pokemons to
        List<Pokemon> pokemons = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        if (MainFragment.publicPokeURL == null) {
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject pokemonData = new JSONObject(jsonResponse);
                JSONArray results;
                if (pokemonData.has("results")) {
                    results = pokemonData.getJSONArray("results");
                } else {
                    results = pokemonData.getJSONArray("forms");
                }
                for (int i = 0; i < results.length(); i++) {
                    JSONObject poke = results.getJSONObject(i);
                    String pokeName = poke.getString("name");
                    String pokeUrl;
                    if (pokemonData.has("sprites")) {
                        JSONObject sprites = pokemonData.getJSONObject("sprites");
                        pokeUrl = sprites.getString("front_default");
                        pokemons.add(new Pokemon(pokeName, pokeUrl));
                    } else {
                        pokeUrl = poke.getString("url");
                        pokeUrl = pokeUrl.substring("https://pokeapi.co/api/v2/pokemon/".length()
                                , pokeUrl.length() - 1);
                        pokemons.add(new Pokemon(pokeName,
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeUrl + ".png"));
                    }

                }
                // build up a list of Pokemon objects with the corresponding data.

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the JSON results", e);
            }
        } else if (MainFragment.publicFromFragment == "type") {
            // for Types
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject pokemonData = new JSONObject(jsonResponse);
                JSONArray results;
                if (pokemonData.has("pokemon")) {
                    results = pokemonData.getJSONArray("pokemon");
                } else {
                    results = pokemonData.getJSONArray("forms");
                }
                for (int i = 0; i < results.length(); i++) {
                    JSONObject poke = results.getJSONObject(i).getJSONObject("pokemon");
                    String pokeName = poke.getString("name");
                    String pokeUrl;
                    if (pokemonData.has("sprites")) {
                        JSONObject sprites = pokemonData.getJSONObject("sprites");
                        pokeUrl = sprites.getString("front_default");
                        pokemons.add(new Pokemon(pokeName, pokeUrl));
                    } else {
                        pokeUrl = poke.getString("url");
                        pokeUrl = pokeUrl.substring("https://pokeapi.co/api/v2/pokemon/".length()
                                , pokeUrl.length() - 1);
                        pokemons.add(new Pokemon(pokeName,
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeUrl + ".png"));
                    }

                }
                // build up a list of Pokemon objects with the corresponding data.

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the JSON results", e);
            }
        } else if (MainFragment.publicFromFragment == "region") {
            // for Types
            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                JSONObject pokemonData = new JSONObject(jsonResponse);
                JSONArray pokedexes = pokemonData.getJSONArray("pokedexes");
                String url2 = pokedexes.getJSONObject(pokedexes.length() - 1).getString("url");
                pokemonData = new JSONObject(QueryList.fetchData(url2));
                JSONArray results;
                if (pokemonData.has("pokemon_entries")) {
                    results = pokemonData.getJSONArray("pokemon_entries");
                } else {
                    results = pokemonData.getJSONArray("forms");
                }
                for (int i = 0; i < results.length(); i++) {
                    JSONObject poke = results.getJSONObject(i).getJSONObject("pokemon_species");
                    String pokeName = poke.getString("name");
                    String pokeUrl;
                    if (pokemonData.has("sprites")) {
                        JSONObject sprites = pokemonData.getJSONObject("sprites");
                        pokeUrl = sprites.getString("front_default");
                        pokemons.add(new Pokemon(pokeName, pokeUrl));
                    } else {
                        pokeUrl = poke.getString("url");
                        pokeUrl = pokeUrl.substring("https://pokeapi.co/api/v2/pokemon-species/".length()
                                , pokeUrl.length() - 1);
                        pokemons.add(new Pokemon(pokeName,
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeUrl + ".png"));
                    }

                }
                // build up a list of Pokemon objects with the corresponding data.

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the JSON results", e);
            }
        }


        return pokemons;
    }
}
