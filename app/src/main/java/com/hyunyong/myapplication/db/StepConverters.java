package com.hyunyong.myapplication.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyunyong.myapplication.data.Step;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;

public class StepConverters {
    @TypeConverter
    public static ArrayList<Step> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Step>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Step> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
