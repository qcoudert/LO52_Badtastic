package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = ThemeLink.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Exercise.class, parentColumns = "id", childColumns = "exercise_id",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = GroupTraining.class, parentColumns = "id", childColumns = "groupe_training_id",
                onDelete = ForeignKey.CASCADE)
})
public class ThemeLink {
    public static final String TABLE_NAME = "theme_link";

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "is_exercise")
    boolean isExerciseLink;

    @ColumnInfo(name = "exercise_id")
    int exerciseId;

    @ColumnInfo(name = "groupe_training_id")
    int groupeTrainingId;

    public ThemeLink(boolean isExerciseLink, int exerciseId, int groupeTrainingId) {
        this.isExerciseLink = isExerciseLink;
        this.exerciseId = exerciseId;
        this.groupeTrainingId = groupeTrainingId;
    }

    public boolean isExerciseLink() {
        return isExerciseLink;
    }

    public void setExerciseLink(boolean exerciseLink) {
        isExerciseLink = exerciseLink;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getGroupeTrainingId() {
        return groupeTrainingId;
    }

    public void setGroupeTrainingId(int groupeTrainingId) {
        this.groupeTrainingId = groupeTrainingId;
    }
}
