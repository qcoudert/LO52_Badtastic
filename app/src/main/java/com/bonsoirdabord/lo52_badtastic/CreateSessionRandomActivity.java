package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateSessionRandomActivity extends AppCompatActivity {

    private Session sessionToCreate;
    private MaterialButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session_random);

        sessionToCreate = new Session();
        createButton = (MaterialButton)findViewById(R.id.valid_button_create_sess);

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

            }
        });
    }

    public boolean checkButtonAvailability() {
        if(sessionToCreate.getName()==null || sessionToCreate.getNumberOfGroup() == 0 || sessionToCreate.getGroupTrainings().isEmpty()) {
            createButton.setEnabled(false);
            return false;
        }
        else {
            createButton.setEnabled(true);
            return true;
        }
    }

    public void onCreatePressed(View v ) {
        //Do
    }
}
