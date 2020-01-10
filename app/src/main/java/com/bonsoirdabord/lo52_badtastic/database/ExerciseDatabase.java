package com.bonsoirdabord.lo52_badtastic.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.beans.ThemeLink;
import com.bonsoirdabord.lo52_badtastic.dao.ExerciseDAO;
import com.bonsoirdabord.lo52_badtastic.dao.ExerciseSetDAO;
import com.bonsoirdabord.lo52_badtastic.dao.GroupTrainingDAO;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionDAO;
import com.bonsoirdabord.lo52_badtastic.dao.SessionDAO;
import com.bonsoirdabord.lo52_badtastic.dao.ThemeDAO;
import com.bonsoirdabord.lo52_badtastic.dao.ThemeLinkDAO;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Exercise.class, ExerciseSet.class, GroupTraining.class, ScheduledSession.class,
        Session.class, Theme.class, ThemeLink.class}, exportSchema = false, version = 6)
public abstract class ExerciseDatabase extends RoomDatabase {
    public static final String DB_NAME = "exercise_badtastic.db";
    private static ExerciseDatabase instance;

    public static final synchronized ExerciseDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, DB_NAME)
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            Log.d("ExerciseDatabase", "Populating with data...");
                            new PopulateDbAsync(instance);
                        }
                    })
                    .allowMainThreadQueries() // bad, very bad
                    .build();
        }
        return instance;
    }

    public void clearDb() {
        if (instance != null) {
            new PopulateDbAsync(instance).execute();
        }
    }

    public abstract ExerciseDAO exerciseDAO();

    public abstract ExerciseSetDAO exerciseSetDAO();

    public abstract GroupTrainingDAO groupTrainingDAO();

    public abstract ScheduledSessionDAO scheduledSessionDAO();

    public abstract SessionDAO sessionDAO();

    public abstract ThemeDAO themeDAO();

    public abstract ThemeLinkDAO themeLinkDAO();


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        public PopulateDbAsync(ExerciseDatabase instance){

        }

        @Override
        public Void doInBackground(Void... voids){
            return null;
        }
    }
}
