package com.example.pokedex2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokeDao {
    @Insert
    public void addPokemonDetail(FavPokemon favPokemon);
    @Query("select * from FavPokemon")
    public List<FavPokemon> getFavPokemon();
    @Delete
    public void deleteFavPokemon(FavPokemon favPokemon);
}
