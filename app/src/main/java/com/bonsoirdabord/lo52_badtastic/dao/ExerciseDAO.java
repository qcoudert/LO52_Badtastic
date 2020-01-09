package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

@Dao
public abstract class ExerciseDAO {
    @Query("SELECT * FROM " + Exercise.TABLE_NAME)
    public abstract LiveData<List<Exercise>> getAllExercise();

    @Query("SELECT * FROM " + Exercise.TABLE_NAME + " WHERE id = :id")
    public abstract LiveData<Exercise> getExercise(int id);

    @Query("DELETE FROM " + Exercise.TABLE_NAME)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Exercise exercise);

    public LiveData<Exercise> getExerciseCompleted(int id, ExerciseDatabase database){
        LiveData<Exercise> exerciseLiveData = getExercise(id);
        exerciseLiveData.getValue().setThemes(database.themeDAO()
                .getThemeForExercise(id, database).getValue());
        return exerciseLiveData;
    }
}
