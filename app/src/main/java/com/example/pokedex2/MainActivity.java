package com.example.pokedex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.pokedex2.ui.Location.LocationFragment;
import com.example.pokedex2.ui.Location.RegionFragment;
import com.example.pokedex2.ui.Location.TypesFragment;
import com.example.pokedex2.ui.items.ItemFragment;
import com.example.pokedex2.ui.main.MainFragment;
import com.example.pokedex2.ui.main.Pokemon;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close_drawer, R.string.open_drawer);
        drawerLayout.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
            navigationView.setCheckedItem(R.id.nav_pokemon);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_pokemon: {
                MainFragment.publicPokeURL = null;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow();
                break;
            }
            case R.id.nav_items: {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ItemFragment.newInstance())
                        .commitNow();
                break;
            }
            case R.id.nav_locations: {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, LocationFragment.newInstance())
                        .commitNow();
                break;
            }
            case R.id.nav_regions: {
                MainFragment.publicPokeURL = "https://pokeapi.co/api/v2/region";
                MainFragment.publicFromFragment = "region";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, RegionFragment.newInstance())
                        .commitNow();
                break;
            }
            case R.id.nav_types: {
                MainFragment.publicPokeURL = "https://pokeapi.co/api/v2/type";
                MainFragment.publicFromFragment = "type";
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TypesFragment.newInstance())
                        .commitNow();
                break;
            }
            case R.id.share: {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                this.startActivity(new Intent(this, PokemonDetailActivity.class));
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
