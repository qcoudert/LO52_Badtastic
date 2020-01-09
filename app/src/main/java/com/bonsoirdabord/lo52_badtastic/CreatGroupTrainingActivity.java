package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class CreatGroupTrainingActivity extends AppCompatActivity {

    private GroupTraining groupTraining;
    private ChipGroup chipGroup;
    private int currentChipNumber;
    private MaterialButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_group_training);

        groupTraining = new GroupTraining();
        chipGroup = (ChipGroup)findViewById(R.id.chip_group_create_group);
        createButton = (MaterialButton)findViewById(R.id.valid_button_create_group);

        TextInputEditText tiet = (TextInputEditText)findViewById(R.id.edit_text_tags_create_group);
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
                        LayoutInflater layoutInflater = LayoutInflater.from(CreatGroupTrainingActivity.this);
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

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                groupTraining.setDifficulty((int)rating);
                checkButtonAvailability();
            }
        });
    }

    private boolean checkButtonAvailability() {
        if(groupTraining.getDifficulty()<0 || groupTraining.getThemes().isEmpty()) {
            createButton.setEnabled(false);
            return false;
        }
        else {
            createButton.setEnabled(true);
            return true;
        }
    }
}
