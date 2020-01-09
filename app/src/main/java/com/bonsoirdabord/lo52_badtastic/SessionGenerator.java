package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionGenerator {

    /***
     *  Get a Session with GroupTraining list and generate the exercise sets with parameter of the GroupTrainings.
     * @param context : Context of the app for database
     * @param session : Session with GroupTraining
     */
    public static void generateSession(Context context, Session session){
        List<Exercise> exercises = ExerciseDatabase.getInstance(context).exerciseDAO()
                .getAllExerciseCompleted(ExerciseDatabase.getInstance(context)).getValue();

        Map<Integer,List<Exercise>> compatibleExercises = getCompatibleExercises(exercises, session);

    }

    private static Map<Integer,List<Exercise>> getCompatibleExercises(List<Exercise> exercises, Session session){
        Map<Integer,List<Exercise>> compatibleExercises = new HashMap<>();

        for (GroupTraining groupTraining : session.getGroupTrainings()) {
            List<Exercise> compatibles = new ArrayList<>();
            for (Exercise exercise : exercises) {
                for (final Theme theme : groupTraining.getThemes()) {
                    for (Theme exerciseTheme : exercise.getThemes()) {
                        if(theme.equals(exerciseTheme)){
                            compatibles.add(exercise);
                            break;
                        }
                    }
                }
            }
            compatibleExercises.put(groupTraining.getId(), compatibles);
        }

        return compatibleExercises;
    }
}
