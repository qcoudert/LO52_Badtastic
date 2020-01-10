package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import org.w3c.dom.Text;

public class ExerciseViewerActivity extends AppCompatActivity {

    public static String EXERCISE_ID_KEY = "exercise_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewer);

        int id = getIntent().getIntExtra(EXERCISE_ID_KEY, 0);

        Exercise exercise = ExerciseDatabase.getInstance(this).exerciseDAO().getExerciseCompleted(id, ExerciseDatabase.getInstance(this));

        TextView tv = (TextView)findViewById(R.id.exercise_viewer_tv_name);
        tv.setText(exercise.getName());

        tv = (TextView)findViewById(R.id.exercise_viewer_tv_desc);
        tv.setText(exercise.getDescriptino());

        tv = (TextView)findViewById(R.id.exercise_viewer_tv_duration);
        tv.setText(String.format(tv.getText().toString(), (int)exercise.getDuration()));

        tv = (TextView)findViewById(R.id.exercise_viewer_tv_public);
        switch(exercise.getPublicType()) {
            case 0:
                tv.setText(String.format(tv.getText().toString(), "Débutant"));
                break;
            case 1:
                tv.setText(String.format(tv.getText().toString(), "Confirmé"));
                break;
            case 2:
                tv.setText(String.format(tv.getText().toString(), "Débutant et confirmé"));
                break;
        }

        RatingBar ratingBar = (RatingBar)findViewById(R.id.exercise_viewer_rating_bar);
        ratingBar.setRating(exercise.getDifficulty());
    }

    public void onDeleteExercisePressed(View v) {
        //Do..
        finish();
    }

    public void onModifyPressed(View v) {
        //Do..
        finish();
    }
}
