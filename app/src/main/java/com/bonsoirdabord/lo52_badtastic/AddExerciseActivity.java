package com.bonsoirdabord.lo52_badtastic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.bonsoirdabord.lo52_badtastic.beans.Exercise;
import com.bonsoirdabord.lo52_badtastic.beans.Theme;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddExerciseActivity extends AppCompatActivity {

    private Exercise exerciseToAdd;
    private MaterialButton addButton;
    private int currentChipNumber = 0;

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
                    if(s.charAt(i)==' ' && i-1>0 && currentChipNumber<5){
                        LayoutInflater layoutInflater = LayoutInflater.from(AddExerciseActivity.this);
                        Chip chip = (Chip)layoutInflater.inflate(R.layout.chip_layout, null);
                        chip.setText(s.subSequence(0,i));
                        Theme tag = new Theme(s.subSequence(0,i).toString());
                        exerciseToAdd.getThemes().add(tag);
                        s.delete(0,i);                                                              //On efface le texte responsable du chip
                        chipGroup.addView(chip);
                        currentChipNumber++;

                        //Callback appelé lors de la fermeture d'un chip
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exerciseToAdd.getThemes().remove(tag);
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
                try {
                    exerciseToAdd.setDuration(Double.valueOf(s.toString()));
                }
                catch(NumberFormatException e) {
                    e.printStackTrace();
                }
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
                    exerciseToAdd.setPublicType(0);
                else if(checkedId == R.id.radio_button_confirme_add_ex)
                    exerciseToAdd.setPublicType(1);
                else
                    exerciseToAdd.setPublicType(2);
            }
        });
    }

    /**
     * Fonction onClick du bouton "Ajouter".
     * Ajoute l'exercice dans la base de données de l'utilisateur avant de fermer l'activité.
     * @param v - View du bouton pressé.
     */
    public void onAddPressed(View v) {
        exerciseToAdd.setId((int) ExerciseDatabase.getInstance(getApplicationContext()).exerciseDAO().insert(exerciseToAdd));
        for (Theme theme : exerciseToAdd.getThemes()) {
            theme.setId((int) ExerciseDatabase.getInstance(getApplicationContext())
                    .themeDAO()
                    .insertThemeByExercise(theme, exerciseToAdd, ExerciseDatabase
                            .getInstance(getApplicationContext())));
        }
        finish();
    }

    /**
     * Vérifie que l'utilisateur peut ajouter l'exercice en vérifiant que tous les champs ont été remplis
     * @return Vrai si le bouton peut être pressé, faux sinon
     */
    public boolean checkButtonAvailability() {
        if(exerciseToAdd.getName()==null || exerciseToAdd.getDescriptino()==null || exerciseToAdd.getDifficulty()<1 || exerciseToAdd.getDuration()<0 || exerciseToAdd.getThemes().isEmpty()){
            addButton.setEnabled(false);
            return false;
        }
        else {
            addButton.setEnabled(true);
            return true;
        }
    }
}
