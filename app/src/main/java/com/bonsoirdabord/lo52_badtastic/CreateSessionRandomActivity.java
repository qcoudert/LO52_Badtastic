package com.bonsoirdabord.lo52_badtastic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.listViewAdapters.BasicDeleteAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateSessionRandomActivity extends AppCompatActivity {

    public static int GROUP_REQUEST_CODE = 420;
    public static int RESULT_OK = 0;
    public static int RESULT_FAIL = -1;

    private Session sessionToCreate;
    private MaterialButton createButton;
    private ListView listView;
    private BasicDeleteAdapter basicDeleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_random);

        sessionToCreate = new Session();
        createButton = (MaterialButton)findViewById(R.id.valid_button_create_sess);
        listView = (ListView)findViewById(R.id.group_list_create_sess);
        basicDeleteAdapter = new BasicDeleteAdapter(sessionToCreate.getGroupTrainings(),new ArrayList<>(), getApplicationContext());
        listView.setAdapter(basicDeleteAdapter);

        TextInputEditText tiet = (TextInputEditText)findViewById(R.id.edit_text_name_create_sess);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sessionToCreate.setName(s.toString());
                checkButtonAvailability();
            }
        });

        tiet = (TextInputEditText)findViewById(R.id.edit_text_duree_create_sess);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    sessionToCreate.setSessionTime(Integer.valueOf(s.toString())*60);
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
                checkButtonAvailability();
            }
        });

    }

    public boolean checkButtonAvailability() {
        if(sessionToCreate.getName()==null || sessionToCreate.getGroupTrainings().isEmpty() || sessionToCreate.getSessionTime()<0) {
            createButton.setEnabled(false);
            return false;
        }
        else {
            createButton.setEnabled(true);
            return true;
        }
    }

    public void onCreatePressed(View v) {

        //Do...
        //sessionToCreate est la session à envoyer
        SessionGenerator.generateExerciseSetForSession(this, sessionToCreate);
        //temp

        Intent i = new Intent(this.getApplicationContext(), SessionActivity.class);
        i.putExtra("scheduled_session", SessionUtils.saveSession(this, sessionToCreate).getId());
        startActivity(i);




        finish();
    }

    public void onAddGroup(View v) {
        Intent i = new Intent(this.getApplicationContext(), CreatGroupTrainingActivity.class);
        startActivityForResult(i, GROUP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GROUP_REQUEST_CODE && resultCode == RESULT_OK){
            GroupTraining groupTraining = new GroupTraining();

            groupTraining.setDifficulty(data.getIntExtra("diff", 1));

            ArrayList<String> thStrings = data.getStringArrayListExtra("themes");
            for(String str : thStrings) {
                groupTraining.getThemes().add(new Theme(str));
            }

            groupTraining.setPublicTarget(data.getIntExtra("public", 2));

            sessionToCreate.getGroupTrainings().add(groupTraining);
            String s = "Difficulté: " + groupTraining.getDifficulty() + " Thèmes: " + groupTraining.getThemes().get(0).getName() + "..";
            basicDeleteAdapter.add(s);
            basicDeleteAdapter.notifyDataSetChanged();
            checkButtonAvailability();
        }
    }
}
