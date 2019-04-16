package com.hyunyong.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
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

    public String getName() {
        return name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getImage() {
        return image;
    }

}
