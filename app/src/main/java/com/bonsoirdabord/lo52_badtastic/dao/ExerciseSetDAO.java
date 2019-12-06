package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;

import java.util.List;

@Dao
public interface ExerciseSetDAO {
    @Query("SELECT * FROM " + ExerciseSet.TABLE_NAME)
    LiveData<List<ExerciseSet>> getAllExerciseSet();

    @Query("DELETE FROM " + ExerciseSet.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ExerciseSet exerciseSet);
}
