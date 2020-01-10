package com.bonsoirdabord.lo52_badtastic.dao;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ExerciseDAO {
    @Query("SELECT * FROM " + Exercise.TABLE_NAME)
    public abstract List<Exercise> getAllExercise();

    @Query("SELECT * FROM " + Exercise.TABLE_NAME + " WHERE id = :id")
    public abstract Exercise getExercise(int id);

    @Query("DELETE FROM " + Exercise.TABLE_NAME + " WHERE id = :id")
    public abstract void deleteExercise(int id);

    @Query("DELETE FROM " + Exercise.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(Exercise... exercises);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Exercise exercise);

    public Exercise getExerciseCompleted(int id, ExerciseDatabase database){
        Exercise exercise = getExercise(id);
        exercise.setThemes(database.themeDAO()
                .getThemeForExercise(id, database));
        return exercise;
    }

    public List<Exercise> getAllExerciseCompleted(ExerciseDatabase database){
        List<Exercise> exercises = getAllExercise();
        for (Exercise exercise : exercises) {
            exercise.setThemes(database.themeDAO()
                    .getThemeForExercise(exercise.getId(), database));
        }
        return exercises;
    }
}
