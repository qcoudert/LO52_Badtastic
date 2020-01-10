package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionGenerator {
    private static final int EXERCISE_TIME_MAX = 120;
    private static final int EXERCISE_TIME_MIN = 60;
    private static final int EXERCISE_TIME_MAX_REPS = 5;
    private static final int EXERCISE_TIME_MIN_REPS = 8;
    private static final int VARIATION_TIME = 1;

    private static final float PUBLIC_MISMATCH_SCORE = 4.0f; //Constant to add if the target public doesn't match
    private static final float DIFFICULTY_MISMATCH_SCORE_MULTPLIER = 0.5f; //Value multiplied by the difference of difficulty
    private static final float TIME_SCORE_EXP = 5.0f; //Formula is 1-exp(-relativeTimeError * TIME_SCORE_EXP)

    /***
     *  Get a Session with GroupTraining list and generate the exercise sets with parameter of the GroupTrainings.
     * @param context : Context of the app for database
     * @param session : Session with GroupTraining
     */
    public static void generateExerciseSetForSession(Context context, Session session){
        List<Exercise> exercises = ExerciseDatabase.getInstance(context).exerciseDAO()
                .getAllExerciseCompleted(ExerciseDatabase.getInstance(context));


        for (GroupTraining groupTraining : session.getGroupTrainings()) {
            List<Exercise> compatibleExercises = getCompatibleExercises(exercises, groupTraining);
            int time = 0; // time of the actual session
            int i = 0;
            while(time < session.getSessionTime() && i < compatibleExercises.size()){
                Exercise exercise = compatibleExercises.get(i);
                int reps = 0;
                if(exercise.getDuration() > EXERCISE_TIME_MAX){
                    reps = (int)(Math.random() * VARIATION_TIME * 2) + EXERCISE_TIME_MAX_REPS - VARIATION_TIME;
                } else if(exercise.getDuration() < EXERCISE_TIME_MIN){
                    reps = (int)(Math.random() * VARIATION_TIME * 2) + EXERCISE_TIME_MIN_REPS - VARIATION_TIME;
                } else{
                    reps = (int)(Math.random() * VARIATION_TIME * 2) + (EXERCISE_TIME_MIN_REPS + EXERCISE_TIME_MAX_REPS) / 2 - VARIATION_TIME;
                }

                time += reps * exercise.getDuration();

                ExerciseSet exerciseSet = new ExerciseSet(i, reps, reps*exercise.getDuration(), 0, exercise.getId(), groupTraining.getId());
                exerciseSet.setExercise(exercise);
                groupTraining.getExerciseSets().add(exerciseSet);

                i = i + 1 % compatibleExercises.size();
            }
        }
    }

    /***
     * Get the list of exercise sorted by compatibility for a group training
     * @param exercises
     * @param groupTraining
     * @return A
     */
    private static List<Exercise> getCompatibleExercises(List<Exercise> exercises, GroupTraining groupTraining){
        List<Exercise> compatibles = new ArrayList<>();
        compatibles.addAll(exercises);
        exercises.sort(new Comparator<Exercise>() {
            @Override
            public int compare(Exercise e1, Exercise e2) {
                if(getExerciseCompatibilityScore(e1, groupTraining) > getExerciseCompatibilityScore(e2, groupTraining))
                    return -1;
                else if(getExerciseCompatibilityScore(e1, groupTraining) < getExerciseCompatibilityScore(e2, groupTraining))
                    return 1;
                else
                    return 0;
            }
        });
        return compatibles;
    }

    /***
     * Get the Compatibility score of an exercise due to :
     * 1. type of public
     * 2. theme
     * 3. difficulty
     * @param exercise
     * @param groupTraining
     * @return the score between 0 and 1;
     */
    private static float getExerciseCompatibilityScore(Exercise exercise, GroupTraining groupTraining){
        if(groupTraining.getPublicTarget() == 0 && exercise.getPublicType() == 1
                || groupTraining.getPublicTarget() == 1 && exercise.getPublicType() == 0)
            return 0;

        float score = 0;
        float theme_score = 0.9f;
        for (Theme exerciseTheme : exercise.getThemes()) {
            for (final Theme theme : groupTraining.getThemes()) {
                if(theme.equals(exerciseTheme)){
                    score = score + theme_score / groupTraining.getThemes().size();
                    break;
                }
            }
        }

        float difficulty_score = 1-0.9f;
        score = score + difficulty_score / (Math.abs(exercise.getDifficulty() - groupTraining.getDifficulty())+1);

        return score;
    }



    private static float exp(float x) {
        return (float) Math.exp((double) x);
    }

    private static float computeGroupTrainingDiscrepanciesScore(Session sess, GroupTraining gt) {
        float totalTime = 0.0f;
        float mismatchScore = 0.0f;
        List<ExerciseSet> exercises = gt.getExerciseSets();

        for(ExerciseSet es : exercises) {
            Exercise e = es.getExercise();

            totalTime += (float) e.getDuration();
            mismatchScore += ((float) Math.abs(gt.getDifficulty() - e.getDifficulty())) * DIFFICULTY_MISMATCH_SCORE_MULTPLIER;

            if(gt.getPublicTarget() != 2 && e.getPublicType() != 2 && gt.getPublicTarget() != e.getPublicType())
                mismatchScore += PUBLIC_MISMATCH_SCORE;
        }

        float worstCaseScenario = (4.0f * DIFFICULTY_MISMATCH_SCORE_MULTPLIER + PUBLIC_MISMATCH_SCORE) * ((float) exercises.size());
        mismatchScore /= worstCaseScenario; //Normalize the mistmatch score (value between 0.0 and 1.0)

        float relativeTimeError = Math.abs(totalTime - (float) sess.getSessionTime()) / ((float) sess.getSessionTime());
        float timeScore = 1.0f - exp(-relativeTimeError * TIME_SCORE_EXP);

        return Math.max(mismatchScore, timeScore);
    }

    /**
     * Computes the correlation score between the generation parameters provided by the user
     * and the generated session.
     *
     * This returned value is between 0.0 and 1.0, where 0.0 means
     * that the session doesn't match the requested parameters at all and 1.0 means that the
     * session perfectly matches the user's requirements.
     *
     * @param sess The session we want the correlation score from
     * @return The correlation score, between 0.0 and 1.0 (0.0 = no match, 1.0 = perfect match)
     */
    public static float computeSessionCorrelationScore(Session sess) {
        float maxErrScore = 0.0f;
        for(GroupTraining gt : sess.getGroupTrainings())
            maxErrScore = Math.max(maxErrScore, computeGroupTrainingDiscrepanciesScore(sess, gt));

        return 1.0f - maxErrScore; //Convert discrepancies score to correlation score
    }
}
