package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.beans.ThemeLink;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ThemeDAO {
    @Query("SELECT * FROM " + Theme.TABLE_NAME)
    public abstract LiveData<List<Theme>> getAllTheme();

    @Query("SELECT * FROM " + Theme.TABLE_NAME + " WHERE id = :id")
    public abstract LiveData<Theme> getTheme(int id);

    @Query("SELECT * FROM " + Theme.TABLE_NAME + " WHERE id IN (:ids)")
    public abstract LiveData<List<Theme>> getThemes(List<Integer> ids);

    @Query("DELETE FROM " + Theme.TABLE_NAME)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Theme theme);

    public LiveData<List<Theme>> getThemeForGroupTraining(int groupTrainingId, ExerciseDatabase database){
        List<ThemeLink> themeLinks = database.themeLinkDAO().getThemeLinkForGroupTraining(groupTrainingId).getValue();
        List<Integer> themes = new ArrayList<>();
        for (ThemeLink themeLink : themeLinks) {
            themes.add(themeLink.themeId);
        }
        return getThemes(themes);
    }

    public LiveData<List<Theme>> getThemeForExercise(int exerciseId, ExerciseDatabase database){
        List<ThemeLink> themeLinks = database.themeLinkDAO().getThemeLinkForExercise(exerciseId).getValue();
        List<Integer> themes = new ArrayList<>();
        for (ThemeLink themeLink : themeLinks) {
            themes.add(themeLink.themeId);
        }
        return getThemes(themes);
    }
}
