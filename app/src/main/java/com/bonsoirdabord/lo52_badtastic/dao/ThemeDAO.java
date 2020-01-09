package com.bonsoirdabord.lo52_badtastic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.beans.ThemeLink;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ThemeDAO {
    @Query("SELECT * FROM " + Theme.TABLE_NAME)
    public abstract List<Theme> getAllTheme();

    @Query("SELECT * FROM " + Theme.TABLE_NAME + " WHERE id = :id")
    public abstract Theme getTheme(int id);

    @Query("SELECT * FROM " + Theme.TABLE_NAME + " WHERE id IN (:ids)")
    public abstract List<Theme> getThemes(List<Integer> ids);

    @Query("DELETE FROM " + Theme.TABLE_NAME)
    public abstract void deleteAll();

    @Update
    public abstract void update(Theme... themes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Theme theme);

    public List<Theme> getThemeForGroupTraining(int groupTrainingId, ExerciseDatabase database){
        List<ThemeLink> themeLinks = database.themeLinkDAO().getThemeLinkForGroupTraining(groupTrainingId);
        List<Integer> themes = new ArrayList<>();
        for (ThemeLink themeLink : themeLinks) {
            themes.add(themeLink.themeId);
        }
        return getThemes(themes);
    }

    public List<Theme> getThemeForExercise(int exerciseId, ExerciseDatabase database){
        List<ThemeLink> themeLinks = database.themeLinkDAO().getThemeLinkForExercise(exerciseId);
        List<Integer> themes = new ArrayList<>();
        for (ThemeLink themeLink : themeLinks) {
            themes.add(themeLink.themeId);
        }
        return getThemes(themes);
    }
}
