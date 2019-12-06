package com.bonsoirdabord.lo52_badtastic.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = ScheduledSessionSessionLink.TABLE_NAME, foreignKeys = {
        @ForeignKey(entity = Session.class, parentColumns = "id", childColumns = "session_id",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = ScheduledSession.class, parentColumns = "id", childColumns = "scheduled_session_id",
                onDelete = ForeignKey.CASCADE)
})
public class ScheduledSessionSessionLink {
    @Ignore
    public static final String TABLE_NAME = "session_scheduled_session_link";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "session_id")
    private int sessionId;

    @ColumnInfo(name = "scheduled_session_id")
    private int scheduledSessionId;

    public ScheduledSessionSessionLink(int sessionId, int scheduledSessionId) {
        this.sessionId = sessionId;
        this.scheduledSessionId = scheduledSessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getScheduledSessionId() {
        return scheduledSessionId;
    }

    public void setScheduledSessionId(int scheduledSessionId) {
        this.scheduledSessionId = scheduledSessionId;
    }
}
