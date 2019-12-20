package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionDAO_Impl;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {

    private final int red = 0xffcf2a27;
    private final int green = 0xff93c47d;
    private final int blue = 0xff9fc5f8;

    private ArrayList<SessionManagerFragment> fragments;
    private ArrayList<Chronometer> chronos;
    private Chronometer globChrono;
    private boolean isChronoPaused;
    private long timeChrono;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        id = getIntent().getIntExtra("scheduled_session", -1);

        fragments = new ArrayList<>();
        chronos = new ArrayList<>();
        timeChrono = 0;

        createNewFragment(green, 1);
        createNewFragment(blue, 2);
        createNewFragment(red, 3);

        globChrono = findViewById(R.id.globchrono);
        globChrono.start();
        isChronoPaused = false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Fermeture de l'entraînement")
                .setMessage("Etes-vous certain de vouloir mettre fin à l'entraînement ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //TODO : implement save before leaving
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void onBackPressed(View view) {
        onBackPressed();
    }

    public void pauseChronometers(View view) {
        if(isChronoPaused) {
            globChrono.setBase(SystemClock.elapsedRealtime() + timeChrono);
            globChrono.start();
            for(SessionManagerFragment fragment : fragments)
                fragment.startChrono();
        }
        else {
            timeChrono = globChrono.getBase() - SystemClock.elapsedRealtime();
            globChrono.stop();
            for(SessionManagerFragment fragment : fragments)
                fragment.stopChrono();
        }

        isChronoPaused = !isChronoPaused;
    }

    // marche pas pour l'instant : détruit tout le layout
    public void swapFragment(View view)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.linlayout1, createNewFragment(green, 1), "fragment1");
        ft.commit();
    }

    private SessionManagerFragment createNewFragment(int color, int index){
        SessionManagerFragment sessionManagerFragment = new SessionManagerFragment(color, index, getScheduledSession(id));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linlayout1, sessionManagerFragment, "fragment"+index);
        fragmentTransaction.commitNow();
        fragments.add(sessionManagerFragment);
        return sessionManagerFragment;
    }

    private ScheduledSession getScheduledSession(int id){
        ScheduledSessionDAO_Impl scheduledSessionDAO = new ScheduledSessionDAO_Impl(ExerciseDatabase.getInstance(this));
        //return scheduledSessionDAO.getScheduledSession(id);
        return null;
    }
}