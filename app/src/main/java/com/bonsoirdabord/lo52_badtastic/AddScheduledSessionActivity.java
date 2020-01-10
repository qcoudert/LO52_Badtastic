package com.bonsoirdabord.lo52_badtastic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.dao.ScheduledSessionDAO;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddScheduledSessionActivity extends AppCompatActivity {

    public static final String PROPERTY_SESSION_ID = "sessionId";
    public static final String PROPERTY_SESSION_NUMBER = "sessionNumber";
    public static final String PROPERTY_SESSION_DATE = "sessionDate";
    public static final String PROPERTY_SESSION_TIME = "sessionTime";

    private static final TimeSeparators TIME_SEP = TimeSeparators.COLONS;

    private DateFormat dateFormat;
    private int sid;
    private EditText dateField;
    private EditText timeField;
    private ArrayAdapter<String> sessionAutoComplete;
    private AutoCompleteTextView sessionSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheduled_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateFormat = android.text.format.DateFormat.getDateFormat(this);

        Intent intent = getIntent();
        sid = intent.getIntExtra(PROPERTY_SESSION_ID, -1);
        int sessionNumber = intent.getIntExtra(PROPERTY_SESSION_NUMBER, 0);
        Calendar sessionDate = (Calendar) intent.getSerializableExtra(PROPERTY_SESSION_DATE);
        int sessionTime = intent.getIntExtra(PROPERTY_SESSION_TIME, -1);

        if(sessionDate == null)
            sessionDate = Calendar.getInstance();

        if(sessionTime < 0)
            sessionTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        getSupportActionBar().setTitle(sid < 0 ? R.string.sched_session_add : R.string.sched_session_edit);

        TextView title = findViewById(R.id.session_label);
        dateField = findViewById(R.id.date_field);
        timeField = findViewById(R.id.time_field);
        sessionSelect = findViewById(R.id.session_field);

        title.setText(String.format(Locale.getDefault(), getString(R.string.session_number_format), sessionNumber + 1));
        dateField.setText(dateFormat.format(sessionDate.getTime()));
        timeField.setText(TIME_SEP.format(sessionTime));

        sessionAutoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        List<Session> sessions = ExerciseDatabase.getInstance(this).sessionDAO().getAllSession();
        for(Session s : sessions)
            sessionAutoComplete.add(s.getName());

        sessionSelect.setAdapter(sessionAutoComplete);
        sessionSelect.setThreshold(1);

        sessionSelect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) { //FIXME: Is this really useful ?
                if(!hasFocus)
                    validateSession((AutoCompleteTextView) view);
            }
        });

        sessionSelect.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                    return validateSession((AutoCompleteTextView) view);

                return false;
            }
        });

        if(sid >= 0) {
            ScheduledSession ss = ExerciseDatabase.getInstance(this).scheduledSessionDAO().getScheduledSession(sid);

            if(ss != null) {
                Session s = ExerciseDatabase.getInstance(this).sessionDAO().getSession(ss.getSessionId());

                if(s != null)
                    sessionSelect.setText(s.getName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_scheduled_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.validate_menu_item) {
            if(validateSession((AutoCompleteTextView) findViewById(R.id.session_field)))
                return true;

            try {
                String sqlDate = CalendarActivity.formatSQLDate(dateFormat.parse(dateField.getText().toString()));
                int time = TIME_SEP.parse(timeField.getText().toString());

                if(time < 0)
                    throw new ParseException("Could not parse time", 0);

                List<Session> sessions = ExerciseDatabase.getInstance(this).sessionDAO().getAllSession();
                int sessId = -1;

                for(Session s : sessions) {
                    if(s.getName().equalsIgnoreCase(sessionSelect.getText().toString())) {
                        sessId = s.getId();
                        break;
                    }
                }

                if(sessId < 0)
                    return true;

                ScheduledSessionDAO ssdao = ExerciseDatabase.getInstance(this).scheduledSessionDAO();
                ScheduledSession bean = new ScheduledSession(sqlDate, time, sessId);

                if(sid < 0)
                    ssdao.insert(bean);
                else {
                    bean.setId(sid);
                    ssdao.update(bean);
                }

                setResult(RESULT_OK);
                finish();
            } catch(ParseException ex) {
                Log.e("SCHEDSESS", "Failed to parse date", ex);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDate(View v) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(dateFormat.parse(dateField.getText().toString()));
        } catch(ParseException ex) {
            Log.e("SCHEDSESS", "Failed to parse date", ex);
        }

        DatePickerDialog dpd = new DatePickerDialog(this);
        dpd.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                Calendar cal = Calendar.getInstance();
                cal.set(y, m, d);

                dateField.setText(dateFormat.format(cal.getTime()));
            }
        });

        dpd.show();
    }

    public void selectTime(View v) {
        int time = TIME_SEP.parse(timeField.getText().toString());
        if(time < 0)
            time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60;

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        timeField.setText(TIME_SEP.format(h, m));
                    }
                }, time / 60, time % 60, true);

        tpd.show();
    }

    private boolean validateSession(AutoCompleteTextView actv) {
        String val = sessionSelect.getText().toString();
        boolean wrong = true;

        for(int i = 0; i < sessionAutoComplete.getCount(); i++) {
            if(sessionAutoComplete.getItem(i).equalsIgnoreCase(val)) {
                wrong = false;
                break;
            }
        }

        if(wrong)
            sessionSelect.setError(getString(R.string.session_invalid));

        return wrong;
    }

}
