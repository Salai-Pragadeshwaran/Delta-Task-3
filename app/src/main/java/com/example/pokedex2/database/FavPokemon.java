package com.example.pokedex2.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavPokemon {
    @PrimaryKey @NonNull
    private String name;
    private int id;
    private String type;
    private int height;
    private int weight;
    private String detail;
    private String imageUrl;
    private int stat1;
    private int stat2;
    private int stat3;
    private int stat4;
    private int stat5;
    private int stat6;

    //private ArrayList<Pokemon> evolution;
    //private ArrayList<Integer> stats;


    public int getStat1() {
        return stat1;
    }

    public void setStat1(int stat1) {
        this.stat1 = stat1;
    }

    public int getStat2() {
        return stat2;
    }

    public void setStat2(int stat2) {
        this.stat2 = stat2;
    }

    public int getStat3() {
        return stat3;
    }

    public void setStat3(int stat3) {
        this.stat3 = stat3;
    }

    public int getStat4() {
        return stat4;
    }

    public void setStat4(int stat4) {
        this.stat4 = stat4;
    }

    public int getStat5() {
        return stat5;
    }

    public void setStat5(int stat5) {
        this.stat5 = stat5;
    }

    public int getStat6() {
        return stat6;
    }

    public void setStat6(int stat6) {
        this.stat6 = stat6;
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
