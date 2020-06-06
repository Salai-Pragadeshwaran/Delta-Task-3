package com.example.pokedex2.ui.main;

public class Pokemon {
    private String name;
    private String imgUrl;

    public Pokemon(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
