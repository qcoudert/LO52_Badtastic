package com.bonsoirdabord.lo52_badtastic.dao;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ScheduledSessionDAO {
    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME)
    public abstract List<ScheduledSession> getAllScheduledSession();

    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME + " WHERE id =:id")
    public abstract ScheduledSession getScheduledSession(int id);

    @Query("DELETE FROM " + ScheduledSession.TABLE_NAME + " WHERE id =:id")
    public abstract void delete(int id);

    @Query("DELETE FROM " + ScheduledSession.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(ScheduledSession... scheduledSessions);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(ScheduledSession scheduledSession);

    public ScheduledSession getScheduledSessionCompleted(int id, ExerciseDatabase database){
        ScheduledSession scheduledSession = getScheduledSession(id);
        Session session = database.sessionDAO().getSessionCompleted(id, database);
        scheduledSession.setSession(session);
        return scheduledSession;
    }
}
