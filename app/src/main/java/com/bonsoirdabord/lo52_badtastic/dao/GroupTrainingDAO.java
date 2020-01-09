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
    public abstract List<GroupTraining> getAllGroupTraining();

    @Query("SELECT * FROM " + GroupTraining.TABLE_NAME + " WHERE session_id = :sessionId")
    public abstract List<GroupTraining> getGroupTrainingForSession(final int sessionId);

    @Query("DELETE FROM " + GroupTraining.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(GroupTraining... groupTrainings);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(GroupTraining groupTraining);

    public List<GroupTraining> getGroupTrainingForSessionCompleted(final int sessionId, final ExerciseDatabase database){
        List<GroupTraining> groupTrainings = getGroupTrainingForSession(sessionId);
        for (GroupTraining groupTraining : groupTrainings) {
            groupTraining.setThemes(database.themeDAO()
                    .getThemeForGroupTraining(groupTraining.getId(), database));
            groupTraining.setExerciseSets(database.exerciseSetDAO()
                    .getExerciseSetForGroupTrainingCompleted(groupTraining.getId(), database));
        }
        return groupTrainings;
    }
}
