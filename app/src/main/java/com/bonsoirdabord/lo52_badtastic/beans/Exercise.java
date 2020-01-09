package com.bonsoirdabord.lo52_badtastic.beans;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Exercise.TABLE_NAME)
public class Exercise {
    @Ignore
    public static final String TABLE_NAME = "exercise";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String descriptino;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    /**
     * Type de public adapté à l'exercice.
     * 0 -> Débutant
     * 1 -> Confirmé
     * 2 -> Les deux
     */
    @ColumnInfo(name = "public_type")
    private int publicType;

    @ColumnInfo(name = "picture_path")
    private String picturePath;

    @ColumnInfo(name = "duration")
    private double duration;

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    @Ignore
    private List<Theme> themes;

    public Exercise(String name, String descriptino, int difficulty, int publicType, String picturePath, double duration, boolean isFavourite) {
        this.name = name;
        this.descriptino = descriptino;
        this.difficulty = difficulty;
        this.publicType = publicType;
        this.picturePath = picturePath;
        this.duration = duration;
        this.isFavourite = isFavourite;
    }

    public Exercise() {
        this.name = null;
        this.descriptino = null;
        this.difficulty = -1;
        this.publicType = 1;
        this.picturePath = null;
        this.duration = -1;
        this.isFavourite = false;
        this.themes = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptino() {
        return descriptino;
    }

    public void setDescriptino(String descriptino) {
        this.descriptino = descriptino;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPublicType() {
        return publicType;
    }

    public void setPublicType(int publicType) {
        this.publicType = publicType;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
}
