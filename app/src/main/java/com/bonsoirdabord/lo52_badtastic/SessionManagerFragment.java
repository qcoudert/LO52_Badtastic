package com.bonsoirdabord.lo52_badtastic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SessionManagerFragment extends Fragment {

    private int color;

    public SessionManagerFragment(int color){
        super();
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_session_manager,
                container, false);
        view.setBackgroundColor(color);
        return view;
    }
}
