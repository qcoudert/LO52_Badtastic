package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private Exercise exerciseToAdd;
    private MaterialButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar_add_ex);
        setSupportActionBar(tbar);

        exerciseToAdd = new Exercise();
        exerciseToAdd.setThemes(new ArrayList<Theme>());

        ChipGroup chipGroup = (ChipGroup)findViewById(R.id.chip_group_add_ex);

        addButton = (MaterialButton)findViewById(R.id.add_button_add_ex);

        //Ajout du listener sur l'input du nom de l'exercice
        TextInputEditText tiet = (TextInputEditText)findViewById(R.id.edit_text_name_add_ex);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                exerciseToAdd.setName(s.toString());
                checkButtonAvailability();
            }
        });

        //Ajout du listener sur l'input de la description
        tiet = (TextInputEditText)findViewById(R.id.edit_text_desc_add_ex);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                exerciseToAdd.setDescriptino(s.toString());
                checkButtonAvailability();
            }
        });

        //Ajout du listener sur l'input des thématiques
        tiet = (TextInputEditText)findViewById(R.id.edit_text_tags_add_ex);
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
                    if(s.charAt(i)==' ' && i-1>0){
                        LayoutInflater layoutInflater = LayoutInflater.from(AddExerciseActivity.this);
                        Chip chip = (Chip)layoutInflater.inflate(R.layout.chip_layout, chipGroup);
                        chip.setText(s.subSequence(0,i));
                        Theme tag = new Theme(s.subSequence(0,i).toString());

                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exerciseToAdd.getThemes().remove(tag);
                                chipGroup.removeView(v);
                            }
                        });
                    }
                }
            }
        });

        //Ajout du listener sur l'input de la durée
        tiet = (TextInputEditText)findViewById(R.id.edit_text_duree_add_ex);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                exerciseToAdd.setDuration(Double.valueOf(s.toString()));
                checkButtonAvailability();
            }
        });

        //Ajout du listener sur la barre de difficulté
        RatingBar ratingBar = (RatingBar)findViewById(R.id.difficulty_rating_add_ex);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                exerciseToAdd.setDifficulty((int)rating);
                checkButtonAvailability();
            }
        });

        //Ajout du listener sur les boutons radio gérant le type du public
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radio_group_public_add_ex);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_button_debutant_add_ex)
                    exerciseToAdd.setDifficulty(0);
                else
                    exerciseToAdd.setDifficulty(1);
            }
        });

        //Tentative d'ajout de chips, à terminer plus tard ou retirer avant release
        /*TextInputEditText tiet = (TextInputEditText) findViewById(R.id.edit_text_tags_add_ex);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for(int is = i1; is<=i2; is++) {
                    if(charSequence.charAt(is)==' ') {
                        ChipDrawable chip = ChipDrawable.createFromResource(AddExerciseActivity.get(), R.xml.chip);
                        chip.setChipText(editable.subSequence(SpannedLength,editable.length()));
                        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                        ImageSpan span = new ImageSpan(chip);
                        editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        SpannedLength = editable.length();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    public void onAddPressed(View v) {
        ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().insert(exerciseToAdd);
        Toast.makeText(getApplicationContext(), "Exercice ajouté !", Toast.LENGTH_SHORT);
        finish();
    }

    /**
     * Vérifie que l'utilisateur peut ajouter l'exercice en vérifiant que tous les champs ont été remplis
     * @return Vrai si le bouton peut être pressé, faux sinon
     */
    public boolean checkButtonAvailability() {
        if(exerciseToAdd.getName()==null || exerciseToAdd.getDescriptino()==null || exerciseToAdd.getDifficulty()==-1 || exerciseToAdd.getDuration()<0 || exerciseToAdd.getThemes().isEmpty()){
            addButton.setBackgroundColor(getColor(R.color.colorGreyish));
            addButton.setEnabled(false);
            return false;
        }
        else {
            addButton.setBackgroundColor(getColor(R.color.colorAccent));
            addButton.setEnabled(true);
            return true;
        }
    }
}
