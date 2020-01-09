package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Theme.TABLE_NAME)
public class Theme {
    public static final String TABLE_NAME = "theme";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public int name;

    public Theme(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
