package com.bonsoirdabord.lo52_badtastic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.widget.EditText;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar_add_ex);
        setSupportActionBar(tbar);

        TextInputEditText tiet = (TextInputEditText) findViewById(R.id.edit_text_tags_add_ex);
        tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for(int is = i1; is<=i2; i++) {
                    if(charSequence.charAt(is)==' ') {
                        ChipDrawable chip = ChipDrawable.createFromResource(getContext(), R.xml.chip);
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
        });
    }
}
