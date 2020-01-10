package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = ThemeLink.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Exercise.class, parentColumns = "id", childColumns = "exercise_id",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = GroupTraining.class, parentColumns = "id", childColumns = "group_training_id",
                onDelete = ForeignKey.CASCADE)
})
public class ThemeLink {
    public static final String TABLE_NAME = "theme_link";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "theme_id")
    public int themeId;

    @ColumnInfo(name = "is_exercise")
    public boolean isExerciseLink;

    @ColumnInfo(name = "exercise_id")
    public Integer exerciseId;

    @ColumnInfo(name = "group_training_id")
    public Integer groupTrainingId;

    public ThemeLink(int themeId, boolean isExerciseLink, Integer exerciseId, Integer groupTrainingId) {
        this.themeId = themeId;
        this.isExerciseLink = isExerciseLink;
        this.exerciseId = exerciseId;
        this.groupTrainingId = groupTrainingId;
    }

    public boolean isExerciseLink() {
        return isExerciseLink;
    }

    public void setExerciseLink(boolean exerciseLink) {
        isExerciseLink = exerciseLink;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getGroupTrainingId() {
        return groupTrainingId;
    }

    public void setGroupTrainingId(Integer groupTrainingId) {
        this.groupTrainingId = groupTrainingId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }
}
