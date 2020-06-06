package com.example.pokedex2.ui.Location;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex2.R;

import java.util.ArrayList;
import java.util.List;

public class TypesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {


    LoaderManager loaderManager;
    RecyclerView locationList;
    LocationAdapter locationAdapter;
    ArrayList<String> locations;
    String urlComponent = "type";
    public String URL_POKEAPI = "https://pokeapi.co/api/v2/" + urlComponent + "/?limit=20&offset=0";//+ 20*(mViewModel.pageNo-1);
    TextView mEmptyStateTextView;
    View loadingIndicator;

    public static TypesFragment newInstance() {
        return new TypesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.location_fragment, container, false);

        locations = new ArrayList<String>();
        locationAdapter = new LocationAdapter(locations, root.getContext());
        mEmptyStateTextView = (TextView) root.findViewById(R.id.empty_view_location);
        loadingIndicator = root.findViewById(R.id.progress_bar_location);

        locationList = (RecyclerView) root.findViewById(R.id.location_Recycler);
        locationList.setAdapter(locationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        locationList.setLayoutManager(linearLayoutManager);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        loaderManager = getLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        TextView previous = (TextView) root.findViewById(R.id.prevPage_location);
        TextView next = (TextView) root.findViewById(R.id.nextPage_location);
        final TextView pageNo = (TextView) root.findViewById(R.id.pageNo_location);
        Button search = (Button) root.findViewById(R.id.startSearch_location);
        final EditText searchText = (EditText) root.findViewById(R.id.searchLocation);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchText.getText().toString();
                searchQuery.toLowerCase();

                if (searchQuery != "") {
                    URL_POKEAPI = "https://pokeapi.co/api/v2/" + urlComponent + "/" + searchQuery;
                } else {
                    //URL_POKEAPI = "https://pokeapi.co/api/v2/"+urlComponent+"?limit=20&offset=0" + 20*(mViewModel.pageNo-1);
                }

                loaderManager.destroyLoader(0);
                loadingIndicator.setVisibility(View.VISIBLE);
                loaderManager.initLoader(0, null, TypesFragment.this);
            }
        });


        return root;
    }


    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LocationLoader(getContext(), URL_POKEAPI);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {
        mEmptyStateTextView.setText(R.string.no_type);
        loadingIndicator.setVisibility(View.GONE);
        locations.clear();
        locationList.removeAllViewsInLayout();
        locationList.setAdapter(locationAdapter);
        locations.addAll(data);
        if (locations.size() != 0) {
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        locations.clear();
    }
}
