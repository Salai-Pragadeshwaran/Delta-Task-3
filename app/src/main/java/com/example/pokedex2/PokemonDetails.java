package com.example.pokedex2;

public class PokemonDetails {
    private String name;
    private int id;
    private String type;
    private int height;
    private int weight;
    private String detail;
    private String imageUrl;

    public PokemonDetails(String name, int id, String type, int height, int weight, String detail, String imageUrl) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.height = height;
        this.weight = weight;
        this.detail = detail;
        this.imageUrl = imageUrl;
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
