package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalendarActivity extends AppCompatActivity {

    private static final String DATE_FORMAT = "EEEE dd MMMM YYYY";

    private TextView currentDateLabel;
    private ScheduledSessionAdapter daySessions;
    private ListView daySessionsList;
    private Calendar prevDate;
    private CaldroidFragment calendar;

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

                startActivity(intent);
            }
        });

        daySessions.setDeleteListener(new ScheduledSessionAdapter.EntryDeleteListener() {
            @Override
            public void deleteEntry(final ScheduledSessionAdapter.Entry e) {
                new AlertDialog.Builder(CalendarActivity.this)
                        .setTitle(R.string.confirm_deletion_title)
                        .setMessage(String.format(Locale.getDefault(), getString(R.string.confirm_deletion_text), TimeSeparators.H.format(e.getStartHour())))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int which) {
                                daySessions.remove(e);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete_black)
                        .show();
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
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                selectDate(cal);
            }
        });

        prevDate = Calendar.getInstance();
        calendar.setSelectedDate(prevDate.getTime());
        selectDate(prevDate);
    }

    private static ScheduledSessionAdapter.Entry randomSession(int i, Random r) {
        return new ScheduledSessionAdapter.Entry(i, i, (r.nextInt(4) + 8) * 60);
    }

    private void selectDate(Calendar date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String dateStr = dateFormat.format(date.getTime());

        if(dateStr.length() >= 2)
            dateStr = dateStr.substring(0, 1).toUpperCase() + dateStr.substring(1); //If you know a better way, I'll gladly use it...

        currentDateLabel.setText(dateStr);
        daySessions.clear();

        Random r = new Random(date.getTime().getTime());
        for(int i = 0; i < r.nextInt(10); i++)
            daySessions.add(randomSession(i, r));

        calendar.setTextColorForDate(R.color.caldroid_black, prevDate.getTime());
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

        startActivity(intent);
    }

}
