package com.example.pokedex2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.pokedex2.ui.Location.LocationFragment;
import com.example.pokedex2.ui.Location.RegionFragment;
import com.example.pokedex2.ui.Location.TypesFragment;
import com.example.pokedex2.ui.items.ItemFragment;
import com.example.pokedex2.ui.main.MainFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static FragmentManager fragmentManager;
    final int RC_SIGN_IN = 1;
    final String ANONYMOUS = "Anonymous";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String mUsername = ANONYMOUS;
    String mAuthEmail;
    Uri mAuthUri;
    ImageView navImg;
    TextView navName;
    TextView navEmail;
    MenuItem auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUsername = ANONYMOUS;


        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.close_drawer, R.string.open_drawer);
        drawerLayout.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        auth = navigationView.getMenu().findItem(R.id.logout);

        navImg = navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);
        navName = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        navEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
            navigationView.setCheckedItem(R.id.nav_pokemon);
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    onSignedInInitialise(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl());

                } else {
                    onSignedOutCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.AnonymousBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }

    private void setNavigationHeader() {
        if (mUsername != ANONYMOUS) {
            navName.setText(mUsername);
            navEmail.setText(mAuthEmail);
            Glide.with(this)
                    .load(mAuthUri)
                    .circleCrop()
                    .placeholder(R.drawable.pokedexpng)
                    .into(navImg);
            auth.setTitle("Logout");
        } else {
            navName.setText(ANONYMOUS);
            navEmail.setText("");
            navImg.setImageResource(R.mipmap.ic_launcher_round);
            auth.setTitle("Login");
        }
    }

    private void onSignedOutCleanUp() {
        mUsername = ANONYMOUS;
    }

    private void onSignedInInitialise(String displayName, String email, Uri photoUrl) {
        if (displayName != null) {
            mUsername = displayName;
            mAuthUri = photoUrl;
            mAuthEmail = email;

        } else {
            mUsername = ANONYMOUS;
        }
        setNavigationHeader();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
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
                MainFragment.publicPokeURL = null;
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
                sendMessage("https://bulbapedia.bulbagarden.net/wiki/Main_Page");
                break;
            }
            case R.id.logout: {
                //MenuItem auth = findViewById(R.id.logout);
                AuthUI.getInstance().signOut(this);
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendMessage(String url) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pokedex");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, (url + " Check this out !"));
        emailIntent.setType("text/plain");
        startActivity(Intent.createChooser(emailIntent, "Send to friend"));
    }
}
