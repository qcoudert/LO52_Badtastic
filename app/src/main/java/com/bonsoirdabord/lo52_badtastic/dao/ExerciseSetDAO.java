package com.bonsoirdabord.lo52_badtastic.dao;

import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.Comparator;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ExerciseSetDAO {
    @Query("SELECT * FROM " + ExerciseSet.TABLE_NAME)
    public abstract List<ExerciseSet> getAllExerciseSet();

    @Query("SELECT * FROM " + ExerciseSet.TABLE_NAME + " WHERE group_training_id =:groupTrainingId")
    public abstract List<ExerciseSet> getExerciseSetForGroupTraining(int groupTrainingId);

    @Query("DELETE FROM " + ExerciseSet.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(ExerciseSet... exerciseSets);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ExerciseSet exerciseSet);

    public List<ExerciseSet> getExerciseSetForGroupTrainingCompleted(int groupTrainingId, ExerciseDatabase database){
        List<ExerciseSet> exerciseSets = getExerciseSetForGroupTraining(groupTrainingId);
        for (ExerciseSet exerciseSet : exerciseSets) {
            exerciseSet.setExercise(database.exerciseDAO()
                    .getExerciseCompleted(exerciseSet.getExerciseId(), database));
        }
        exerciseSets.sort(new Comparator<ExerciseSet>() {
            @Override
            public int compare(ExerciseSet o1, ExerciseSet o2) {
                return (Integer.compare(o1.getOrder(), o2.getOrder()));
            }
        });
        return exerciseSets;
    }


}
