package com.example.pokedex2.database;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex2.MainActivity;
import com.example.pokedex2.R;
import com.example.pokedex2.ui.main.MainViewModel;
import com.example.pokedex2.ui.main.Pokemon;
import com.example.pokedex2.ui.main.PokemonAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment {

    private MainViewModel mViewModel;
    RecyclerView pokeList;

    public static FavFragment newInstance() {
        return new FavFragment();
    }

    PokemonAdapter pokemonAdapter;
    ArrayList<Pokemon> pokemons;
    TextView mEmptyStateTextView;
    View loadingIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.pokemon_fragment, container, false);

        List<FavPokemon> favPokemons;
        favPokemons = MainActivity.pokedexDatabase.pokeDao().getFavPokemon();
        pokemons = new ArrayList<>();
        for(int i=0; i<favPokemons.size();i++){
            pokemons.add(new Pokemon(favPokemons.get(i).getName(), favPokemons.get(i).getImageUrl()));
        }
        pokemonAdapter = new PokemonAdapter(pokemons, getContext());
        mEmptyStateTextView = (TextView) root.findViewById(R.id.empty_view);
        loadingIndicator = root.findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setVisibility(View.GONE);

        pokeList = (RecyclerView) root.findViewById(R.id.pokemon_Recycler);
        pokeList.setAdapter(pokemonAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        pokeList.setLayoutManager(linearLayoutManager);


        // Get a reference to the ConnectivityManager to check state of network connectivity



        TextView previous = (TextView) root.findViewById(R.id.prevPage);
        TextView next = (TextView) root.findViewById(R.id.nextPage);
        final TextView pageNo = (TextView) root.findViewById(R.id.pageNo);
        Button search = (Button) root.findViewById(R.id.startSearch);
        final EditText searchText = (EditText) root.findViewById(R.id.searchPokemon);
        searchText.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        previous.setVisibility(View.GONE);
        pageNo.setVisibility(View.GONE);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}
