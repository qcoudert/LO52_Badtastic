package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class CreateSessionRandomActivity extends AppCompatActivity {

    private Session sessionToCreate;
    private MaterialButton createButton;
    private GroupTraining groupTraining;
    private ChipGroup chipGroup;
    private int currentChipNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_random);

        sessionToCreate = new Session();
        groupTraining = new GroupTraining();
        sessionToCreate.getGroupTrainings().add(groupTraining); //TODO:ENLEVER APRES FINITION
        createButton = (MaterialButton)findViewById(R.id.valid_button_create_sess);

        chipGroup = (ChipGroup)findViewById(R.id.chip_group_create_sess);
        currentChipNumber = 0;

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

        tiet = (TextInputEditText)findViewById(R.id.edit_text_difficulte_create_sess);
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
                    groupTraining.setDifficulty(Integer.valueOf(s.toString()));
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        });

        tiet = (TextInputEditText)findViewById(R.id.edit_text_tags_create_sess);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i=0; i<s.length(); i++) {
                    if(s.charAt(i)==' ' && i-1>0 && currentChipNumber<5){
                        LayoutInflater layoutInflater = LayoutInflater.from(CreateSessionRandomActivity.this);
                        Chip chip = (Chip)layoutInflater.inflate(R.layout.chip_layout, null);
                        chip.setText(s.subSequence(0,i));
                        Theme tag = new Theme(s.subSequence(0,i).toString());
                        groupTraining.getThemes().add(tag);
                        s.delete(0,i);                                                              //On efface le texte responsable du chip
                        chipGroup.addView(chip);
                        currentChipNumber++;

                        //Callback appelé lors de la fermeture d'un chip
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                groupTraining.getThemes().remove(tag);
                                chipGroup.removeView(v);
                                currentChipNumber--;
                                checkButtonAvailability();
                            }
                        });

                        checkButtonAvailability();
                    }
                }
            }
        });

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio_group_public_add_ex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_button_debutant_add_ex)
                    groupTraining.setPublicTarget(0);
                else if(checkedId == R.id.radio_button_confirme_add_ex)
                    groupTraining.setPublicTarget(1);
                else
                    groupTraining.setPublicTarget(2);
            }
        });
    }

    public boolean checkButtonAvailability() {
        if(sessionToCreate.getName()==null || sessionToCreate.getGroupTrainings().isEmpty()) {
            createButton.setEnabled(false);
            return false;
        }
        else {
            createButton.setEnabled(true);
            return true;
        }
    }

    public void onCreatePressed(View v ) {

        //Do...
        //sessionToCreate est la session à envoyer
        SessionGenerator.generateExerciseSetForSession(this, sessionToCreate);

        //temp

        Intent i = new Intent(this.getApplicationContext(), SessionActivity.class);
        i.putExtra("scheduled_session", SessionUtils.saveSession(this, sessionToCreate).getId());
        startActivity(i);




        finish();
    }
}
