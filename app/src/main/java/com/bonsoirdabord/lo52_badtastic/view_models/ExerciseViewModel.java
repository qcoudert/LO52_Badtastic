package com.bonsoirdabord.lo52_badtastic.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.dao.ExerciseDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseDAO exerciseDAO;
    private LiveData<List<Exercise>> exerciseLiveData;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseDAO = ExerciseDatabase.getInstance(application).exerciseDAO();
        exerciseLiveData = exerciseDAO.getAllExercise();
    }

    public LiveData<List<Exercise>> getAllExercise(){return exerciseLiveData;}

    public void deleteAll(){exerciseDAO.deleteAll();}

    public long insert(Exercise exercise){return exerciseDAO.insert(exercise);}
}
