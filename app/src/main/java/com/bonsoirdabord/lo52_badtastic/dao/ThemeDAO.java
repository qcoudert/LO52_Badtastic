package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.Theme;

import java.util.List;

@Dao
public interface ThemeDAO {
    @Query("SELECT * FROM " + Theme.TABLE_NAME)
    LiveData<List<Theme>> getAllTheme();

    @Query("DELETE FROM " + Theme.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Theme theme);

}
