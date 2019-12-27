package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.dao.SessionDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class SessionViewModel extends AndroidViewModel {
    private SessionDAO sessionDAO;
    private LiveData<List<Session>> sessionLiveData;

    public SessionViewModel(@NonNull Application application) {
        super(application);
        sessionDAO = ExerciseDatabase.getInstance(application).sessionDAO();
        sessionLiveData = sessionDAO.getAllSession();
    }

    public LiveData<List<Session>> getAllSession(){
        return sessionDAO.getAllSession();
    }

    public void deleteAll(){sessionDAO.deleteAll();}

    public long insert(Session session){return sessionDAO.insert(session);}
}
