package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public Session(boolean isFavourite, int numberOfGroup) {
        this.isFavourite = isFavourite;
        this.numberOfGroup = numberOfGroup;
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
}
