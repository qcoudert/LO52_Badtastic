package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExerciseAdapter extends ArrayAdapter<ExerciseAdapter.Entry> {

    public static class Entry {
        private final int id;
        private final String name;
        private final String shortDesc;

        public Entry(int id, String name, String shortDesc) {
            this.id = id;
            this.name = name;
            this.shortDesc = shortDesc;
        }

        public int getID() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShortDescription() {
            return shortDesc;
        }
    }

    public interface EntryDeleteListener {
        void deleteEntry(Entry e);
    }

    private static class ViewHolder {
        private TextView nameView;
        private TextView descView;
        private Entry entry;
    }

    private class DeleteButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(deleteListener != null)
                deleteListener.deleteEntry(((ViewHolder) view.getTag()).entry);
        }
    }

    private final DeleteButtonListener deleteButtonOnClickListener = new DeleteButtonListener();
    private EntryDeleteListener deleteListener;

    public ExerciseAdapter(@NonNull Context context) {
        super(context, R.layout.layout_exercise_entry);
    }

    public void setDeleteListener(EntryDeleteListener edl) {
        deleteListener = edl;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_exercise_entry, parent, false);

        ViewHolder vh = (ViewHolder) convertView.getTag();
        if(vh == null) {
            vh = new ViewHolder();
            vh.nameView = convertView.findViewById(R.id.exerciseName);
            vh.descView = convertView.findViewById(R.id.exerciseDesc);

            ImageButton deleteButton = convertView.findViewById(R.id.deleteExerciseButton);
            deleteButton.setOnClickListener(deleteButtonOnClickListener);

            deleteButton.setTag(vh);
            convertView.setTag(vh);
        }

        Entry entry = getItem(position);

        vh.nameView.setText(entry.name);
        vh.descView.setText(entry.shortDesc);
        vh.entry = entry;

        return convertView;
    }

}
