package com.example.pokedex2.ui.Location;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.pokedex2.ui.main.Pokemon;
import com.example.pokedex2.ui.main.QueryList;

import java.util.List;

public class LocationLoader extends AsyncTaskLoader<List<String>> {

    /** Tag for log messages */
    private static final String LOG_TAG = LocationLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public LocationLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<String> locations = QueryLocation.fetchData(mUrl);
        return locations;
    }
}
