package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

@Dao
public abstract class GroupTrainingDAO {
    @Query("SELECT * FROM " + GroupTraining.TABLE_NAME)
    public abstract LiveData<List<GroupTraining>> getAllGroupTraining();

    @Query("SELECT * FROM " + GroupTraining.TABLE_NAME + " WHERE session_id = :sessionId")
    public abstract LiveData<List<GroupTraining>> getGroupTrainingForSession(final int sessionId);

    @Query("DELETE FROM " + GroupTraining.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(GroupTraining... groupTrainings);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(GroupTraining groupTraining);

    public LiveData<List<GroupTraining>> getGroupTrainingForSessionCompleted(final int sessionId, final ExerciseDatabase database){
        LiveData<List<GroupTraining>> groupTrainingsLive = getGroupTrainingForSession(sessionId);
        for (GroupTraining groupTraining : groupTrainingsLive.getValue()) {
            groupTraining.setThemes(database.themeDAO()
                    .getThemeForGroupTraining(groupTraining.getId(), database).getValue());
            groupTraining.setExerciseSets(database.exerciseSetDAO()
                    .getExerciseSetForGroupTrainingCompleted(groupTraining.getId(), database).getValue());
        }
        return groupTrainingsLive;
    }
}
