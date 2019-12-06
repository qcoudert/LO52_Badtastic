package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {

    private final int red = 0xffcf2a27;
    private final int green = 0xff93c47d;
    private final int blue = 0xff9fc5f8;

    ArrayList<SessionManagerFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        fragments = new ArrayList<>();

        createNewFragment(green);
        createNewFragment(blue);
        createNewFragment(red);

    }

    private void createNewFragment(int color){
        SessionManagerFragment sessionManagerFragment = new SessionManagerFragment(color);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.linlayout1, sessionManagerFragment);
        fragmentTransaction.commitNow();
        fragments.add(sessionManagerFragment);
    }
}