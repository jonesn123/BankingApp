package com.hyunyong.myapplication.db.dao;

import com.hyunyong.myapplication.data.Step;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepDao {
    @Query("SELECT * FROM step WHERE id=:id")
    Step getStep(int id);

    @Query("SELECT * FROM step WHERE id=:id")
    LiveData<Step> getLiveStep(int id);

    @Query("SELECT COUNT(*) FROM step")
    int getCount();

    @Insert(onConflict = REPLACE)
    void insertAll(List<Step> steps);

    @Query("DELETE FROM step")
    void deleteAll();
}
