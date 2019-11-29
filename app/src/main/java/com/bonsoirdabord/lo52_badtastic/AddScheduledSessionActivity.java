package com.bonsoirdabord.lo52_badtastic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddScheduledSessionActivity extends AppCompatActivity {

    public static final String PROPERTY_SESSION_ID = "sessionId";
    public static final String PROPERTY_SESSION_NUMBER = "sessionNumber";
    public static final String PROPERTY_SESSION_DATE = "sessionDate";
    public static final String PROPERTY_SESSION_TIME = "sessionTime";

    private int sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheduled_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        sid = intent.getIntExtra(PROPERTY_SESSION_ID, -1);
        int sessionNumber = intent.getIntExtra(PROPERTY_SESSION_NUMBER, 0);
        Date sessionDate = (Date) intent.getSerializableExtra(PROPERTY_SESSION_DATE);
        int sessionTime = intent.getIntExtra(PROPERTY_SESSION_TIME, -1);

        if(sessionDate == null)
            sessionDate = Calendar.getInstance().getTime();

        if(sessionTime < 0)
            sessionTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setTitle(sid < 0 ? R.string.sched_session_add : R.string.sched_session_edit);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        TextView title = findViewById(R.id.session_label);
        EditText date = findViewById(R.id.date_field);
        EditText time = findViewById(R.id.time_field);

        title.setText(String.format(Locale.getDefault(), getString(R.string.session_number_format), sessionNumber + 1));
        date.setText(dateFormat.format(sessionDate));
        time.setText(TimeSeparators.COLONS.format(sessionTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_scheduled_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.validate_menu_item) {
            //TODO: Add entry to database
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
