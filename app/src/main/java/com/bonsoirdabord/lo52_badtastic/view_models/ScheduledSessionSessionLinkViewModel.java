package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSessionSessionLink;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionDAO;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionSessionLinkDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class ScheduledSessionSessionLinkViewModel extends AndroidViewModel {
    ScheduledSessionSessionLinkDAO scheduledSessionSessionLinkDAO;
    LiveData<List<ScheduledSessionSessionLink>> scheduledSessionSessionLinkLiveData;

    public ScheduledSessionSessionLinkViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ScheduledSessionSessionLink>> getAllScheduledSession(){return scheduledSessionSessionLinkDAO.getAllScheduledSessionSessionLink();}

    public void deleteAll(){scheduledSessionSessionLinkDAO.deleteAll();}

    public long insert(ScheduledSessionSessionLink scheduledSessionSessionLink){return scheduledSessionSessionLinkDAO.insert(scheduledSessionSessionLink);}

}
