package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ScheduledSession.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Session.class, parentColumns = "id", childColumns = "session_id",
                onDelete = ForeignKey.CASCADE)
})
public class ScheduledSession {
    @Ignore
    public static final String TABLE_NAME = "scheduled_session";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "session_id")
    private int sessionId;

    @Ignore
    private Session session;

    public ScheduledSession(String date, int hour, int sessionId) {
        this.date = date;
        this.hour = hour;
        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
