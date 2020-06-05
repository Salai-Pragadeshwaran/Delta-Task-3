package com.example.pokedex2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pokedex2.ui.Location.LocationFragment;
import com.example.pokedex2.ui.items.ItemFragment;
import com.example.pokedex2.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LocationFragment.newInstance())
                    .commitNow();
        }
    }
}
