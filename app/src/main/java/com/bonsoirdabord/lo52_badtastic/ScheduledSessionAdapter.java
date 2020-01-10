package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScheduledSessionAdapter extends ArrayAdapter<ScheduledSessionAdapter.Entry> {

    public static class Entry {
        private final int id;
        private final int dayNumber;
        private final int startHour;

        public Entry(int id, int dayNumber, int startHour) {
            this.id = id;
            this.dayNumber = dayNumber;
            this.startHour = startHour;
        }

        public int getID() {
            return id;
        }

        public int getDayNumber() {
            return dayNumber;
        }

        public int getStartHour() {
            return startHour;
        }
    }

    public interface EntryDeleteListener {
        void deleteEntry(Entry e);
    }

    public interface EntryStartListener {
        void startEntry(Entry e);
    }

    private static class ViewHolder {
        private TextView sessionNumberLabel;
        private TextView sessionTimeLabel;
        private Entry entry;
    }

    private class DeleteButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(deleteListener != null)
                deleteListener.deleteEntry(((ViewHolder) view.getTag()).entry);
        }
    }

    private class StartButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(startListener != null)
                startListener.startEntry(((ViewHolder) view.getTag()).entry);
        }
    }

    private final DeleteButtonListener deleteButtonOnClickListener = new DeleteButtonListener();
    private final StartButtonListener startButtonOnClickListener = new StartButtonListener();
    private EntryDeleteListener deleteListener;
    private EntryStartListener startListener;

    public ScheduledSessionAdapter(Context ctx) {
        super(ctx, R.layout.layout_sched_entry);
    }

    public void setDeleteListener(EntryDeleteListener edl) {
        deleteListener = edl;
    }

    public void setStartListener(EntryStartListener esl) {
        startListener = esl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_sched_entry, parent, false);

        ViewHolder vh = (ViewHolder) convertView.getTag();
        if(vh == null) {
            vh = new ViewHolder();
            vh.sessionNumberLabel = convertView.findViewById(R.id.sessionNumberLabel);
            vh.sessionTimeLabel = convertView.findViewById(R.id.sessionTime);

            ImageButton deleteButton = convertView.findViewById(R.id.deleteSessionButton);
            deleteButton.setOnClickListener(deleteButtonOnClickListener);

            ImageButton startButton = convertView.findViewById(R.id.startSessionButton);
            startButton.setOnClickListener(startButtonOnClickListener);

            deleteButton.setTag(vh);
            startButton.setTag(vh);
            convertView.setTag(vh);
        }

        Locale curLocale = Locale.getDefault();
        Entry entry = getItem(position);

        vh.sessionNumberLabel.setText(String.format(curLocale, getContext().getString(R.string.session_number_format), entry.dayNumber + 1));
        vh.sessionTimeLabel.setText(TimeSeparators.H.format(entry.startHour));
        vh.entry = entry;

        return convertView;
    }
}
