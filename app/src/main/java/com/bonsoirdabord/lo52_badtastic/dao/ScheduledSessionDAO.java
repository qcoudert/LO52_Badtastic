package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

@Dao
public abstract class ScheduledSessionDAO {
    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME)
    public abstract LiveData<List<ScheduledSession>> getAllScheduledSession();

    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME + " WHERE id =:id")
    public abstract LiveData<ScheduledSession> getScheduledSession(int id);

    @Query("DELETE FROM " + ScheduledSession.TABLE_NAME)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ScheduledSession scheduledSession);

    public LiveData<ScheduledSession> getScheduledSessionCompleted(int id, ExerciseDatabase database){
        LiveData<ScheduledSession> scheduledSessionLiveData = getScheduledSession(id);
        Session session = database.sessionDAO().getSessionCompleted(id, database).getValue();
        scheduledSessionLiveData.getValue().setSession(session);
        return scheduledSessionLiveData;
    }
}
