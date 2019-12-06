package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;

import java.util.List;

@Dao
public interface ScheduledSessionDAO {
    @Query("SELECT * FROM " + ScheduledSession.TABLE_NAME)
    LiveData<List<ScheduledSession>> getAllScheduledSession();

    @Query("DELETE FROM " + ScheduledSession.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ScheduledSession scheduledSession);
}
