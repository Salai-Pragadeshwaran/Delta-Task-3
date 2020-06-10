package com.example.pokedex2.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pokedex2.R;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Pokemon>> {

    public static String publicPokeURL = null;
    public static String publicFromFragment = null;

    private MainViewModel mViewModel;
    LoaderManager loaderManager ;
    RecyclerView pokeList;
    public static MainFragment newInstance() {
        return new MainFragment();
    }
    PokemonAdapter pokemonAdapter;
    ArrayList<Pokemon> pokemons;
    public String URL_POKEAPI = "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0";//+ 20*(mViewModel.pageNo-1);
    TextView mEmptyStateTextView;
    View loadingIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.pokemon_fragment, container, false);

        pokemons = new ArrayList<>();
        pokemonAdapter = new PokemonAdapter(pokemons, getContext());
        mEmptyStateTextView = (TextView) root.findViewById(R.id.empty_view);
        loadingIndicator = root.findViewById(R.id.progress_bar);

        pokeList = (RecyclerView) root.findViewById(R.id.pokemon_Recycler);
        pokeList.setAdapter(pokemonAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        pokeList.setLayoutManager(linearLayoutManager);


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


        TextView previous = (TextView) root.findViewById(R.id.prevPage);
        TextView next = (TextView) root.findViewById(R.id.nextPage);
        final TextView pageNo = (TextView) root.findViewById(R.id.pageNo);
        Button search = (Button) root.findViewById(R.id.startSearch);
        final EditText searchText = (EditText) root.findViewById(R.id.searchPokemon);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchText.getText().toString();
                searchQuery = searchQuery.toLowerCase();

                if (searchQuery!="") {
                    URL_POKEAPI = "https://pokeapi.co/api/v2/pokemon/" + searchQuery;
                }else {
                    URL_POKEAPI = "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0" + 20*(mViewModel.pageNo-1);
                }

                loaderManager.destroyLoader(0);
                loadingIndicator.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setVisibility(View.GONE);
                loaderManager.initLoader(0, null, MainFragment.this);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mViewModel.pageNo>1)&&(publicPokeURL==null)) {
                    mViewModel.pageNo--;
                    String text = String.valueOf(mViewModel.pageNo);
                    text = "Page "+text;
                    pageNo.setText(text);

                    URL_POKEAPI = "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0" + 20*(mViewModel.pageNo-1);

                    loaderManager.destroyLoader(0);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    loaderManager.initLoader(0, null, MainFragment.this);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publicPokeURL==null) {
                    mViewModel.pageNo++;
                    String text = String.valueOf(mViewModel.pageNo);
                    text = "Page "+text;
                    pageNo.setText(text);

                    URL_POKEAPI = "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0" + 20*(mViewModel.pageNo-1);

                    loaderManager.destroyLoader(0);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    loaderManager.initLoader(0, null, MainFragment.this);
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @NonNull
    @Override
    public Loader<List<Pokemon>> onCreateLoader(int id, @Nullable Bundle args) {
        if (publicPokeURL!=null){
            return new PokemonLoader(getContext(), publicPokeURL);
        }
        return new PokemonLoader(getContext(), URL_POKEAPI);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Pokemon>> loader, List<Pokemon> data) {
        mEmptyStateTextView.setText(R.string.no_pokemons);
        loadingIndicator.setVisibility(View.GONE);
        pokemons.clear();
        pokeList.removeAllViewsInLayout();
        pokeList.setAdapter(pokemonAdapter);
        pokemons.addAll(data);
        if(pokemons.size()!=0){
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Pokemon>> loader) {
        pokemons.clear();
    }


}
