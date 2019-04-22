package com.hyunyong.myapplication.db.dao;

import com.hyunyong.myapplication.data.Ingredient;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    List<Ingredient> getIngredients();

    @Insert(onConflict = REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Query("DELETE FROM ingredient")
    void deleteAll();
}
