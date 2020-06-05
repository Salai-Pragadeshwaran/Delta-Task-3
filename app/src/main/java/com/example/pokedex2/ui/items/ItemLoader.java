package com.example.pokedex2.ui.items;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.pokedex2.ui.main.Pokemon;
import com.example.pokedex2.ui.main.QueryList;

import java.util.List;

public class ItemLoader extends AsyncTaskLoader<List<Item>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ItemLoader.class.getName();

    /** Query URL */
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
        List<Item> items = QueryItems.fetchData(mUrl);
        return items;
    }
}
