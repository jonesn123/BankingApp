package com.hyunyong.myapplication.db.dao;

import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.data.Step;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT ingredients FROM recipe WHERE id=:id")
    LiveData<String> getIngredients(int id);

    @Query("SELECT steps FROM recipe WHERE id=:id")
    LiveData<String> getSteps(int id);

    @Insert(onConflict = REPLACE)
    void insert(Recipe recipe);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Recipe> recipes);

    @Query("DELETE FROM recipe")
    void deleteAll();
}
