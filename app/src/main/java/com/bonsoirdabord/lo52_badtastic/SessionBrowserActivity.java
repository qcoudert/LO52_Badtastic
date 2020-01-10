package com.bonsoirdabord.lo52_badtastic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;
import com.bonsoirdabord.lo52_badtastic.beans.Session;
import com.bonsoirdabord.lo52_badtastic.database.ExerciseDatabase;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SessionBrowserActivity extends AppCompatActivity {

    private static final int CREATE_RANDOM_SESSION_REQUEST = 6942;

    private ListView sessionList;
    private ExerciseAdapter sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_browser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sessionAdapter = new ExerciseAdapter(this);

        sessionAdapter.setDeleteListener(new ExerciseAdapter.EntryDeleteListener() {
            @Override
            public void deleteEntry(ExerciseAdapter.Entry e) {
                new AlertDialog.Builder(SessionBrowserActivity.this)
                        .setTitle(R.string.confirm_session_deletion_title)
                        .setMessage(String.format(Locale.getDefault(), getString(R.string.confirm_session_deletion_text), e.getName()))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int which) {
                                sessionAdapter.remove(e);
                                ExerciseDatabase.getInstance(SessionBrowserActivity.this).sessionDAO().deleteSession(e.getID());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_delete_black)
                        .show();
            }
        });

        sessionList = findViewById(R.id.sessionList);
        sessionList.setAdapter(sessionAdapter);

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ExerciseAdapter.Entry entry = sessionAdapter.getItem(pos);

                Intent intent = new Intent(getApplicationContext(), SessionActivity.class);
                intent.putExtra("scheduled_session", entry.getID());

                startActivity(intent);
            }
        });

        loadSessions();
    }

    private void loadSessions() {
        List<Session> sessions = ExerciseDatabase.getInstance(this).sessionDAO().getAllSession();
        sessionAdapter.clear();

        for(Session s : sessions) {
            StringBuilder desc = new StringBuilder();
            int t = s.getSessionTime() / 60;

            desc.append(getString(R.string.add_ex_duree)).append(": ");

            if(t >= 60) {
                desc.append(t / 60).append("h ");
                t = t % 60;
            }

            int maxDifficulty = 1;
            for(GroupTraining gt : s.getGroupTrainings())
                maxDifficulty = Math.max(maxDifficulty, gt.getDifficulty());

            desc.append(t).append("m, ").append(getString(R.string.add_ex_diff).toLowerCase()).append(' ').append(maxDifficulty);
            sessionAdapter.add(new ExerciseAdapter.Entry(s.getId(), s.getName(), desc.toString()));
        }
    }

    public void generateNewSession(View v) {
        Intent i = new Intent(getApplicationContext(), CreateSessionRandomActivity.class);
        i.putExtra(CreateSessionRandomActivity.START_ON_CREATE, false);

        startActivityForResult(i, CREATE_RANDOM_SESSION_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_RANDOM_SESSION_REQUEST)
            loadSessions();
    }

}
