package com.bonsoirdabord.lo52_badtastic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.listViewAdapters.BasicDeleteAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CreateSessionRandomActivity extends AppCompatActivity {

    public static int GROUP_REQUEST_CODE = 420;
    public static int RESULT_OK = 0;
    public static int RESULT_FAIL = -1;

    public static String START_ON_CREATE = "startOnCreate";

    private Session sessionToCreate;
    private MaterialButton createButton;
    private MaterialButton addButton;
    private ListView listView;
    private BasicDeleteAdapter basicDeleteAdapter;
    private boolean startOnCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_random);

        sessionToCreate = new Session();
        createButton = (MaterialButton)findViewById(R.id.valid_button_create_sess);
        addButton = (MaterialButton)findViewById(R.id.add_group_button_create_sess);
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

    public void checkButtonAvailability() {
        if(sessionToCreate.getName()==null || sessionToCreate.getGroupTrainings().isEmpty() || sessionToCreate.getSessionTime()<0) {
            createButton.setEnabled(false);
        }
        else {
            createButton.setEnabled(true);
        }

        if(sessionToCreate.getGroupTrainings().size()<3){
            addButton.setEnabled(true);
        }
        else {
            addButton.setEnabled(false);
        }
    }

    public void onCreatePressed(View v) {

        //Do...
        //sessionToCreate est la session à envoyer
        SessionGenerator.generateExerciseSetForSession(this, sessionToCreate);
        if(SessionGenerator.computeSessionCorrelationScore(sessionToCreate)<0.66){
            new AlertDialog.Builder(this).setTitle(R.string.create_session_low_cor)
                                                 .setMessage(R.string.create_session_low_cor_desc)
                                                 .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         SessionUtils.saveSession(CreateSessionRandomActivity.this, sessionToCreate);
                                                     }
                                                 })
                                                 .setNegativeButton(android.R.string.no, null)
                                                 .setIcon(R.drawable.ic_warning_black_24dp)
                                                 .show();
        }
        else {
            SessionUtils.saveSession(this, sessionToCreate);
        }

        int savedSessId = SessionUtils.saveSession(this, sessionToCreate).getId();
        //temp

        if(startOnCreate) {
            Intent i = new Intent(this.getApplicationContext(), SessionActivity.class);
            i.putExtra("scheduled_session", savedSessId);
            startActivity(i);
        }




        finish();
    }

    public void onAddGroup(View v) {
        Intent i = new Intent(this.getApplicationContext(), CreatGroupTrainingActivity.class);
        startActivityForResult(i, GROUP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GROUP_REQUEST_CODE && resultCode == RESULT_OK && sessionToCreate.getGroupTrainings().size()<4){
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
