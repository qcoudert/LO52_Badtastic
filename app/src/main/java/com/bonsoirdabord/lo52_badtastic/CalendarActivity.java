package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bonsoirdabord.lo52_badtastic.beans.ScheduledSession;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalendarActivity extends AppCompatActivity {

    private static final DateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final String DISPLAY_DATE_FORMAT = "EEEE d MMMM YYYY";
    private static final int EDIT_SCHEDSESS_REQUEST = 0xBEEF;

    public static Date parseSQLDate(String date) throws ParseException {
        if(date.isEmpty())
            return new Date();

        return SQL_DATE_FORMAT.parse(date);
    }

    public static String formatSQLDate(Date date) {
        return SQL_DATE_FORMAT.format(date);
    }

    private TextView currentDateLabel;
    private ScheduledSessionAdapter daySessions;
    private ListView daySessionsList;
    private Calendar prevDate;
    private CalendarFragment calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        currentDateLabel = findViewById(R.id.selectedDayLabel);
        daySessions = new ScheduledSessionAdapter(this);

        daySessionsList = findViewById(R.id.schedSessionList);
        daySessionsList.setAdapter(daySessions);

        daySessionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ScheduledSessionAdapter.Entry e = daySessions.getItem(pos);

                Intent intent = new Intent(CalendarActivity.this, AddScheduledSessionActivity.class);
                intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_ID, e.getID());
                intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_NUMBER, e.getDayNumber());
                intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_DATE, prevDate);
                intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_TIME, e.getStartHour());

                startActivityForResult(intent, EDIT_SCHEDSESS_REQUEST);
            }
        });

        daySessions.setDeleteListener(new ScheduledSessionAdapter.EntryDeleteListener() {
            @Override
            public void deleteEntry(final ScheduledSessionAdapter.Entry e) {
                new AlertDialog.Builder(CalendarActivity.this)
                        .setTitle(R.string.confirm_session_deletion_title)
                        .setMessage(String.format(Locale.getDefault(), getString(R.string.confirm_session_deletion_text), TimeSeparators.H.format(e.getStartHour())))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int which) {
                                daySessions.remove(e);
                                calendar.decrementSessionCountForDate(prevDate.getTime());
                                calendar.refreshView();

                                ExerciseDatabase.getInstance(CalendarActivity.this).scheduledSessionDAO().delete(e.getID());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete_black)
                        .show();
            }
        });

        daySessions.setStartListener(new ScheduledSessionAdapter.EntryStartListener() {
            @Override
            public void startEntry(ScheduledSessionAdapter.Entry e) {
                ScheduledSession ss = ExerciseDatabase.getInstance(CalendarActivity.this).scheduledSessionDAO().getScheduledSession(e.getID());

                if(ss != null) {
                    Session s = ExerciseDatabase.getInstance(CalendarActivity.this).sessionDAO().getSession(ss.getSessionId());

                    if(s != null) {
                        Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
                        intent.putExtra("scheduled_session", s.getId());

                        startActivity(intent);
                    }
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        calendar = (CalendarFragment) getSupportFragmentManager().findFragmentById(R.id.calendar);
        if(calendar == null)
            throw new RuntimeException("Could not find calendar fragment in CalendarActivity (WTF?)");

        calendar.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                selectDate(cal);
            }
        });

        prevDate = Calendar.getInstance();
        calendar.setSelectedDate(prevDate.getTime());
        selectDate(prevDate);
    }

    private static boolean dateEquals(Calendar d1, Calendar d2) {
        return d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)
                && d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)
                && d1.get(Calendar.DAY_OF_MONTH) == d2.get(Calendar.DAY_OF_MONTH);
    }

    private void selectDate(Calendar date) {
        DateFormat dateFormat = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault());
        String dateStr = dateFormat.format(date.getTime());

        if(dateStr.length() >= 2)
            dateStr = dateStr.substring(0, 1).toUpperCase() + dateStr.substring(1); //If you know a better way, I'll gladly use it...

        currentDateLabel.setText(dateStr);
        daySessions.clear();

        int curDayCount = 0;
        List<ScheduledSession> data = ExerciseDatabase.getInstance(this).scheduledSessionDAO().getAllScheduledSession();
        calendar.clearSessionCount();

        if(data == null)
            Log.d("SCHEDSESS", "ScheduledSession list from database is null");
        else {
            for(int i = 0; i < data.size(); i++) {
                ScheduledSession ss = data.get(i);

                try {
                    Calendar ssCal = Calendar.getInstance();
                    ssCal.setTime(parseSQLDate(ss.getDate())); //FIXME: Make sure there's no issue with timezones here...

                    if(dateEquals(ssCal, date))
                        daySessions.add(new ScheduledSessionAdapter.Entry(ss.getId(), curDayCount++, ss.getHour()));

                    calendar.incrementSessionCountForDate(ssCal.getTime());
                } catch(ParseException e) {
                    Log.e("SCHEDSESS", "Failed to parse **DATABASE** date (" + ss.getDate() + ")", e);
                }
            }
        }

        calendar.clearTextColorForDate(prevDate.getTime());
        calendar.setTextColorForDate(R.color.caldroid_holo_blue_light, date.getTime());
        calendar.refreshView();
        daySessionsList.refreshDrawableState();

        prevDate = date;
    }

    public void scheduleNewSession(View btn) {
        int max = 0;
        for(int i = 0; i < daySessions.getCount(); i++) {
            ScheduledSessionAdapter.Entry e = daySessions.getItem(i);

            if(e.getDayNumber() > max)
                max = e.getDayNumber();
        }

        Intent intent = new Intent(this, AddScheduledSessionActivity.class);
        intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_ID, -1); //-1 => new ss
        intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_NUMBER, max + 1);
        intent.putExtra(AddScheduledSessionActivity.PROPERTY_SESSION_DATE, prevDate);

        startActivityForResult(intent, EDIT_SCHEDSESS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_SCHEDSESS_REQUEST && resultCode == RESULT_OK)
            selectDate(prevDate);
    }

}
