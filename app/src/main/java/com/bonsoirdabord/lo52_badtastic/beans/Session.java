package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Session.TABLE_NAME)
public class Session {
    @Ignore
    public static final String TABLE_NAME = "session";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name= "name")
    private String name;

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    @ColumnInfo(name = "number_of_group")
    private int numberOfGroup;

    @Ignore
    private List<GroupTraining> groupTrainings;

    public Session(String name, boolean isFavourite, int numberOfGroup) {
        this.name = name;
        this.isFavourite = isFavourite;
        this.numberOfGroup = numberOfGroup;
        this.groupTrainings = new ArrayList<>();
    }

    public Session() {
        this.name = null;
        this.isFavourite = false;
        this.numberOfGroup = 0;
        this.groupTrainings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    public void setNumberOfGroup(int numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    public List<GroupTraining> getGroupTrainings() {
        return groupTrainings;
    }

    public void setGroupTrainings(List<GroupTraining> groupTrainings) {
        this.groupTrainings = groupTrainings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
