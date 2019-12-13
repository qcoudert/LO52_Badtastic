package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class ScheduledSessionViewModel extends AndroidViewModel {
    private ScheduledSessionDAO scheduledSessionDAO;
    private LiveData<List<ScheduledSession>> scheduledSessionLiveData;

    public ScheduledSessionViewModel(@NonNull Application application) {
        super(application);
        scheduledSessionDAO = ExerciseDatabase.getInstance(application).scheduledSessionDAO();
        scheduledSessionLiveData = scheduledSessionDAO.getAllScheduledSession();
    }

    public LiveData<List<ScheduledSession>> getAllScheduledSession(){return scheduledSessionDAO.getAllScheduledSession();}

    public void deleteAll(){scheduledSessionDAO.deleteAll();}

    public long insert(ScheduledSession scheduledSession){return scheduledSessionDAO.insert(scheduledSession);}


}
