package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.dao.ExerciseSetDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class ExerciseSetViewModel extends AndroidViewModel {
    private ExerciseSetDAO exerciseSetDAO;
    private LiveData<List<ExerciseSet>> exerciseSetLiveData;

    public ExerciseSetViewModel(@NonNull Application application) {
        super(application);
        exerciseSetDAO = ExerciseDatabase.getInstance(application).exerciseSetDAO();
        exerciseSetLiveData = exerciseSetDAO.getAllExerciseSet();
    }

    public LiveData<List<ExerciseSet>> getAllExerciseSet(){return exerciseSetLiveData;}

    public void deleteAll(){exerciseSetDAO.deleteAll();}

    public long insert(ExerciseSet exercise){return exerciseSetDAO.insert(exercise);}
}
