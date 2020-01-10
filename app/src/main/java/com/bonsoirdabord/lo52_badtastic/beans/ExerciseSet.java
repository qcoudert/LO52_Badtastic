package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ExerciseSet.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Exercise.class, parentColumns = "id", childColumns = "exercise_id",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = GroupTraining.class, parentColumns = "id", childColumns = "group_training_id",
        onDelete = ForeignKey.CASCADE)})
public class ExerciseSet {
    @Ignore
    public static final String TABLE_NAME = "exercise_set";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "order")
    private int order;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "duration")
    private double duration;

    @ColumnInfo(name = "intensity")
    private int intensity;

    @ColumnInfo(name = "exercise_id")
    private int exerciseId;

    @ColumnInfo(name = "group_training_id")
    private int groupTrainingId;

    @Ignore
    private Exercise exercise;

    public ExerciseSet(int order, int reps, double duration, int intensity, int exerciseId, int groupTrainingId) {
        this.order = order;
        this.reps = reps;
        this.duration = duration;
        this.intensity = intensity;
        this.exerciseId = exerciseId;
        this.groupTrainingId = groupTrainingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getGroupTrainingId() {
        return groupTrainingId;
    }

    public void setGroupTrainingId(int groupTrainingId) {
        this.groupTrainingId = groupTrainingId;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
