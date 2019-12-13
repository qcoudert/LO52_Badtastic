package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.ThemeLink;

import java.util.List;

@Dao
public interface ThemeLinkDAO {
    @Query("SELECT * FROM " + ThemeLink.TABLE_NAME)
    LiveData<List<ThemeLink>> getAllThemeLink();

    @Query("DELETE FROM " + ThemeLink.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ThemeLink themeLink);
}
