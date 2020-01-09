package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSessionActivity(View v) {
        Intent i = new Intent(this.getApplicationContext(), SessionActivity.class);
        i.putExtra("scheduled_session", 1);
        startActivity(i);
    }

    public void launchAddExercise(View v) {
        Intent i = new Intent(this.getApplicationContext(), AddExerciseActivity.class);
        startActivity(i);
    }

    public void launchCreateSessionRandomActivity(View v) {
        Intent i = new Intent(this.getApplicationContext(), CreateSessionRandomActivity.class);
        startActivity(i);
    }
}
