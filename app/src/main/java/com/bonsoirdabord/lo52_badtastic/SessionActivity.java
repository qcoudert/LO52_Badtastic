package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class SessionActivity extends AppCompatActivity {

    public final int red = 0xffcf2a27;
    public final int green = 0xff93c47d;
    public final int blue = 0xff9fc5f8;
    public final int[] colors = {green, blue, red};

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

        try {
            for (int i = 0; i < getScheduledSession(id).getSession().getGroupTrainings().size(); i++)
                createNewFragment(colors[i % 3], i + 1);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

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
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Non", null)
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

    public ArrayList<SessionManagerFragment> getFragments() {
        return fragments;
    }

    private SessionManagerFragment createNewFragment(int color, int index){
        SessionManagerFragment sessionManagerFragment = null;
        try {
            sessionManagerFragment = new SessionManagerFragment(color, index, 1, 1,  this, getScheduledSession(id));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linlayout1, sessionManagerFragment, "fragment"+index);
        fragmentTransaction.commitNow();
        fragments.add(sessionManagerFragment);
        return sessionManagerFragment;
    }

    private ScheduledSession getScheduledSession(int id) throws Exception{
        if(id == -1)
            throw new Exception("Extra wasn't properly got"); // Data base lecture problem

        return ExerciseDatabase.getInstance(this).scheduledSessionDAO()
                .getScheduledSessionCompleted(id, ExerciseDatabase.getInstance(this));
    }
}