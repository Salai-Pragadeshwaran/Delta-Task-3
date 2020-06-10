package com.example.pokedex2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex2.ui.items.Item;
import com.example.pokedex2.ui.items.ItemAdapter;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.ArrayList;

public class PokemonDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonDetails> {

    public static String PokemonName = "pikachu";
    LoaderManager loaderManager;
    ImageView pokeimg;
    TextView pokename;
    TextView poketype;
    TextView pokeHeight;
    TextView pokeWeight;
    TextView pokeDetail;
    TextView pokeid;
    RecyclerView pokeList;
    PokemonEvolutionAdapter pokemonEvolutionAdapter;
    ArrayList<Pokemon> pokemonsEvolution = new ArrayList<>();
    String URL = "https://pokeapi.co/api/v2/pokemon/";
    TextView hpValue;
    TextView attackValue;
    TextView defenseValue;
    TextView sAttackValue;
    TextView sDefenseValue;
    TextView speedValue;
    ProgressBar hpValuePb;
    ProgressBar attackValuePb;
    ProgressBar defenseValuePb;
    ProgressBar sAttackValuePb;
    ProgressBar sDefenseValuePb;
    ProgressBar speedValuePb;
    RecyclerView heldItemsList;
    ArrayList<Item> heldItems = new ArrayList<>();
    ItemAdapter itemAdapter;
    TextView headHeldItems;
    View loadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        //fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        PokemonName = getIntent().getStringExtra("NAME");

        loadingIndicator = findViewById(R.id.progress_bar2);
        pokeimg = findViewById(R.id.pokemon_detail_image);
        pokename = findViewById(R.id.pokemon_detail_name);
        poketype = findViewById(R.id.pokemon_detail_type);
        pokeHeight = findViewById(R.id.pokemon_detail_height);
        pokeWeight = findViewById(R.id.pokemon_detail_weight);
        pokeid = findViewById(R.id.pokemon_detail_id);
        pokeDetail = findViewById(R.id.pokemon_detail_detail);
        hpValue = findViewById(R.id.hp);
        attackValue = findViewById(R.id.attack);
        defenseValue = findViewById(R.id.defense);
        sAttackValue = findViewById(R.id.sp_attack);
        sDefenseValue = findViewById(R.id.sp_defense);
        speedValue = findViewById(R.id.speed);
        hpValuePb = findViewById(R.id.progressBar_hp);
        attackValuePb = findViewById(R.id.progressBar_attack);
        defenseValuePb = findViewById(R.id.progressBar_defense);
        sAttackValuePb = findViewById(R.id.progressBar_sp_attack);
        sDefenseValuePb = findViewById(R.id.progressBar_sp_defense);
        speedValuePb = findViewById(R.id.progressBar_speed);
        heldItemsList = findViewById(R.id.item_Recycler_detail);
        headHeldItems = findViewById(R.id.heading_held_items);

        pokeList = (RecyclerView) findViewById(R.id.evolution_recycler);


        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        loaderManager = getSupportLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //loadingIndicator.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            //mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poke_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_pokemon: {
                final Intent sharePokemon = new Intent(android.content.Intent.ACTION_SEND);
                String url = "https://bulbapedia.bulbagarden.net/wiki/";
                sharePokemon.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pokedex");
                sharePokemon.putExtra(android.content.Intent.EXTRA_TEXT, (url + PokemonName + " Check this out !"));
                sharePokemon.setType("text/plain");
                startActivity(Intent.createChooser(sharePokemon, "Share via"));
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @NonNull
    @Override
    public Loader<PokemonDetails> onCreateLoader(int id, @Nullable Bundle args) {
        return new PokemonDetailsLoader(this, URL + PokemonName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<PokemonDetails> loader, PokemonDetails data) {
        if (data!=null) {
            loadingIndicator.setVisibility(View.GONE);loadingIndicator.setVisibility(View.GONE);
            pokename.setText(data.getName().toUpperCase());
            pokeWeight.setText(String.valueOf(data.getWeight()));
            pokeHeight.setText(String.valueOf(data.getHeight()));
            poketype.setText(String.valueOf(data.getType()));
            String id = "#" + (data.getId());
            pokeid.setText(id);
            pokeDetail.setText(data.getDetail());
            Glide.with(this)
                    .asDrawable()
                    .load(data.getImageUrl())
                    .into(pokeimg);
            if (data.getEvolution()!=null) {
                pokemonsEvolution.clear();
                pokemonsEvolution.addAll(data.getEvolution());
            }
            pokemonEvolutionAdapter = new PokemonEvolutionAdapter(pokemonsEvolution, this);
            pokeList.setAdapter(pokemonEvolutionAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            pokeList.setLayoutManager(linearLayoutManager);

            hpValue.setText(String.valueOf(data.getStats().get(0)));
            attackValue.setText(String.valueOf(data.getStats().get(1)));
            defenseValue.setText(String.valueOf(data.getStats().get(2)));
            sAttackValue.setText(String.valueOf(data.getStats().get(3)));
            sDefenseValue.setText(String.valueOf(data.getStats().get(4)));
            speedValue.setText(String.valueOf(data.getStats().get(5)));

            hpValuePb.setProgress((data.getStats().get(0)*100)/250);
            attackValuePb.setProgress((data.getStats().get(1)*100)/250);
            defenseValuePb.setProgress((data.getStats().get(2)*100)/250);
            sAttackValuePb.setProgress((data.getStats().get(3)*100)/250);
            sDefenseValuePb.setProgress((data.getStats().get(4)*100)/250);
            speedValuePb.setProgress((data.getStats().get(5)*100)/250);

            heldItems.clear();
            heldItems.addAll(data.getHeldItems());
            itemAdapter = new ItemAdapter(heldItems, this);
            heldItemsList.setAdapter(itemAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            heldItemsList.setLayoutManager(linearLayoutManager);
            if(heldItems.size()==0){
                headHeldItems.setVisibility(View.GONE);
            }
            else {
                headHeldItems.setVisibility(View.VISIBLE);
            }
        }else {
            pokename.setText("ERROR !");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonDetails> loader) {

    }
}
