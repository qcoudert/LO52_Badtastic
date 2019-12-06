package com.bonsoirdabord.lo52_badtastic.beans;

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
    public static final String TABLE_NAME = "groupe_training";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "public_target")
    private int publicTarget;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    @ColumnInfo(name = "session_id")
    private int sessionId;

    public GroupTraining(int publicTarget, int difficulty, int sessionId) {
        this.publicTarget = publicTarget;
        this.difficulty = difficulty;
        this.sessionId = sessionId;
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
}
