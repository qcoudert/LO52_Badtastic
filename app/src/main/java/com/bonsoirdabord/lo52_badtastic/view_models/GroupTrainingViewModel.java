package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.dao.GroupTrainingDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class GroupTrainingViewModel extends AndroidViewModel {
    private GroupTrainingDAO groupTrainingDAO;
    private LiveData<List<GroupTraining>> groupTrainingLiveData;

    public GroupTrainingViewModel(@NonNull Application application) {
        super(application);
        groupTrainingDAO = ExerciseDatabase.getInstance(application).groupTrainingDAO();
        groupTrainingLiveData = groupTrainingDAO.getAllGroupTraining();
    }

    public LiveData<List<GroupTraining>> getAllGroupTraining(){return groupTrainingDAO.getAllGroupTraining();}

    public void deleteAll(){groupTrainingDAO.deleteAll();}

    public long insert(GroupTraining groupTraining){return groupTrainingDAO.insert(groupTraining);}

}
