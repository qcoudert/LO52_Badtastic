package com.bonsoirdabord.lo52_badtastic;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SessionManagerFragment extends Fragment {
    private static boolean isFirstProcessing = false; // mutex for avoiding double adding of the third fragment while first and second fragments are changed
    private boolean mutex = false; // mutex for avoiding a click on the button and a time-out at the same time
    private int color;
    private int index;
    private int exerciceNbr;
    private int repetitionNbr;
    private int maxRepetitions;
    private long timeChrono;
    private Chronometer chrono;
    private ScheduledSession scheduledSession;
    private SessionActivity activity;

    public SessionManagerFragment(int color, int index, int exerciceNbr, int repetitionNbr, SessionActivity activity, ScheduledSession scheduledSession){
        super();
        this.color = color;
        this.index = index;
        this.exerciceNbr = exerciceNbr;
        this.repetitionNbr = repetitionNbr;
        this.scheduledSession = scheduledSession;
        this.timeChrono = 0;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_session_manager,
                container, false);
        view.setBackgroundColor(color);

        //getting the current Exercise and setting all informations
        //for(int i = 4; i<scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().size(); i++)  // TODO : REMOVE THOSE 2 LINES AFTER DEBUG
        //    scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().remove(i);
        Exercise exercise = scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().get(exerciceNbr - 1).getExercise();
        maxRepetitions = scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().get(exerciceNbr - 1).getReps();
        ((TextView)view.findViewById(R.id.textView)).setText(getString(R.string.grp_nbr) + " " + index);
        ((TextView)view.findViewById(R.id.textView7)).setText(getString(R.string.exercise_nbr) + " " + exerciceNbr +"/" + scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().size());
        ((TextView)view.findViewById(R.id.textView6)).setText(getString(R.string.exercise_name) + exercise.getName());
        ((TextView)view.findViewById(R.id.textView4)).setText(getString(R.string.exercise_rep) + repetitionNbr +"/" + maxRepetitions);
        ((TextView)view.findViewById(R.id.textView3)).setText(getString(R.string.exercise_desc) + exercise.getDescriptino());

        String themesText = getString(R.string.exercise_theme);
        for(int i = 0; i<exercise.getThemes().size(); i++) {
            themesText += exercise.getThemes().get(i).getName();

            if(i != exercise.getThemes().size() - 1)
                themesText += ", ";
        }
        ((TextView)view.findViewById(R.id.textView5)).setText(themesText);


        chrono = view.findViewById(R.id.layoutchrono);
        chrono.setBase((long)(SystemClock.elapsedRealtime() + exercise.getDuration() * 1000));

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if((chronometer.getBase() - SystemClock.elapsedRealtime()) <= 0.0){
                    setClickReaction();
                    chronometer.setOnChronometerTickListener(null);
                }

            }
        });
        chrono.start();
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setClickReaction();
            }
        });
        return view;
    }

    public void stopChrono()
    {
        timeChrono = chrono.getBase() - SystemClock.elapsedRealtime();
        chrono.stop();
    }

    public void startChrono()
    {
        chrono.setBase(SystemClock.elapsedRealtime() + timeChrono);
        chrono.start();
    }

    private void setClickReaction() {
        if(mutex){
            return;
        } else {
            mutex = true;
        }
        SessionManagerFragment newFragment = null;
        ArrayList<SessionManagerFragment> fragments = activity.getFragments();
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        // this group has finished, we delete the fragment
        if((exerciceNbr == scheduledSession.getSession().getGroupTrainings().get(index - 1).getExerciseSets().size()) && (repetitionNbr == maxRepetitions)) {
            ft.remove(this);
            ft.commitNow();
        }
        else if(index == 1) { // We must remove the second and the third fragment (if they exist...) and put them back
            SessionManagerFragment secondFrag = null;
            SessionManagerFragment lastFrag = null;
            isFirstProcessing = true;

            for(SessionManagerFragment fragment : fragments) {
                if(fragment.index == 3)
                    lastFrag = fragment;

                if(fragment.index == 2)
                    secondFrag = fragment;
            }


            if(secondFrag != null) {
                FragmentTransaction ft2 = supportFragmentManager.beginTransaction();
                ft2.remove(secondFrag);
                ft2.commitNow();
            }

            if(lastFrag != null) {
                FragmentTransaction ft3 = supportFragmentManager.beginTransaction();
                ft3.remove(lastFrag);
                ft3.commitNow();
            }

            if(repetitionNbr < maxRepetitions)
                newFragment = new SessionManagerFragment(activity.green, 1, exerciceNbr, repetitionNbr + 1, activity, scheduledSession);
            else
                newFragment = new SessionManagerFragment(activity.green, 1, exerciceNbr + 1, 1, activity, scheduledSession);

            ft.remove(this);
            ft.add(R.id.linlayout1, newFragment);
            ft.commitNow();

            if(secondFrag != null) {
                FragmentTransaction ft4 = supportFragmentManager.beginTransaction();
                ft4.add(R.id.linlayout1, secondFrag);
                secondFrag.stopChrono(); // done here to lose the minimum amount of time
                ft4.commitNow();
                secondFrag.startChrono();
            }

            if(lastFrag != null) {
                FragmentTransaction ft5 = supportFragmentManager.beginTransaction();
                ft5.add(R.id.linlayout1, lastFrag);
                lastFrag.stopChrono(); // done here to lose the minimum amount of time
                ft5.commitNow();
                lastFrag.startChrono();
            }

            isFirstProcessing = false;
        }
        else if(index == 2) {// We remove the last fragment and add it back
            SessionManagerFragment lastFrag = null;
            boolean shouldManageLast = true;

            if(isFirstProcessing)
                shouldManageLast = false;

            for(SessionManagerFragment fragment : fragments)
                if(fragment.index == 3)
                    lastFrag = fragment;

            if(lastFrag != null && shouldManageLast) {
                FragmentTransaction ft2 = supportFragmentManager.beginTransaction();
                ft2.remove(lastFrag);
                ft2.commitNow();
            }

            if(repetitionNbr < maxRepetitions)
                newFragment = new SessionManagerFragment(activity.blue, 2, exerciceNbr, repetitionNbr + 1, activity, scheduledSession);
            else
                newFragment = new SessionManagerFragment(activity.blue, 2, exerciceNbr + 1, 1, activity, scheduledSession);

            ft.remove(this);
            ft.add(R.id.linlayout1, newFragment);
            ft.commitNow();

            //We put back the third fragment
            if(lastFrag != null && shouldManageLast) {
                FragmentTransaction ft4 = supportFragmentManager.beginTransaction();
                ft4.add(R.id.linlayout1, lastFrag);
                lastFrag.stopChrono(); // done here to lose the minimum amount of time
                ft4.commitNow();
                lastFrag.startChrono();
            }
        }
        else if(index == 3)
        {
            if(repetitionNbr < maxRepetitions)
                newFragment = new SessionManagerFragment(activity.red, 3, exerciceNbr, repetitionNbr + 1, activity, scheduledSession);
            else
                newFragment = new SessionManagerFragment(activity.red, 3, exerciceNbr + 1, 1, activity, scheduledSession);

            ft.remove(this);
            ft.add(R.id.linlayout1, newFragment);
            ft.commitNow();
        }
        fragments.remove(this);

        if(newFragment != null)
            fragments.add(newFragment);

        if(fragments.isEmpty())
            activity.finish();

        mutex = false;
    }
}
