package com.bonsoirdabord.lo52_badtastic.beans;

import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = GroupTraining.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Session.class, parentColumns = "id", childColumns = "session_id",
                onDelete = ForeignKey.CASCADE)
})
public class GroupTraining {
    @Ignore
    public static final String TABLE_NAME = "group_training";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "public_target")
    private int publicTarget;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    @ColumnInfo(name = "session_id")
    private int sessionId;

    @Ignore
    private List<ExerciseSet> exerciseSets;

    @Ignore
    private List<Theme> themes;


    public GroupTraining(int publicTarget, int difficulty, int sessionId) {
        this.publicTarget = publicTarget;
        this.difficulty = difficulty;
        this.sessionId = sessionId;
        exerciseSets = new ArrayList<>();
    }

    public GroupTraining() {
        this.publicTarget = 0;
        this.difficulty = -1;
        this.exerciseSets = new ArrayList<>();
        this.themes = new ArrayList<>();
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicTarget() {
        return publicTarget;
    }

    public void setPublicTarget(int publicTarget) {
        this.publicTarget = publicTarget;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public void setExerciseSets(List<ExerciseSet> exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
}
