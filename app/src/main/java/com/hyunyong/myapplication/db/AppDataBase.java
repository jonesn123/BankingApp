package com.hyunyong.myapplication.db;

import android.content.Context;

import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.db.dao.IngredientDao;
import com.hyunyong.myapplication.db.dao.RecipeDao;
import com.hyunyong.myapplication.db.dao.StepDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientConverters.class, StepConverters.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract StepDao stepDao();
    public abstract IngredientDao ingredientDao();

    private static final String DATABASE_NAME = "banking-db";
    private static AppDataBase INSTANCE = null;

    public static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, DATABASE_NAME).allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}