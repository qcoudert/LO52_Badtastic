package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bonsoirdabord.lo52_badtastic.beans.ThemeLink;

import java.util.List;

@Dao
public interface ThemeLinkDAO {
    @Query("SELECT * FROM " + ThemeLink.TABLE_NAME)
    List<ThemeLink> getAllThemeLink();

    @Query("SELECT * FROM " + ThemeLink.TABLE_NAME + " WHERE group_training_id = :groupTrainingId")
    List<ThemeLink> getThemeLinkForGroupTraining(int groupTrainingId);

    @Query("SELECT * FROM " + ThemeLink.TABLE_NAME + " WHERE exercise_id = :exerciseId")
    List<ThemeLink> getThemeLinkForExercise(int exerciseId);

    @Query("DELETE FROM " + ThemeLink.TABLE_NAME)
    void deleteAll();

    @Update
    public abstract void update(ThemeLink... themeLinks);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ThemeLink themeLink);

}
