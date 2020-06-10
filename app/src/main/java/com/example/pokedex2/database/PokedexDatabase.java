package com.example.pokedex2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = FavPokemon.class, version = 1)
public abstract class PokedexDatabase extends RoomDatabase {
    public abstract PokeDao pokeDao();
}
