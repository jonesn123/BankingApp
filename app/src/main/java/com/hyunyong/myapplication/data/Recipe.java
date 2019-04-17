package com.hyunyong.myapplication.data;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe implements Serializable {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "ingredients")
    private ArrayList<Ingredient> ingredients;

    @ColumnInfo(name = "steps")
    private ArrayList<Step> steps;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "serving")
    private int serving;

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
        this.serving = serving;
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

    public int getServing() {
        return serving;
    }

}
