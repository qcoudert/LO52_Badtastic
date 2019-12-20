package com.bonsoirdabord.lo52_badtastic;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;

public class SessionManagerFragment extends Fragment {

    private int color;
    private int index;
    private long timeChrono;
    private Chronometer chrono;
    private TextView groupNbr;
    private ScheduledSession scheduledSession;

    public SessionManagerFragment(int color, int index, ScheduledSession scheduledSession){
        super();
        this.color = color;
        this.index = index;
        this.scheduledSession = scheduledSession;
        this.timeChrono = 0;
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
        return view;
    }

    public Chronometer getChrono() {
        return chrono;
    }

    public TextView getGroupNbr() {
        return groupNbr;
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
}
