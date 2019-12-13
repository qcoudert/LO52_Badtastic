package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.Session;

import java.util.List;

@Dao
public interface SessionDAO {
    @Query("SELECT * FROM " + Session.TABLE_NAME)
    LiveData<List<Session>> getAllSession();

    @Query("DELETE FROM " + Session.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Session session);
}
