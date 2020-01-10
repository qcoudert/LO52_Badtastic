package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;

import com.bonsoirdabord.lo52_badtastic.beans.ExerciseSet;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

public class SessionUtils {

    public static ScheduledSession saveSession(Context context, Session session){

        ExerciseDatabase database = ExerciseDatabase.getInstance(context);

        session.setId((int)database.sessionDAO().insert(session));
        for (GroupTraining groupTraining : session.getGroupTrainings()) {
            groupTraining.setSessionId(session.getId());
            groupTraining.setId((int)database.groupTrainingDAO().insert(groupTraining));
            for (ExerciseSet exerciseSet : groupTraining.getExerciseSets()) {
                exerciseSet.setGroupTrainingId(groupTraining.getId());
                exerciseSet.setId((int) database.exerciseSetDAO().insert(exerciseSet));
            }
            for (Theme theme : groupTraining.getThemes()) {
                theme.setId((int)database.themeDAO().insertThemeByGroupTraining(theme, groupTraining, database));
            }
        }

        ScheduledSession scheduledSession = new ScheduledSession("",0, session.getId());
        scheduledSession.setId((int) database.scheduledSessionDAO().insert(scheduledSession));
        return scheduledSession;
    }
}
