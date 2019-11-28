package com.bonsoirdabord.lo52_badtastic;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalendarActivity extends AppCompatActivity {

    private static final String DATE_FORMAT = "EEEE dd MMMM YYYY";

    private TextView currentDateLabel;
    private ScheduledSessionAdapter daySessions;
    private Date prevDate;
    private CaldroidFragment calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        currentDateLabel = findViewById(R.id.selectedDayLabel);
        daySessions = new ScheduledSessionAdapter(this);

        ListView sessions = findViewById(R.id.schedSessionList);
        sessions.setAdapter(daySessions);

        daySessions.setDeleteListener(new ScheduledSessionAdapter.EntryDeleteListener() {
            @Override
            public void deleteEntry(ScheduledSessionAdapter.Entry e) {
                //TODO: Ask user for confirmation and code a real delete
                daySessions.remove(e);
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

        calendar = (CaldroidFragment) getSupportFragmentManager().findFragmentById(R.id.calendar);
        if(calendar == null)
            throw new RuntimeException("Could not find calendar fragment in CalendarActivity (WTF?)");

        calendar.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                selectDate(date);
            }
        });

        prevDate = Calendar.getInstance().getTime();
        calendar.setSelectedDate(prevDate);
        selectDate(prevDate);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private static ScheduledSessionAdapter.Entry randomSession(int i, Random r) {
        int start = r.nextInt(4) + 8;
        int end = start + r.nextInt(2) + 1;

        return new ScheduledSessionAdapter.Entry(-1, i, start, end);
    }

    private void selectDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String dateStr = dateFormat.format(date);

        if(dateStr.length() >= 2)
            dateStr = dateStr.substring(0, 1).toUpperCase() + dateStr.substring(1); //If you know a better way, I'll gladly use it...

        currentDateLabel.setText(dateStr);
        daySessions.clear();

        Random r = new Random(date.getTime());
        for(int i = 0; i < r.nextInt(10); i++)
            daySessions.add(randomSession(i, r));

        calendar.setTextColorForDate(R.color.caldroid_black, prevDate);
        calendar.setTextColorForDate(R.color.caldroid_holo_blue_light, date);
        calendar.refreshView();
        prevDate = date;
    }

    public void scheduleNewSession(View btn) {
        int max = 0;
        for(int i = 0; i < daySessions.getCount(); i++) {
            ScheduledSessionAdapter.Entry e = daySessions.getItem(i);

            if(e.getDayNumber() > max)
                max = e.getDayNumber();
        }

        daySessions.add(randomSession(max + 1, ThreadLocalRandom.current()));
    }

}
