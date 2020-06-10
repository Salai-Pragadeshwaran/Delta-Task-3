package com.example.pokedex2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.pokedex2.ui.items.Item;
import com.example.pokedex2.ui.main.Pokemon;
import com.example.pokedex2.ui.main.QueryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokemonDetailsLoader extends AsyncTaskLoader<PokemonDetails> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = PokemonDetailsLoader.class.getName();

    /**
     * Query URL
     */
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

        String jsonResponse = QueryList.fetchData(mUrl);


        PokemonDetails pokemonDetails;


        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject pokemonData = new JSONObject(jsonResponse);
            String img = pokemonData.getJSONObject("sprites").getString("front_default");
            String name = pokemonData.getString("name");
            int weight = Integer.valueOf(pokemonData.getString("weight"));
            int height = Integer.valueOf(pokemonData.getString("height"));
            int id = Integer.valueOf(pokemonData.getString("id"));
            ArrayList<Integer> stats = new ArrayList<>();
            JSONArray statsArray = pokemonData.getJSONArray("stats");
            for(int i = 0; i<statsArray.length(); i++){
                int value = statsArray.getJSONObject(i).getInt("base_stat");
                stats.add(value);
            }
            ArrayList<Item> heldItems = new ArrayList<>();
            JSONArray held_items = pokemonData.getJSONArray("held_items");
            for(int i = 0; i<held_items.length(); i++){
                JSONObject item = held_items.getJSONObject(i).getJSONObject("item");
                String item_name = item.getString("name");
                heldItems.add(new Item(item_name,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + item_name + ".png"));
            }
            String type = null;
            for (int i =0; i<pokemonData.getJSONArray("types").length(); i++) {
                if(type == null){
                    type = pokemonData.getJSONArray("types").getJSONObject(i)
                            .getJSONObject("type").getString("name");
                }else {
                    type = type + ", " + pokemonData.getJSONArray("types").getJSONObject(i)
                            .getJSONObject("type").getString("name");
                }
            }
            String detail = "";
            // build up a list of Pokemon objects with the corresponding data.
            String pokeSpecies = QueryList.fetchData("https://pokeapi.co/api/v2/pokemon-species/" + id);
            ArrayList<Pokemon> evolution = null;
            if (pokeSpecies!="") {
                pokemonData = new JSONObject(pokeSpecies);
                String evolutionResponse = QueryList.fetchData(pokemonData.getJSONObject("evolution_chain").getString("url"));
                pokemonData = new JSONObject(evolutionResponse);
                JSONObject evolutionData = pokemonData.getJSONObject("chain");

                String id2;
                evolution = new ArrayList<>();
                String epokeName = evolutionData.getJSONObject("species").getString("name");
                id2 = evolutionData.getJSONObject("species").getString("url");
                id2 = id2.substring("https://pokeapi.co/api/v2/pokemon-species/".length(), id2.length() -1);
                evolution.add(new Pokemon(epokeName, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id2 + ".png"));

                while (evolutionData.has("evolves_to")) {
                    if (evolutionData.getJSONArray("evolves_to").length() != 0) {
                        evolutionData = evolutionData.getJSONArray("evolves_to").getJSONObject(0);
                        epokeName = evolutionData.getJSONObject("species").getString("name");
                        id2 = evolutionData.getJSONObject("species").getString("url");
                        id2 = id2.substring("https://pokeapi.co/api/v2/pokemon-species/".length(), id2.length() -1);
                        evolution.add(new Pokemon(epokeName, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id2 + ".png"));
                    } else break;
                }
            }

            pokemonDetails = new PokemonDetails(name, id, type, height, weight, detail, img, evolution, stats, heldItems);

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
            return null;
        }


        return pokemonDetails;
    }
}
