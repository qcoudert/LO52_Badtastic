package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ExerciseBrowserActivity extends AppCompatActivity {

    private static final int EDIT_EXERCISE_REQUEST = 0xDEAD;

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

        exerciseAdapter.setDeleteListener(new ExerciseAdapter.EntryDeleteListener() {
            @Override
            public void deleteEntry(ExerciseAdapter.Entry e) {
                new AlertDialog.Builder(ExerciseBrowserActivity.this)
                        .setTitle(R.string.confirm_exercise_deletion_title)
                        .setMessage(String.format(Locale.getDefault(), getString(R.string.confirm_exercise_deletion_text), e.getName()))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int which) {
                                exerciseAdapter.remove(e);
                                ExerciseDatabase.getInstance(ExerciseBrowserActivity.this).exerciseDAO().deleteExercise(e.getID());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete_black)
                        .show();
            }
        });

        exerciseList = findViewById(R.id.exerciseList);
        exerciseList.setAdapter(exerciseAdapter);

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent i = new Intent(ExerciseBrowserActivity.this, ExerciseViewerActivity.class);
                i.putExtra(ExerciseViewerActivity.EXERCISE_ID_KEY, exerciseAdapter.getItem(pos).getID());
                startActivity(i);
            }
        });

        loadExercises();
    }

    private static String limitLength(String str) {
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
        startActivityForResult(i, EDIT_EXERCISE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_EXERCISE_REQUEST)
            loadExercises();
    }
}
