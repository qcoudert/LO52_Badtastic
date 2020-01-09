package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.Comparator;
import java.util.List;

@Dao
public abstract class ExerciseSetDAO {
    @Query("SELECT * FROM " + ExerciseSet.TABLE_NAME)
    public abstract LiveData<List<ExerciseSet>> getAllExerciseSet();

    @Query("SELECT * FROM " + ExerciseSet.TABLE_NAME + " WHERE group_training_id =:groupTrainingId")
    public abstract LiveData<List<ExerciseSet>> getExerciseSetForGroupTraining(int groupTrainingId);

    @Query("DELETE FROM " + ExerciseSet.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(ExerciseSet... exerciseSets);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ExerciseSet exerciseSet);

    public LiveData<List<ExerciseSet>> getExerciseSetForGroupTrainingCompleted(int groupTrainingId, ExerciseDatabase database){
        LiveData<List<ExerciseSet>> exerciseSetLiveData = getExerciseSetForGroupTraining(groupTrainingId);
        for (ExerciseSet exerciseSet : exerciseSetLiveData.getValue()) {
            exerciseSet.setExercise(database.exerciseDAO()
                    .getExerciseCompleted(exerciseSet.getExerciseId(), database).getValue());
        }
        exerciseSetLiveData.getValue().sort(new Comparator<ExerciseSet>() {
            @Override
            public int compare(ExerciseSet o1, ExerciseSet o2) {
                return (Integer.compare(o1.getOrder(), o2.getOrder()));
            }
        });
        return exerciseSetLiveData;
    }


}
