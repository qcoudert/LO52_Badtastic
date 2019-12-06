package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSessionSessionLink;

import java.util.List;

@Dao
public interface ScheduledSessionSessionLinkDAO {
    @Query("SELECT * FROM " + ScheduledSessionSessionLink.TABLE_NAME)
    LiveData<List<ScheduledSessionSessionLink>> getAllScheduledSessionSessionLink();

    @Query("DELETE FROM " + ScheduledSessionSessionLink.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ScheduledSessionSessionLink scheduledSessionSessionLink);

}
