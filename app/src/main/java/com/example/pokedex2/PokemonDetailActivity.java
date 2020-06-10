package com.example.pokedex2;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex2.database.FavPokemon;
import com.example.pokedex2.ui.items.Item;
import com.example.pokedex2.ui.items.ItemAdapter;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonDetails> {

    public static String PokemonName = "pikachu";

    PokemonDetails pokeData;
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
    List<FavPokemon> favPokemons;
    MenuItem favItem;
    Menu optionsMenu;

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
        @SuppressLint("ResourceType") Menu menu = findViewById(R.menu.poke_details_menu);

        pokeList = (RecyclerView) findViewById(R.id.evolution_recycler);

        favPokemons = MainActivity.pokedexDatabase.pokeDao().getFavPokemon();


        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        loaderManager = getSupportLoaderManager();
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager.initLoader(0, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            for(int i=0; i<favPokemons.size();i++){
                String databaseName = favPokemons.get(i).getName();
                if(databaseName.equals(PokemonName.toString())){
                    loadingIndicator.setVisibility(View.GONE);
                    pokename.setText(favPokemons.get(i).getName().toUpperCase());
                    pokeWeight.setText(String.valueOf(favPokemons.get(i).getWeight()));
                    pokeHeight.setText(String.valueOf(favPokemons.get(i).getHeight()));
                    poketype.setText(String.valueOf(favPokemons.get(i).getType()));
                    String id = "#" + (favPokemons.get(i).getId());
                    pokeid.setText(id);
                    pokeDetail.setText(favPokemons.get(i).getDetail());
                    Glide.with(this)
                            .asDrawable()
                            .load(favPokemons.get(i).getImageUrl())
                            .into(pokeimg);
                    hpValue.setText(String.valueOf(favPokemons.get(i).getStat1()));
                    attackValue.setText(String.valueOf(favPokemons.get(i).getStat2()));
                    defenseValue.setText(String.valueOf(favPokemons.get(i).getStat3()));
                    sAttackValue.setText(String.valueOf(favPokemons.get(i).getStat4()));
                    sDefenseValue.setText(String.valueOf(favPokemons.get(i).getStat5()));
                    speedValue.setText(String.valueOf(favPokemons.get(i).getStat6()));

                    hpValuePb.setProgress((favPokemons.get(i).getStat1()*100)/250);
                    attackValuePb.setProgress((favPokemons.get(i).getStat2()*100)/250);
                    defenseValuePb.setProgress((favPokemons.get(i).getStat3()*100)/250);
                    sAttackValuePb.setProgress((favPokemons.get(i).getStat4()*100)/250);
                    sDefenseValuePb.setProgress((favPokemons.get(i).getStat5()*100)/250);
                    speedValuePb.setProgress((favPokemons.get(i).getStat6()*100)/250);
                    headHeldItems.setVisibility(View.GONE);
                    pokeList.setVisibility(View.GONE);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poke_details_menu, menu);
        favItem = menu.findItem(R.id.addFav);
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
                return true;
            }
            case R.id.addFav:{
                FavPokemon favPokemon = new FavPokemon();
                if(pokeData==null) {
                    if(!pokeid.getText().toString().equals("Id")){
                        favItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                        favItem.setTitle("Add to Favorites");
                        favPokemon.setName(pokename.getText().toString().toLowerCase());
                        MainActivity.pokedexDatabase.pokeDao().deleteFavPokemon(favPokemon);
                        Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    Toast.makeText(this, "Could not add to Favorites\nTry again later.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (pokeData!=null) {

                    if (favItem.getTitle()=="Add to Favorites") {
                        favPokemon.setName(pokeData.getName());
                        favPokemon.setId(pokeData.getId());
                        favPokemon.setDetail(pokeData.getDetail());
                        favPokemon.setHeight(pokeData.getHeight());
                        favPokemon.setWeight(pokeData.getWeight());
                        favPokemon.setType(pokeData.getType());
                        favPokemon.setImageUrl(pokeData.getImageUrl());
                        favPokemon.setStat1(pokeData.getStats().get(0));
                        favPokemon.setStat2(pokeData.getStats().get(1));
                        favPokemon.setStat3(pokeData.getStats().get(2));
                        favPokemon.setStat4(pokeData.getStats().get(3));
                        favPokemon.setStat5(pokeData.getStats().get(4));
                        favPokemon.setStat6(pokeData.getStats().get(5));
                        MainActivity.pokedexDatabase.pokeDao().addPokemonDetail(favPokemon);
                        Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                        favItem.setIcon(R.drawable.ic_favorite_white_24dp);
                        favItem.setTitle("Remove from Favourites");
                    }
                    else{
                        favItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                        favItem.setTitle("Add to Favorites");
                        favPokemon.setName(pokeData.getName());
                        MainActivity.pokedexDatabase.pokeDao().deleteFavPokemon(favPokemon);
                        Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
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
            pokeData = data;
            loadingIndicator.setVisibility(View.GONE);
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
            favItem.setTitle("Add to Favorites");
            for(int i=0; i<favPokemons.size();i++){
                if(favPokemons.get(i).getId()==data.getId()){
                    favItem.setIcon(R.drawable.ic_favorite_white_24dp);
                    favItem.setTitle("Remove from Favourites");
                    break;
                }
            }
        }else {
            pokename.setText("ERROR !");

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonDetails> loader) {

    }
}
