package com.hyunyong.myapplication.data;

import java.util.ArrayList;

public class Recipe {
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String image;

    public Recipe(int id,
                  String name,
                  ArrayList<Ingredient> ingredients,
                  ArrayList<Step> steps,
                  int serving,
                  String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
