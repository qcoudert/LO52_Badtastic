package com.bonsoirdabord.lo52_badtastic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSessionActivity(View v) {
        Intent i = new Intent(getApplicationContext(), SessionActivity.class);
        i.putExtra("scheduled_session", 1);
        startActivity(i);
    }

    public void launchCreateSessionRandomActivity(View v) {
        Intent i = new Intent(getApplicationContext(), CreateSessionRandomActivity.class);
        startActivity(i);
    }

    public void launchSchedSessMgr(View v) {
        Intent i = new Intent(getApplicationContext(), CalendarActivity.class);
        startActivity(i);
    }

    public void launchExerciseBrowser(View v) {
        Intent i = new Intent(getApplicationContext(), ExerciseBrowserActivity.class);
        startActivity(i);
    }

    public void launchSessionBrowser(View v) {
        Intent i = new Intent(getApplicationContext(), SessionBrowserActivity.class);
        startActivity(i);
    }
}
