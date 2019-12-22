package com.bonsoirdabord.lo52_badtastic;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;

import java.util.ArrayList;

public class SessionManagerFragment extends Fragment {

    private int color;
    private int index;
    private long timeChrono;
    private Chronometer chrono;
    private TextView groupNbr;
    private ScheduledSession scheduledSession;
    private SessionActivity activity;

    public SessionManagerFragment(int color, int index, SessionActivity activity, ScheduledSession scheduledSession){
        super();
        this.color = color;
        this.index = index;
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
        groupNbr = view.findViewById(R.id.textView);
        groupNbr.setText("Groupe " + index);
        chrono = view.findViewById(R.id.layoutchrono);
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
        SessionManagerFragment newFragment = null;
        ArrayList<SessionManagerFragment> fragments = activity.getFragments();
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        if(index == 1) {
            /*
               The replace method has a bug : "the ghost Fragment", the first green fragment stay behind the new ones.
               The only way to hide it from the user during transition is to do a hide & show. (remove, only hide etc...
               will result on a deleting of the whole fragment. We could as well use the same method as for index 2 (remove the 2 other
               fragments, add the new green one and put back the 2 others, but it would have a bigger computational cost
             */
            newFragment = new SessionManagerFragment(activity.green, 1, activity, scheduledSession);
            ft.replace(R.id.fragment, newFragment);
            ft.hide(activity.getFirstFragment());
            ft.show(activity.getFirstFragment());
            ft.commitNow();
        }
        else if(index == 2) {
            // We remove the last fragment for adding the new second fragment before
            SessionManagerFragment lastFrag = null;
            for(SessionManagerFragment fragment : fragments)
                if(fragment.index == 3)
                    lastFrag = fragment;

            if(lastFrag != null) {
                FragmentTransaction ft2 = supportFragmentManager.beginTransaction();
                ft2.remove(lastFrag);
                ft2.commitNow();
            }

            newFragment = new SessionManagerFragment(activity.blue, 2, activity, scheduledSession);
            ft.remove(this);
            ft.add(R.id.linlayout1, newFragment);
            ft.commitNow();

            //We put back the third fragment
            if(lastFrag != null) {
                FragmentTransaction ft3 = supportFragmentManager.beginTransaction();
                ft3.add(R.id.linlayout1, lastFrag);
                lastFrag.stopChrono(); // done here to lose the minimum amount of time
                ft3.commitNow();
                lastFrag.startChrono();
            }
        }
        else if(index == 3)
        {
            newFragment = new SessionManagerFragment(activity.red, 3, activity, scheduledSession);
            ft.remove(this);
            ft.add(R.id.linlayout1, newFragment);
            ft.commitNow();
        }
        fragments.remove(this);
        fragments.add(newFragment);
    }
}
