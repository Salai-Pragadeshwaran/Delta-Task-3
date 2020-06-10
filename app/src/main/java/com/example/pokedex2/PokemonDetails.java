package com.example.pokedex2;

import com.example.pokedex2.ui.items.Item;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.ArrayList;

public class PokemonDetails {
    private String name;
    private int id;
    private String type;
    private int height;
    private int weight;
    private String detail;
    private String imageUrl;
    private ArrayList<Pokemon> evolution;
    private ArrayList<Integer> stats;
    private ArrayList<Item> heldItems;

    public PokemonDetails(String name, int id, String type, int height, int weight, String detail,
                          String imageUrl, ArrayList<Pokemon> evol, ArrayList<Integer> stat, ArrayList<Item> heldItems) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.height = height;
        this.weight = weight;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.evolution = evol;
        this.stats = stat;
        this.heldItems = heldItems;
    }

    public ArrayList<Item> getHeldItems() {
        return heldItems;
    }

    public void setHeldItems(ArrayList<Item> heldItems) {
        this.heldItems = heldItems;
    }

    public ArrayList<Integer> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }

    public ArrayList<Pokemon> getEvolution() {
        return evolution;
    }

    public void setEvolution(ArrayList<Pokemon> evolution) {
        this.evolution = evolution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
