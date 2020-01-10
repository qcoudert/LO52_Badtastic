package com.bonsoirdabord.lo52_badtastic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ExerciseBrowserActivity extends AppCompatActivity {

    private ListView exerciseList;
    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_browser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        exerciseAdapter = new ExerciseAdapter(this);

        exerciseList = findViewById(R.id.exerciseList);
        exerciseList.setAdapter(exerciseAdapter);
        loadExercises();
    }

    private String limitLength(String str) {
        //Trim the string to avoid filling the RAM
        //Don't care if we cut it right in the middle because the UI then uses ellipsize
        //This is used to shorten descriptions as they might get very long

        if(str.length() <= 128)
            return str;
        else
            return str.substring(0, 128);
    }

    private void loadExercises() {
        List<Exercise> exercises = ExerciseDatabase.getInstance(this).exerciseDAO().getAllExercise();
        exerciseAdapter.clear();

        for(Exercise ex : exercises)
            exerciseAdapter.add(new ExerciseAdapter.Entry(ex.getId(), ex.getName(), limitLength(ex.getDescriptino())));
    }

    public void addNewExercise(View v) {
        Intent i = new Intent(getApplicationContext(), AddExerciseActivity.class);
        startActivity(i);
    }
}
