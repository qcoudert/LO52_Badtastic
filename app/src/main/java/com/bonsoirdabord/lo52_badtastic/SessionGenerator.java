package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SessionGenerator {
    // Si la durée d’une répétition est inférieure à une minute, alors le nombre de répétition est environ égal à 8.
    // Si la durée d’une répétition est supérieure à deux minutes, alors le nombre de répétition est environ égal à 5 répétitions.
    // Le temps total de la séance soit modifié en conséquence et soit clairement affiché à l’utilisateur.

    private static final int EXERCISE_TIME_MAX = 120;
    private static final int EXERCISE_TIME_MIN = 60;
    private static final int EXERCISE_TIME_MAX_REPS = 5;
    private static final int EXERCISE_TIME_MIN_REPS = 8;
    private static final int VARIATION_TIME = 1;

    /***
     *  Get a Session with GroupTraining list and generate the exercise sets with parameter of the GroupTrainings.
     * @param context : Context of the app for database
     * @param session : Session with GroupTraining
     */
    public static void generateExerciseSetForSession(Context context, Session session){
        List<Exercise> exercises = ExerciseDatabase.getInstance(context).exerciseDAO()
                .getAllExerciseCompleted(ExerciseDatabase.getInstance(context));

        Map<Integer,List<Exercise>> compatibleExerciseMap = getCompatibleExerciseMap(exercises, session);

        for (GroupTraining groupTraining : session.getGroupTrainings()) {
            List<Exercise> compatibleExercises = compatibleExerciseMap.get(groupTraining.getId());
            int time = 0; // time of the actual session
            int i = 0;
            Collections.shuffle(compatibleExercises, new Random());
            while(time < session.getSessionTime()){
                Exercise exercise = compatibleExercises.get(i);
                int reps = 0;
                if(exercise.getDuration() > EXERCISE_TIME_MAX){
                    reps = (int)(Math.random()*VARIATION_TIME*2)+EXERCISE_TIME_MAX_REPS-VARIATION_TIME;
                } else if(exercise.getDuration() < EXERCISE_TIME_MIN){
                    reps = (int)(Math.random()*VARIATION_TIME*2)+EXERCISE_TIME_MIN_REPS-VARIATION_TIME;
                } else{
                    reps = (int)(Math.random()*VARIATION_TIME*2)+(EXERCISE_TIME_MIN_REPS+EXERCISE_TIME_MAX_REPS)/2-VARIATION_TIME;
                }

                ExerciseSet exerciseSet = new ExerciseSet(i, reps, reps*exercise.getDuration(), 0, exercise.getId(), groupTraining.getId());
                exerciseSet.setExercise(exercise);
                groupTraining.getExerciseSets().add(exerciseSet);
            }
        }
    }

    private static Map<Integer,List<Exercise>> getCompatibleExerciseMap(List<Exercise> exercises, Session session){
        Map<Integer,List<Exercise>> compatibleExerciseMap = new HashMap<>();

        for (GroupTraining groupTraining : session.getGroupTrainings()) {
            List<Exercise> compatibles = new ArrayList<>();
            for (Exercise exercise : exercises) {
                Boolean test = false;
                for (final Theme theme : groupTraining.getThemes()) {
                    for (Theme exerciseTheme : exercise.getThemes()) {
                        if(theme.equals(exerciseTheme)){
                            compatibles.add(exercise);
                            test = true;
                            break;
                        }
                    }
                    if (test)
                        break;
                }
            }
            compatibleExerciseMap.put(groupTraining.getId(), compatibles);
        }
        return compatibleExerciseMap;
    }
}
