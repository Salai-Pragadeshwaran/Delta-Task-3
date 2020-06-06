package com.example.pokedex2.ui.items;

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

public class ItemLoader extends AsyncTaskLoader<List<Item>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = ItemLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    public ItemLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Item> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        String jsonResponse = QueryList.fetchData(mUrl);
        // Create an empty ArrayList that we can start adding items to
        List<Item> items = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject itemData = new JSONObject(jsonResponse);
            JSONArray results;
            if (itemData.has("sprites")) {
                JSONObject sprites = itemData.getJSONObject("sprites");
                String itemUrl = sprites.getString("default");
                String itemName = itemData.getString("name");
                items.add(new Item(itemName, itemUrl));
                return items;

            }
            results = itemData.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject item = results.getJSONObject(i);
                String itemName = item.getString("name");


                items.add(new Item(itemName,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + itemName + ".png"));


            }
            // build up a list of Pokemon objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        // Return the list
        return items;
    }
}
