package com.hyunyong.myapplication.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Step;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import androidx.room.TypeConverter;

public class IngredientConverters {
    @TypeConverter
    public static ArrayList<Ingredient> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Ingredient> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}