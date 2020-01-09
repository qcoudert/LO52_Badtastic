package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

@Dao
public abstract class SessionDAO {
    @Query("SELECT * FROM " + Session.TABLE_NAME)
    public abstract LiveData<List<Session>> getAllSession();

    @Query("SELECT * FROM " + Session.TABLE_NAME + " WHERE id = :id")
    public abstract LiveData<Session> getSession(int id);

    @Query("DELETE FROM " + Session.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(Session... sessions);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Session session);

    public LiveData<Session> getSessionCompleted(int id, ExerciseDatabase database){
        LiveData<Session> sessionLiveData = getSession(id);
        List<GroupTraining> groupTrainings = database.groupTrainingDAO()
                .getGroupTrainingForSessionCompleted(sessionLiveData.getValue().getId(), database)
                .getValue();
        return sessionLiveData;
    }

}
