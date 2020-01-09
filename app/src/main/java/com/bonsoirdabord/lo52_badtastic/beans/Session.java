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

    @ColumnInfo(name = "is_favourite")
    private boolean isFavourite;

    @ColumnInfo(name = "number_of_group")
    private int numberOfGroup;

    @ColumnInfo(name = "session_time")
    private int session_time;

    @Ignore
    private List<GroupTraining> groupTrainings;

    public Session(boolean isFavourite, int numberOfGroup, int session_time) {
        this.isFavourite = isFavourite;
        this.numberOfGroup = numberOfGroup;
        this.session_time = session_time;
        groupTrainings = new ArrayList<>();
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

    public int getSession_time() {
        return session_time;
    }

    public void setSession_time(int session_time) {
        this.session_time = session_time;
    }
}
