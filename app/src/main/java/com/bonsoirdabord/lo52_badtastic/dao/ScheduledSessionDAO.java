package com.bonsoirdabord.lo52_badtastic.dao;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScheduledSessionDAO {
    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME)
    List<ScheduledSession> getAllScheduledSession();

    @Query("DELETE FROM " + ScheduledSession.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ScheduledSession scheduledSession);

    @Update
    void update(ScheduledSession ss);

    @Delete
    void delete(ScheduledSession ss);
}
