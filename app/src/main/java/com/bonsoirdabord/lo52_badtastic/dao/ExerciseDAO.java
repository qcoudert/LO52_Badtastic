package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;

import java.util.List;

@Dao
public interface ExerciseDAO {
    @Query("SELECT * FROM " + Exercise.TABLE_NAME)
    LiveData<List<Exercise>> getAllExercise();

    @Query("DELETE FROM " + Exercise.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Exercise exercise);
}
