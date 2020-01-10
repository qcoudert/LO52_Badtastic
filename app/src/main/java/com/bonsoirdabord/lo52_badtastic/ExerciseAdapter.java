package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    private static class ViewHolder {
        private TextView nameView;
        private TextView descView;
        private Entry entry;
    }

    public ExerciseAdapter(@NonNull Context context) {
        super(context, R.layout.layout_exercise_entry);
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

            convertView.setTag(vh);
        }

        Entry entry = getItem(position);

        vh.nameView.setText(entry.name);
        vh.descView.setText(entry.shortDesc);
        vh.entry = entry;

        return convertView;
    }

}
