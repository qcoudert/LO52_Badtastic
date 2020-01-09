package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

public class SessionGenerator {

    /***
     *  Get a Session with GroupTraining list and generate the exercise sets with parameter of the GroupTrainings.
     * @param context : Context of the app for database
     * @param session : Session with GroupTraining
     */
    public void generateSession(Context context, Session session){
        List<Exercise> exercises = ExerciseDatabase.getInstance(context).exerciseDAO().getExerciseCompleted();
    }
}
